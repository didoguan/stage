package com.deepspc.stage.core.utils;

import cn.hutool.core.util.StrUtil;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 条形码工具类
 * @author gzw
 * @date 2021/4/20 14:57
 */
public class BarCodeUtils {

    private double QUIET_ZONE = 4.0;

    /**
     * 生成128型条形码
     * @param msg 条码内容
     * @param filePath 图片路径
     * @throws IOException
     */
    public static void barcode39Pic(String msg, String filePath) throws IOException {
        if (StrUtil.isBlank(msg) || StrUtil.isBlank(filePath)) {
            return;
        }
        Code39Bean bean = new Code39Bean();
        //精细度
        final int dpi = 150;
        //宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        //msg高度
        bean.setBarHeight(10);
        //两侧留白
        bean.doQuietZone(true);
        String format = "image/png";
        OutputStream os = null;

        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
            File pic = new File(file, fileName);
            pic.createNewFile();
        }
        try {
            os = new FileOutputStream(new File(filePath));
            //输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(os, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            //生成条形码
            bean.generateBarcode(canvas, msg);
            //添加额外的条形码描述
            //canvas.deviceText("商品名称", 4.0, 4.0, 5.0 - 1, null, 4, TextAlignment.TA_CENTER);
            canvas.finish();
        } finally {
            if (null != os) {
                os.close();
            }
        }
    }

}
