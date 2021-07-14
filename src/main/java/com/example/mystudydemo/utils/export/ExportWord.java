package com.example.mystudydemo.utils.export;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description 导出word文档
 * @Author  Huangxiaocong
 * 2018年12月1日  下午12:12:15
 */
public  class ExportWord {
    private XWPFHelperTable xwpfHelperTable = null;
    private XWPFHelper xwpfHelper = null;
    public static Integer tableSize=0;
    public ExportWord() {
        xwpfHelperTable = new XWPFHelperTable();
        xwpfHelper = new XWPFHelper();
    }
    /**
     * 创建好文档的基本 标题，表格  段落等部分
     * @return
     * @Author Huangxiaocong 2018年12月16日
     */
    public void createXWPFDocument(XWPFDocument doc, int row,int colunm,String title,ParagraphAlignment paragraphAlignment) {
        createTitleParagraph(doc, title,paragraphAlignment);
        createTableParagraphMobel(doc, row, colunm);
    }
    /**
     * 创建表格的标题样式
     * @param document
     * @Author Huangxiaocong 2018年12月16日 下午5:28:38
     */
    public void createTitleParagraph(XWPFDocument document,String title,ParagraphAlignment paragraphAlignment) {
        XWPFParagraph titleParagraph = document.createParagraph();    //新建一个标题段落对象（就是一段文字）
        addCustomHeadingStyle(document,"标题2",2);
        titleParagraph.setStyle("标题2");
        titleParagraph.setAlignment(paragraphAlignment);//样式
        XWPFRun titleFun = titleParagraph.createRun();    //创建文本对象
        titleFun.setText(title); //设置标题的名字
/*
        titleFun.setBold(true); //加粗
*/
        titleFun.setColor("000000");//设置颜色
        titleFun.setFontSize(15);    //字体大小
//        titleFun.setFontFamily("");//设置字体
        //...
    }
    /**
     * 设置表格
     * @param document
     * @param rows
     * @param cols
     * @Author Huangxiaocong 2018年12月16日
     */
    public void createTableParagraph(XWPFDocument document, int rows, int cols) {
//        xwpfHelperTable.createTable(xdoc, rowSize, cellSize, isSetColWidth, colWidths)
        XWPFTable infoTable = document.createTable(rows, cols);
        xwpfHelperTable.setTableWidthAndHAlign(infoTable, "9072", STJc.CENTER);
        //合并表格
        xwpfHelperTable.mergeCellsHorizontal(infoTable, 0, 1, 3);
        xwpfHelperTable.mergeCellsHorizontal(infoTable, 0, 4, 6);
        xwpfHelperTable.mergeCellsHorizontal(infoTable, 0, 7, 9);

        xwpfHelperTable.mergeCellsVertically(infoTable, 0, 0, 1);
  /*      for(int col = 1; col < 10; col++) {
            xwpfHelperTable.mergeCellsHorizontal(infoTable, col, 1, 2);
        }*/
        //设置表格样式
        List<XWPFTableRow> rowList = infoTable.getRows();
        for(int i = 0; i < rowList.size(); i++) {
            XWPFTableRow infoTableRow = rowList.get(i);
            List<XWPFTableCell> cellList = infoTableRow.getTableCells();
            for(int j = 0; j < cellList.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphRun = cellParagraph.createRun();
                cellParagraphRun.setFontSize(12);
                if(i % 2 != 0) {
                    cellParagraphRun.setBold(true);
                }
            }
        }
        xwpfHelperTable.setTableHeight(infoTable, 560, STVerticalJc.CENTER);
    }

    public void createTableParagraphZdtj(XWPFDocument document, int rows, int cols, LinkedHashMap<String, Integer> map) {
//        xwpfHelperTable.createTable(xdoc, rowSize, cellSize, isSetColWidth, colWidths)
        XWPFTable infoTable = document.createTable(rows, cols);
        xwpfHelperTable.setTableWidthAndHAlign(infoTable, "9072", STJc.CENTER);
        //合并表格
        for(int i=2;i<cols-1;i=i+2){
            xwpfHelperTable.mergeCellsHorizontal(infoTable, 0, i, i+1);
        }
        xwpfHelperTable.mergeCellsVertically(infoTable, 0, 0, 1);
        int x=2;
        for (String s : map.keySet()) {
            xwpfHelperTable.mergeCellsVertically(infoTable, 0, x, x+map.get(s));
            x=x+map.get(s)+1;
        }
  /*      for(int col = 1; col < 10; col++) {
            xwpfHelperTable.mergeCellsHorizontal(infoTable, col, 1, 2);
        }*/
        //设置表格样式
        List<XWPFTableRow> rowList = infoTable.getRows();
        for(int i = 0; i < rowList.size(); i++) {
            XWPFTableRow infoTableRow = rowList.get(i);
            List<XWPFTableCell> cellList = infoTableRow.getTableCells();
            for(int j = 0; j < cellList.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphRun = cellParagraph.createRun();
                cellParagraphRun.setFontSize(12);
                if(i % 2 != 0) {
                    cellParagraphRun.setBold(true);
                }
            }
        }
        xwpfHelperTable.setTableHeight(infoTable, 560, STVerticalJc.CENTER);
    }

    public void createTableParagraphDldm(XWPFDocument document, int rows, int cols) {
//        xwpfHelperTable.createTable(xdoc, rowSize, cellSize, isSetColWidth, colWidths)
        XWPFTable infoTable = document.createTable(rows, cols);
        xwpfHelperTable.setTableWidthAndHAlign(infoTable, "9072", STJc.CENTER);
        //合并表格
        for(int i=1;i<cols;i=i+2){
            xwpfHelperTable.mergeCellsHorizontal(infoTable, 0, i, i+1);
        }
        xwpfHelperTable.mergeCellsVertically(infoTable, 0, 0, 1);

        //设置表格样式
        List<XWPFTableRow> rowList = infoTable.getRows();
        for(int i = 0; i < rowList.size(); i++) {
            XWPFTableRow infoTableRow = rowList.get(i);
            List<XWPFTableCell> cellList = infoTableRow.getTableCells();
            for(int j = 0; j < cellList.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphRun = cellParagraph.createRun();
                cellParagraphRun.setFontSize(12);
                if(i % 2 != 0) {
                    cellParagraphRun.setBold(true);
                }
            }
        }
        xwpfHelperTable.setTableHeight(infoTable, 560, STVerticalJc.CENTER);
    }
    /**
     * 设置表格
     * @param document
     * @param rows
     * @param cols
     * @Author Huangxiaocong 2018年12月16日
     */
    public void createTableParagraphMobel(XWPFDocument document, int rows, int cols) {
//        xwpfHelperTable.createTable(xdoc, rowSize, cellSize, isSetColWidth, colWidths)
        XWPFTable infoTable = document.createTable(rows, cols);
        xwpfHelperTable.setTableWidthAndHAlign(infoTable, "9072", STJc.CENTER);
        //合并表格

        //设置表格样式
        List<XWPFTableRow> rowList = infoTable.getRows();
        for(int i = 0; i < rowList.size(); i++) {
            XWPFTableRow infoTableRow = rowList.get(i);
            List<XWPFTableCell> cellList = infoTableRow.getTableCells();
            for(int j = 0; j < cellList.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphRun = cellParagraph.createRun();
                cellParagraphRun.setFontSize(12);
                if(i==0) {
                    cellParagraphRun.setBold(true);
                }
            }
        }
        xwpfHelperTable.setTableHeight(infoTable, 560, STVerticalJc.CENTER);
    }
    /**
     * 设置表格
     * @param document
     * @param rows
     * @param cols
     * @Author Huangxiaocong 2018年12月16日
     */
    public void createTableParagraphDemo(XWPFDocument document, int rows, int cols) {
//        xwpfHelperTable.createTable(xdoc, rowSize, cellSize, isSetColWidth, colWidths)
        XWPFTable infoTable = document.createTable(rows, cols);
        xwpfHelperTable.setTableWidthAndHAlign(infoTable, "9072", STJc.CENTER);
        //合并表格
        xwpfHelperTable.mergeCellsHorizontal(infoTable, 1, 1, 5);
        xwpfHelperTable.mergeCellsVertically(infoTable, 0, 3, 6);
        for(int col = 3; col < 7; col++) {
            xwpfHelperTable.mergeCellsHorizontal(infoTable, col, 1, 5);
        }
        //设置表格样式
        List<XWPFTableRow> rowList = infoTable.getRows();
        for(int i = 0; i < rowList.size(); i++) {
            XWPFTableRow infoTableRow = rowList.get(i);
            List<XWPFTableCell> cellList = infoTableRow.getTableCells();
            for(int j = 0; j < cellList.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphRun = cellParagraph.createRun();
                cellParagraphRun.setFontSize(12);
                if(i % 2 != 0) {
                    cellParagraphRun.setBold(true);
                }
            }
        }
        xwpfHelperTable.setTableHeight(infoTable, 560, STVerticalJc.CENTER);
    }

    /**
     * 往表格中填充数据
     * @param dataList
     * @param document
     * @throws IOException
     * @Author Huangxiaocong 2018年12月16日
     */
    @SuppressWarnings("unchecked")
    public void exportCheckWord(Map<String, Object> dataList, XWPFDocument document, String savePath) throws IOException {
        int i=0;
        for (String s : dataList.keySet()) {
            List<List<Object>> tableData = (List<List<Object>>) dataList.get(s);
            XWPFTable table = document.getTableArray(i);
            fillTableData(table, tableData);
            i++;

        }
        /*xwpfHelper.saveDocument(document, savePath);*/
    }
    /**
     * 往表格中填充数据
     * @param table
     * @param tableData
     * @Author Huangxiaocong 2018年12月16日
     */
    public void fillTableData(XWPFTable table, List<List<Object>> tableData) {
        List<XWPFTableRow> rowList = table.getRows();
        for(int i = 0; i < tableData.size(); i++) {
            List<Object> list = tableData.get(i);
            List<XWPFTableCell> cellList = rowList.get(i).getTableCells();
            for(int j = 0; j < list.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                XWPFRun cellParagraphRun = cellParagraph.getRuns().get(0);
                cellParagraphRun.setText(String.valueOf(list.get(j)));
            }
        }
    }
    /**
     * 写入图片
     * @param document
     * @param picName
     * @param width
     * @param height
     * @param alignment
     */
    public void WriteImage(CustomXWPFDocument document, String picName, int width, int height, ParagraphAlignment alignment) {
        try {
/*
            CustomXWPFDocument document= new CustomXWPFDocument();
*/
            XWPFParagraph imgPara = document.createParagraph();
            imgPara.setAlignment(alignment);
            int format = XWPFDocument.PICTURE_TYPE_PNG;
            if(picName.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
            else if(picName.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
            else if(picName.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
            else if(picName.endsWith(".jpeg") || picName.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
            else if(picName.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
            else if(picName.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
            else if(picName.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
            else if(picName.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
            else if(picName.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
            else if(picName.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
            else if(picName.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
            XWPFRun stdMarkRun = imgPara.createRun();
            stdMarkRun.addBreak();

            String blipId = imgPara.getDocument().addPictureData(new FileInputStream(new File(picName)),format);
            document.createPicture(blipId,document.getNextPicNameNumber(format),width,height, imgPara);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPicture(String blipId, int id, int width, int height,XWPFParagraph paragraph) {
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        //String blipId = getAllPictures().get(id).getPackageRelationship().getId();
        CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();

        String picXml =
                "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "         <pic:nvPicPr>" +
                "            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                "            <pic:cNvPicPr/>" +
                "         </pic:nvPicPr>" +
                "         <pic:blipFill>" +
                "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                "            <a:stretch>" +
                "               <a:fillRect/>" +
                "            </a:stretch>" +
                "         </pic:blipFill>" +
                "         <pic:spPr>" +
                "            <a:xfrm>" +
                "               <a:off x=\"0\" y=\"0\"/>" +
                "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                "            </a:xfrm>" +
                "            <a:prstGeom prst=\"rect\">" +
                "               <a:avLst/>" +
                "            </a:prstGeom>" +
                "         </pic:spPr>" +
                "      </pic:pic>" +
                "   </a:graphicData>" +
                "</a:graphic>";

        // CTGraphicalObjectData graphicData =
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture" + id);
        docPr.setDescr("Generated");
    }
    public void setTitleStyle(XWPFRun run,String text){
        run.setFontSize(14);
        run.setBold(true);
        run.setText(text);
    }

    /**
     * 设置标题
     * @param docxDocument
     * @param strStyleId
     * @param headingLevel
     */
    private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

}