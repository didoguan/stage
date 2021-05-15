package com.deepspc.stage.core.utils;

import cn.hutool.core.util.StrUtil;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    public static void printImage(String filePath, int width, int height) throws FileNotFoundException, PrintException {
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
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(OrientationRequested.PORTRAIT);
        //打印一个副本
        pras.add(new Copies(1));
        pras.add(PrintQuality.HIGH);
        DocAttributeSet das = new HashDocAttributeSet();
        //设置打印大小(毫米为单位)
        das.add(new MediaPrintableArea(0, 0, width, height, MediaPrintableArea.MM));
        fis = new FileInputStream(filePath);
        Doc doc = new SimpleDoc(fis, dfl, das);
        DocPrintJob job = ps.createPrintJob();
        //执行打印
        job.print(doc, pras);
    }

    /**
     * 打印图片，默认打印60x60毫米
     * @param filePath 要打印的文件名称及路径
     * @throws FileNotFoundException
     * @throws PrintException
     */
    public static void printImage60(String filePath) throws FileNotFoundException, PrintException {
        printImage(filePath, 60, 60);
    }
}
