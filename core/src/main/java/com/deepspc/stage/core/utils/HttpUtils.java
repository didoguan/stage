package com.deepspc.stage.core.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送HTTP请求工具类
 * @author gzw
 * @date 2021/2/2 16:22
 */
public class HttpUtils {

    /**
     * 连接超时时间
     */
    private static final Integer HTTP_CONNECTION_TIMEOUT = 20000; //20秒

    /**
     * 读取数据超时时间
     */
    private static final Integer HTTP_REQUST_TIMEOUT = 20000; //20秒

    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String CONTENT_GBK = "GBK";

    public static final Charset UTF_8 = Charset.forName(CHARSET_UTF8);

    public static final Charset GBK = Charset.forName(CONTENT_GBK);

    public static HttpResponse doGet(String host, Map<String, String> headers,
                                     Map<String, String> querys) throws IOException {
        HttpClient httpClient = wrapClient(host);
        String url = buildUrl(host, querys);
        HttpGet request = new HttpGet(url);
        if (null != headers) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
        return httpClient.execute(request);
    }

    public static HttpResponse doPost(String host,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys) throws IOException {
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(buildUrl(host, querys));
        if(null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.setHeader(e.getKey(), e.getValue());
            }
        }
        if (bodys != null) {
            StringEntity entity = new StringEntity(JsonUtil.obj2json(bodys), "UTF-8");
            request.setEntity(entity);
        }
        return httpClient.execute(request);
    }

    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body) throws IOException {
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(buildUrl(host, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (StrUtil.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }

    public static String doPost(String url, Map<String, String> params,String charset) throws IOException {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(buildHttpPost(params), charset == null ? CHARSET_UTF8 : charset));
           return httpClient.execute(httpPost, strResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            abortConnection(httpPost, httpClient);
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
    }

    // 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放， 解决对连接的释放管理
    private static ResponseHandler<String> strResponseHandler = new ResponseHandler<String>() {
        public String handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8 : EntityUtils.getContentCharSet(entity);
                return new String(EntityUtils.toByteArray(entity), charset);
            }
            return null;
        }
    };

    private static void abortConnection(final HttpRequestBase httpRequestBase, final CloseableHttpClient httpClient) throws IOException {
        if (httpRequestBase != null) {
            httpRequestBase.abort();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    /**
     * 封装参数
     * @param params
     * @return
     */
    private static List<NameValuePair> buildHttpPost(Map<String, String> params){
        List<NameValuePair> ps = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        return ps;
    }

    /**
     * 设置重连机制和异常自动恢复处理
     */
    private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 3) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent) {
                return true;
            }
            return false;
        }
    };

    public static HttpResponse doPost(String host,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      byte[] body) throws IOException {
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(buildUrl(host, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }
        return httpClient.execute(request);
    }

    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body) throws IOException {
        HttpClient httpClient = wrapClient(host);
        HttpPut request = new HttpPut(buildUrl(host, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (StrUtil.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }

    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     byte[] body) throws IOException {
        HttpClient httpClient = wrapClient(host);
        HttpPut request = new HttpPut(buildUrl(host, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }
        return httpClient.execute(request);
    }

    public static HttpResponse doDelete(String host, String path, String method,
                                        Map<String, String> headers,
                                        Map<String, String> querys) throws IOException {
        HttpClient httpClient = wrapClient(host);
        HttpDelete request = new HttpDelete(buildUrl(host, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        return httpClient.execute(request);
    }

    private static String buildUrl(String host, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (null != querys && !querys.isEmpty()) {
            sbUrl.append("?");
            int i = 1;
            for (Map.Entry<String, String> query : querys.entrySet()) {
                sbUrl.append(query.getKey()).append("=");
                if (StrUtil.isNotBlank(query.getValue())) {
                    sbUrl.append(URLEncoder.encode(query.getValue(), "utf-8"));
                }
                if (i < querys.size()) {
                    sbUrl.append("&");
                }
                i++;
            }

        }
        return sbUrl.toString();
    }

    private static CloseableHttpClient wrapClient(String host) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        if (host.startsWith("https://")) {
            return sslClient();
        }
        return httpClient;
    }

    private static CloseableHttpClient sslClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }
                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
                    ctx, NoopHostnameVerifier.INSTANCE);
            return HttpClients.custom().setSSLSocketFactory(ssf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String sendPost(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuilder sb = new StringBuilder();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"))
                            .append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String getJsonData(String jsonParam, String urls) {
        StringBuffer sb = new StringBuffer();
        try {
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 写入请求的字符串
            out.write((jsonParam).getBytes());
            out.flush();
            out.close();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                InputStream in1 = conn.getInputStream();
                try {
                    String readLine;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
