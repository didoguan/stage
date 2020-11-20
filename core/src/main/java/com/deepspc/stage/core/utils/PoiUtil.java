package com.deepspc.stage.core.utils;

import com.deepspc.stage.core.annotation.ExcelImport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * POI导入导出处理
 * @author gzw
 * @date 2020/8/5 17:00
 */
public class PoiUtil {

    private final String Excel_2003 = ".xls"; //2003 版本的excel
    private final String Excel_2007 = ".xlsx"; //2007 版本的excel


    /**
     * 描述：根据文件后缀，自动适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     * */
    @Deprecated
    public Workbook getWorkbook(InputStream inStr,String fileName) throws Exception {
        Workbook work = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(Excel_2003.equals(fileType)){
            work=new HSSFWorkbook(inStr);//2003 版本的excel
        }else if(Excel_2007.equals(fileType)) {
            work=new XSSFWorkbook(inStr);//2007 版本的excel
        }else {
            throw new Exception("解析文件格式有误！");
        }

        return work;
    }

    /**
     * 创建workbook
     * @param is
     * @param fileName
     * @return
     * @throws IOException
     */
    private Workbook createWorkbook(InputStream is, String fileName) throws IOException {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(Excel_2003.equals(fileType)){
            return new HSSFWorkbook(is);
        } else if (Excel_2007.equals(fileType)) {
            return new XSSFWorkbook(is);
        } else {
            return null;
        }
    }

    /**
     * 根据导入的excel转换对应的实体数据
     * @param file
     * @param clazz
     * @param <T> 传入的实体必须添加ExcelImport注解
     * @param startRow 需要读取数据的开始行
     * @return
     */
    public <T> List<T> tranformExcelData(MultipartFile file, Class<T> clazz, int startRow) throws IOException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (null == file) {
            return null;
        }
        Map<String, Method> methods = new HashMap<>();
        String fileName = file.getOriginalFilename();
        InputStream is = file.getInputStream();
        Workbook work = createWorkbook(is, fileName);
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields( ));
        for (Field field : fields) {
            ExcelImport excelImport = field.getAnnotation(ExcelImport.class);
            if (null == excelImport) {
                continue;
            }
            Method method = clazz.getDeclaredMethod(excelImport.setMethod(), excelImport.paramType());
            methods.put(excelImport.colIndex() + "", method);
        }
        Sheet sheet = work.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();
        Row first = sheet.getRow(0);
        int totalCells = first.getPhysicalNumberOfCells();

        List<T> datas = new ArrayList<>();
        flag : for (int i = startRow; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            if (null == row) {
                continue;
            }
            T obj = clazz.newInstance();
            for (int j = 0; j < totalCells; j++) {
                Cell cell = row.getCell(j);

                Object value = getCellValue(cell);
                if (j == 0 && value == null) {
                    break flag;
                }
                Method method = methods.get(j + "");
                if (null != method) {
                    method.invoke(obj, value);
                }
            }
            datas.add(obj);
        }
        is.close();

        return datas;
    }

    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     * */
    public Object getCellValue(Cell cell) {
        DecimalFormat df1 = new DecimalFormat("0");//格式化number，string字符
        SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");//日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");//格式化数字
        if(cell !=null && !"".equals(cell)) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getRichStringCellValue().getString();
                case NUMERIC:
                    if("General".equals(cell.getCellStyle().getDataFormatString())) {
                        return df1.format(cell.getNumericCellValue());
                    } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        return sdf.format(cell.getDateCellValue());
                    } else {
                        return cell.getNumericCellValue();
                    }
                case BOOLEAN:
                    Boolean isValue = cell.getBooleanCellValue();
                    return isValue.toString();
                case BLANK:
                    return null;
                case FORMULA:
                    return cell.getNumericCellValue();
            }
        }
        return null;
    }



    /**
     * 通过模板导入excel
     * @param templatePath 模板文件名称及路径
     * @param datas 要导出的数据
     */
    public static ByteArrayOutputStream exportExcelByTemplate(String templatePath, String[] cols, List<Map<String, Object>> datas) throws IOException {
        Resource resource = new ClassPathResource(templatePath);
        boolean isFile = resource.isFile();
        if (!isFile) {
            return null;
        }
        if (null == datas || datas.isEmpty()) {
            return null;
        }
        boolean hasCols = false;
        if (null != cols && cols.length > 0) {
            hasCols = true;
        }
        String path = resource.getFile().getPath();
        InputStream fis = new FileInputStream(path);
        Workbook workbook = WorkbookFactory.create(fis);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //第一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        int rowIndex = sheet.getLastRowNum();
        int i;
        Object value;
        String val;
        for (Map<String, Object> data : datas) {
            rowIndex++;
            Row row = sheet.createRow(rowIndex);
            //设置列数据
            i = 0;
            if (hasCols) {
                for (; i < cols.length; i++) {
                    value = data.get(cols[i]);
                    if (null != value) {
                        val = value.toString();
                    } else {
                        val = "";
                    }
                    row.createCell(i).setCellValue(val);
                }
            } else {
                for (Map.Entry<String, Object> map : data.entrySet()) {
                    value = map.getValue();
                    if (null != value) {
                        val = value.toString();
                    } else {
                        val = "";
                    }
                    row.createCell(i).setCellValue(val);
                    i++;
                }
            }
        }
        workbook.write(out);
        workbook.close();
        fis.close();
        return out;
    }
}
