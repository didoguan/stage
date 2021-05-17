package com.deepspc.stage.core.utils;

import cn.hutool.core.util.StrUtil;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author gzw
 * @date 2021/5/15 15:46
 */
public class PrintUtil {

    /**
     * 打印图片
     * @param filePath 要打印的文件名称及路径
     * @param width 打印的宽度(毫米)
     * @param height 打印的高度(毫米)
     * @throws FileNotFoundException
     * @throws PrintException
     */
    public static void printImage(String filePath, int width, int height) throws IOException, PrintException {
        if (StrUtil.isBlank(filePath)) {
            return;
        }
        FileInputStream fis;
        DocFlavor dfl;
        if (filePath.endsWith(".jpg")) {
            dfl = DocFlavor.INPUT_STREAM.JPEG;
        } else if (filePath.endsWith(".png")) {
            dfl = DocFlavor.INPUT_STREAM.PNG;
        } else if (filePath.endsWith(".gif")) {
            dfl = DocFlavor.INPUT_STREAM.GIF;
        } else {
            throw new PrintException("文件格式只能是图片");
        }

        //获取默认的打印服务(提供服务的打印机)
        PrintService prints[] = PrintServiceLookup.lookupPrintServices(dfl, null);
        PrintService print = null;
        for (PrintService ps : prints) {
            if (ps.getName().contains("DL-886A")) {
                print = ps;
                break;
            }
        }
        if (null == print) {
            throw new PrintException("找不到相关打印机");
        }
        //构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(OrientationRequested.PORTRAIT);
        pras.add(PrintQuality.HIGH);
        pras.add(Chromaticity.MONOCHROME);
        DocAttributeSet das = new HashDocAttributeSet();
        //设置打印大小(毫米为单位)
        das.add(new MediaPrintableArea(0, 0, width, height, MediaPrintableArea.MM));
        fis = new FileInputStream(filePath);
        Doc doc = new SimpleDoc(fis, dfl, das);
        DocPrintJob job = print.createPrintJob();
        //执行打印
        job.print(doc, pras);
        fis.close();
    }

    /**
     * 打印图片，默认打印40x40毫米
     * @param filePath 要打印的文件名称及路径
     * @throws FileNotFoundException
     * @throws PrintException
     */
    public static void printImage60(String filePath) throws IOException, PrintException {
        printImage(filePath, 39, 43);
    }

    public static void main(String[] args) {
        try {
            printImage60("D:\\esmanager\\goods\\barcode\\1394205821937442818.png");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }
}
