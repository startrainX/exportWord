package com.example.mystudydemo.utils;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/7/28 9:20
 * @description:
 */

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("D:\\test.docx"));
            //段落
            XWPFParagraph paragraph = document.createParagraph();
            //具有相同属性的一个区域。
            XWPFRun run = paragraph.createRun();
            run.setBold(true); //加粗
            run.setText("测试");
            run.setColor("FF0000");
            run = paragraph.createRun();
            run.setText("背景色");
            run.getCTR().addNewRPr().addNewHighlight().setVal(STHighlightColor.YELLOW);
            run = paragraph.createRun();
            CTShd shd = run.getCTR().addNewRPr().addNewShd();
            shd.setFill("17365D");
            run.setText("底纹");
            document.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}