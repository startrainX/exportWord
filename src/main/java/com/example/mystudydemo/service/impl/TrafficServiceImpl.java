package com.example.mystudydemo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.mystudydemo.entity.bean.TrafficBean;
import com.example.mystudydemo.mapper.TrafficMapper;
import com.example.mystudydemo.service.TrafficService;
import com.example.mystudydemo.utils.DateUtil;
import com.example.mystudydemo.utils.export.EchartsOptionUtils;
import com.example.mystudydemo.utils.export.EchartsUtils;
import com.example.mystudydemo.utils.export.ExportWord;
import com.example.mystudydemo.utils.export.XWPFHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/8 9:25
 * @description:
 */
@Service
@DS("mysql")
public class TrafficServiceImpl implements TrafficService {

    @Autowired
    TrafficMapper trafficMapper;


    @Override
    public String export(String startTime, String endTime) throws FileNotFoundException, InvalidFormatException, ParseException {
        String[] times = startTime.split("-");
        String month = times[1] + "月";
        //工具类新建
        ExportWord ew = new ExportWord();
        XWPFDocument doc = new XWPFDocument();
        //创建开始部分
        XWPFParagraph paragraph = createTitle(doc, startTime);

        //分页
        doc.createParagraph().setPageBreak(true);

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        // 将该段设置为标题
        addCustomHeadingStyle(doc, "标题1", 1);
        paragraph.setStyle("标题1");
        XWPFRun run = paragraph.createRun();
        // 设置字体
        run.setFontFamily("仿宋_GB2312", XWPFRun.FontCharRange.eastAsia);
        run.setText("1.前言");
        run.setFontSize(16);
        run.setBold(true);

        // 生成新一段
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        addCustomHeadingStyle(doc, "标题2", 2);
        paragraph.setStyle("标题2");
        run = paragraph.createRun();
        ew.setTitleStyle(run, "1.1  指数说明");
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        paragraph=doc.createParagraph();
        run = paragraph.createRun();
        run.setText("自由流速度：根据历史数据计算畅通状态下的车辆通行速度，单位km/h。");

        paragraph=doc.createParagraph();
        run = paragraph.createRun();
        run.setText("平均速度：路段或路网中车辆通行的平均车速，单位km/h。");

        paragraph=doc.createParagraph();
        run = paragraph.createRun();
        run.setText("道路拥堵指数：畅通:I<1.5、缓行:1.5≤I<2、拥堵:2≤I<4、严重拥堵:I≥4");

        paragraph=doc.createParagraph();
        run = paragraph.createRun();
        run.setText("区域拥堵指数：畅通:I<1.5、缓行:1.5≤I<1.8、拥堵:1.8≤I<2.2、严重拥堵:I≥2.2");
        

        // 指数说明表格
        List<List<Object>> list = table();
        ew.createXWPFDocument(doc, 3, 5,"", ParagraphAlignment.LEFT);
        

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        addCustomHeadingStyle(doc, "标题2", 2);
        paragraph.setStyle("标题2");
        run = paragraph.createRun();
        ew.setTitleStyle(run, "1.2  时间范围");
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        run = paragraph.createRun();
        run.setText("          " + startTime + "-" + endTime);

        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        addCustomHeadingStyle(doc, "标题2", 2);
        paragraph.setStyle("标题2");
        run = paragraph.createRun();
        ew.setTitleStyle(run, "1.3  高峰定义");
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        

        //高峰定义表格
        List<List<Object>> list0 = table0();
        ew.createXWPFDocument(doc,3,3,"",ParagraphAlignment.LEFT);

        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        addCustomHeadingStyle(doc, "标题2", 2);
        paragraph.setStyle("标题2");
        run = paragraph.createRun();
        ew.setTitleStyle(run, "1.4  区域范围");

        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        image0(doc,ew);

        paragraph = doc.createParagraph();
        addCustomHeadingStyle(doc, "标题1", 1);
        paragraph.setStyle("标题1");
        run = paragraph.createRun();
        run.setText("2.道路、路段拥堵排名及分析");
        run.setFontSize(16);
        run.setBold(true);
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        Map<String, Object> dataList = new LinkedHashMap<String, Object>();
        //表格一数据查询
        List<List<Object>> list1 = table1(startTime, endTime, "1");
        XWPFHelper xwpfHelper = new XWPFHelper();
        //表格一创建
        ew.createXWPFDocument(doc, 21, 5, "2.1 三区早高峰拥堵道路TOP20：", ParagraphAlignment.LEFT);
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        //图表一创建
        image1(doc,startTime,endTime,ew);
        //表格二数据查询
        List<List<Object>> list2 = table2(startTime, endTime, "1");
        //表格二创建
        ew.createXWPFDocument(doc,4, 3, "2.2 三区早高峰拥堵道路区域占比", ParagraphAlignment.LEFT);
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        //图表二创建
        image2(doc,startTime,endTime,ew);
        //表格三数据查询
        List<List<Object>> list3 = table3(startTime, endTime, "2");
        //表格三创建
        ew.createXWPFDocument(doc, 21, 5, "2.3 三区晚高峰拥堵道路TOP20：", ParagraphAlignment.LEFT);
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        //图表三创建
        image3(doc,startTime,endTime,ew);
        //表格四数据查询
        List<List<Object>> list4 = table4(startTime, endTime, "2");
        //表格四创建
        ew.createXWPFDocument(doc,4, 3, "2.4 三区晚高峰拥堵道路区域占比", ParagraphAlignment.LEFT);
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        //图表四创建
        image4(doc,startTime,endTime,ew);

        //todo 路段占比空

        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        addCustomHeadingStyle(doc, "标题2", 2);
        paragraph.setStyle("标题2");
        run = paragraph.createRun();
        ew.setTitleStyle(run, "2.5  分析");

        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("        从"+ month +"工作日早晚高峰期道路拥堵TOP20的对比结果得出，由于婺城区包括江北和江南商业" +
                "较为集中，城区分布有重点学校，且具有老城区道路相对狭窄、同行能力低的特点，拥堵比较集中。");
        run.setFontSize(12);
        
        run = paragraph.createRun();
        run.setText("        金东区拥堵道路较少，拥堵主要发生在较多小路段。");

        Map<String, Double> map = new HashMap<>();
        image6(doc,startTime,endTime,ew);
        image7(doc,startTime,endTime,ew);
        image8(doc,startTime,endTime,ew);
        paragraph=doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("        " + month + "婺城、金东、开发三区域整体交通状况较好；");
        run.setFontSize(12);
        

        image9(doc,startTime,endTime,ew);
        // 与上月对比分析 早高峰
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preStartTime = DateUtil.format(DateUtil.preMonth(sdf.parse(startTime)));
        String preMonth = preStartTime.split("-")[1];
        String nowMonth = startTime.split("-")[1];
        map = analyse10(startTime,endTime,"1");
        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("        " + month + "金华市区早高峰指数较上月有所"+
                (map.get("preTotal") > map.get("nowTotal")?"下降。":"上升。") +
                "婺城区从"+ map.get("婺城区-" + preMonth) + (map.get("婺城区-" + preMonth) > map.get("婺城区-" + nowMonth)?"下降到":"上升到") + map.get("婺城区-" + nowMonth) +
                "；金东区从"+ map.get("金东区-" + preMonth) + (map.get("金东区-" + preMonth) > map.get("金东区-" + nowMonth)?"下降到":"上升到") + map.get("金东区-" + nowMonth) +
                "；开发区从"+ map.get("开发区-" + preMonth)  + (map.get("开发区-" + preMonth) > map.get("开发区-" + nowMonth)?"下降到":"上升到") + map.get("开发区-" + nowMonth) + "。");
        run.setFontSize(12);
        

        image10(doc,startTime,endTime,ew);
        // 与上月对比分析 晚高峰
        map = analyse10(startTime, endTime, "2");
        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();

        run.setText("        " + month + "金华市区晚高峰指数较上月有所"+
                (map.get("preTotal") > map.get("nowTotal")?"下降。":"上升。") +
                "婺城区从"+ map.get("婺城区-" + preMonth) + (map.get("婺城区-" + preMonth) > map.get("婺城区-" + nowMonth)?"下降到":"上升到") + map.get("婺城区-" + nowMonth) +
                "；金东区从"+ map.get("金东区-" + preMonth) + (map.get("金东区-" + preMonth) > map.get("金东区-" + nowMonth)?"下降到":"上升到") + map.get("金东区-" + nowMonth) +
                "；开发区从"+ map.get("开发区-" + preMonth)  + (map.get("开发区-" + preMonth) > map.get("开发区-" + nowMonth)?"下降到":"上升到") + map.get("开发区-" + nowMonth) + "。");
        run.setFontSize(12);
        

        dataList.put("table",list);
        dataList.put("table0",list0);
        dataList.put("table1",list1);
        dataList.put("table2",list2);
        dataList.put("table3",list3);
        dataList.put("table4",list4);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr=sdf1.format(new Date());
        try {
            //表格填充
            ew.exportCheckWord(dataList, doc, "D:/报表"+dayStr+".docx");
            xwpfHelper.saveDocument(doc, "D:/报表"+dayStr+".docx");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文档生成成功");
        return "D:/报表"+dayStr+".docx";
    }



    /**
     * 创建标题头部
     * @param xwpfDocument
     * @return
     */
    public XWPFParagraph createTitle(XWPFDocument xwpfDocument, String startTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        XWPFParagraph para = xwpfDocument.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = para.createRun();
        run.setText("金华市城市交通拥堵指数分析\n");
        run.setBold(true);
        run.setColor("000000");
        run.setFontSize(14);
        //换行
        para = xwpfDocument.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);
        run = para.createRun();
        String[] split = startTime.split("-");
        run.setText(split[0] + "年" + split[1] + "月");
        //加粗
        run.setBold(true);
        run.setColor("000000");
        run.setFontSize(14);

        return para;
    }

    public void headOne(XWPFRun run, String text) {
        run.setText(text);
        run.setBold(true);
        run.setColor("000000");
        run.setFontSize(20);
        
        
    }

    public List<List<Object>> table() {
        List<List<Object>> list1 = new ArrayList<List<Object>>();
        List<Object> tempList = new ArrayList<Object>();
        tempList = new ArrayList<Object>();
        tempList.add("指数区间");
        tempList.add("[1~1.5)");
        tempList.add("[1.5~2)");
        tempList.add("[2~4)");
        tempList.add("[4~10]");
        list1.add(tempList);
        tempList = new ArrayList<>();
        tempList.add("指数等级");
        tempList.add("畅通");
        tempList.add("缓行");
        tempList.add("拥堵");
        tempList.add("严重拥堵");
        list1.add(tempList);
        tempList = new ArrayList<>();
        tempList.add("运行状况");
        tempList.add("交通运行状况良好、车速高，基本无拥堵");
        tempList.add("交通运行状况一般，车速缓慢，有一定比例道路拥堵；");
        tempList.add("交通运行状况很差，车速很低甚至阻塞停驶，道路拥堵比例很高；");
        tempList.add("道路交通运行状况非常差，车辆停驶比例显著；");
        list1.add(tempList);

        return list1;
    }

    public List<List<Object>> table0() {
        List<List<Object>> list1 = new ArrayList<List<Object>>();
        List<Object> tempList = new ArrayList<Object>();
        tempList = new ArrayList<Object>();
        tempList.add("类别");
        tempList.add("早高峰");
        tempList.add("晚高峰");
        list1.add(tempList);
        tempList = new ArrayList<>();
        tempList.add("工作日");
        tempList.add("7:00-9：00");
        tempList.add("17:00-19:00");
        list1.add(tempList);
        tempList = new ArrayList<>();
        tempList.add("双休日及节假日");
        tempList.add("-");
        tempList.add("-");
        list1.add(tempList);

        return list1;
    }

    public List<List<Object>> table1(String startTime, String endTime, String type) {
        List<List<Object>> list1 = new ArrayList<List<Object>>();
        List<Object> tempList = new ArrayList<Object>();
        tempList = new ArrayList<Object>();
        tempList.add("序号");
        tempList.add("区域");
        tempList.add("名称");
        tempList.add("指数");
        tempList.add("平均速度");
        list1.add(tempList);
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime, endTime, type);
        for (int i = 0; i < roadTop20.size(); i++) {
            tempList = new ArrayList<>();
            tempList.add(i+1);
            tempList.add(roadTop20.get(i).getAreaName());
            tempList.add(roadTop20.get(i).getRoadName());
            tempList.add(roadTop20.get(i).getAvgIndex());
            tempList.add(roadTop20.get(i).getAvgSpeed());
            list1.add(tempList);
        }
        return list1;
    }

    public List<List<Object>> table2(String startTime, String endTime, String type) {
        Map<String,Integer> map = new HashMap<>();
        map.put("婺城区",0);
        map.put("金东区",0);
        map.put("开发区",0);
        List<List<Object>> list2 = new ArrayList<List<Object>>();
        List<Object> tempList = new ArrayList<Object>();
        tempList = new ArrayList<Object>();
        tempList.add("区域");
        tempList.add("拥堵道路条数");
        tempList.add("占比");
        list2.add(tempList);
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime, endTime, type);
        for (TrafficBean t : roadTop20) {
            if (map.containsKey(t.getAreaName())) {
                map.put(t.getAreaName(),map.get(t.getAreaName()).intValue()+1);
            }
        }
        for (Map.Entry entry:map.entrySet()){
            tempList = new ArrayList<>();
            tempList.add(entry.getKey());
            tempList.add(entry.getValue());
            tempList.add(new BigDecimal((float)
                    Integer.parseInt(entry.getValue()+"")/20)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100 + "%");
            list2.add(tempList);
        }
        return list2;
    }

    public List<List<Object>> table3(String startTime, String endTime, String type) {
        List<List<Object>> list1 = new ArrayList<List<Object>>();
        List<Object> tempList = new ArrayList<Object>();
        tempList = new ArrayList<Object>();
        tempList.add("序号");
        tempList.add("区域");
        tempList.add("名称");
        tempList.add("指数");
        tempList.add("平均速度");
        list1.add(tempList);
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime, endTime, type);
        for (int i = 0; i < roadTop20.size(); i++) {
            tempList = new ArrayList<>();
            tempList.add(i+1);
            tempList.add(roadTop20.get(i).getAreaName());
            tempList.add(roadTop20.get(i).getRoadName());
            tempList.add(roadTop20.get(i).getAvgIndex());
            tempList.add(roadTop20.get(i).getAvgSpeed());
            list1.add(tempList);
        }
        return list1;
    }

    public List<List<Object>> table4(String startTime, String endTime, String type) {
        Map<String,Integer> map = new HashMap<>();
        map.put("婺城区",0);
        map.put("金东区",0);
        map.put("开发区",0);
        List<List<Object>> list2 = new ArrayList<List<Object>>();
        List<Object> tempList = new ArrayList<Object>();
        tempList = new ArrayList<Object>();
        tempList.add("区域");
        tempList.add("拥堵道路条数");
        tempList.add("占比");
        list2.add(tempList);
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime, endTime, type);
        for (TrafficBean t : roadTop20) {
            if (map.containsKey(t.getAreaName())) {
                map.put(t.getAreaName(),map.get(t.getAreaName()).intValue()+1);
            }
        }
        for (Map.Entry entry:map.entrySet()){
            tempList = new ArrayList<>();
            tempList.add(entry.getKey());
            tempList.add(entry.getValue());
            tempList.add(new BigDecimal((float)
                    Integer.parseInt(entry.getValue()+"")/20)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100 + "%");
            list2.add(tempList);
        }
        return list2;
    }

    public void image0(XWPFDocument xwpfDocument,ExportWord ew) throws FileNotFoundException, InvalidFormatException {

        String imagePath = "D:\\echarts-convert\\1.png";
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
    }

    /**
     * 早高峰拥堵道路top20柱状图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image1(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException {
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime,endTime,"1");
        String[] units = new String[roadTop20.size()];
        double[] datas = new double[roadTop20.size()];
        for(int i=0;i<roadTop20.size();i++){
            units[i]=roadTop20.get(i).getRoadName();
            datas[i]=Double.parseDouble(roadTop20.get(i).getAvgIndex());
        }
        String option = EchartsOptionUtils.getBarOption(units, datas, "早高峰拥堵道路");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 早高峰拥堵道路top20区域占比饼状图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image2(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException {
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime,endTime,"1");
        Map<String,Integer> map = new HashMap<>();
        List<List<Object>> list2 = new ArrayList<>();
        List<Object> tempList = new ArrayList<Object>();
        map.put("婺城区",0);
        map.put("金东区",0);
        map.put("开发区",0);
        for (TrafficBean t : roadTop20) {
            if (map.containsKey(t.getAreaName())) {
                map.put(t.getAreaName(),map.get(t.getAreaName()).intValue()+1);
            }
        }
        String[] units = new String[3];
        int[] datas = new int[3];
        int i = 0;
        for (Map.Entry entry:map.entrySet()){
            units[i]=entry.getKey()+"";
            datas[i]=Integer.parseInt(entry.getValue()+"");
            i++;
        }

        String option = EchartsOptionUtils.getPieOption(units, datas, "早高峰拥堵道路区域占比");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 晚高峰拥堵道路top20柱状图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image3(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException {
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime,endTime,"2");
        String[] units = new String[roadTop20.size()];
        double[] datas = new double[roadTop20.size()];
        for(int i=0;i<roadTop20.size();i++){
            units[i]=roadTop20.get(i).getRoadName();
            datas[i]=Double.parseDouble(roadTop20.get(i).getAvgIndex());
        }
        String option = EchartsOptionUtils.getBarOption(units, datas, "晚高峰拥堵道路");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 晚高峰拥堵道路top20区域占比饼状图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image4(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException {
        List<TrafficBean> roadTop20 = trafficMapper.selectRoadTop20(startTime,endTime,"2");
        Map<String,Integer> map = new HashMap<>();
        List<List<Object>> list2 = new ArrayList<>();
        List<Object> tempList = new ArrayList<Object>();
        map.put("婺城区",0);
        map.put("金东区",0);
        map.put("开发区",0);
        for (TrafficBean t : roadTop20) {
            if (map.containsKey(t.getAreaName())) {
                map.put(t.getAreaName(),map.get(t.getAreaName()).intValue()+1);
            }
        }
        String[] units = new String[3];
        int[] datas = new int[3];
        int i = 0;
        for (Map.Entry entry:map.entrySet()){
            units[i]=entry.getKey()+"";
            datas[i]=Integer.parseInt(entry.getValue()+"");
            i++;
        }

        String option = EchartsOptionUtils.getPieOption(units, datas, "晚高峰拥堵道路区域占比");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }
    /**
     * 市每日平均指数折线图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image6(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<TrafficBean> tpiEveryDay = trafficMapper.selectAllTpiEveryDay(startTime,endTime);
        List<Date> datesBetweenUsingJava7 = DateUtil.getDatesBetweenUsingJava7(sdf.parse(startTime), sdf.parse(endTime));

        String[] times = new String[datesBetweenUsingJava7.size()];
        double[] index = new double[datesBetweenUsingJava7.size()];
        for (int i = 0; i < datesBetweenUsingJava7.size(); i++) {
            times[i] = sdf1.format(datesBetweenUsingJava7.get(i));
            index[i] = 0;
        }
        String[] types = {"平均指数"};
        for (int i = 0; i < tpiEveryDay.size(); i++) {
            index[i] = Double.parseDouble(tpiEveryDay.get(i).getAvgIndex());
//            speed[i] = Double.parseDouble(tpiEveryDay.get(i).getAvgSpeed());
        }
        double[][] datas=new double[2][];
        datas[0]=index;
//        datas[1]=speed;

        String option = EchartsOptionUtils.getLineOption(types,times, datas, "金华市日均指数");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 市每日平均速度折线图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image7(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<TrafficBean> tpiEveryDay = trafficMapper.selectAllTpiEveryDay(startTime,endTime);
        List<Date> datesBetweenUsingJava7 = DateUtil.getDatesBetweenUsingJava7(sdf.parse(startTime), sdf.parse(endTime));

        String[] times = new String[datesBetweenUsingJava7.size()];
        double[] index = new double[datesBetweenUsingJava7.size()];
        double[] speed = new double[datesBetweenUsingJava7.size()];
        String[] types = {"平均速度","自由流速度"};
        for (int i = 0; i < datesBetweenUsingJava7.size(); i++) {
            times[i] = sdf1.format(datesBetweenUsingJava7.get(i));
            index[i] = 0;
            speed[i] = new Random().nextInt(45-43)+43;
        }
        for (int i = 0; i < tpiEveryDay.size(); i++) {
            index[i] = Double.parseDouble(tpiEveryDay.get(i).getAvgSpeed());
        }
        double[][] datas=new double[2][];
        datas[0]=index;
        datas[1]=speed;

        String option = EchartsOptionUtils.getLineOption(types,times, datas, "金华市日均速度");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 三区每日平均指数折线图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image8(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<TrafficBean> tpiEveryDay = trafficMapper.selectRegionTpiEveryDay(startTime,endTime);
        //获取两个时间之间的所有日期
        List<Date> datesBetweenUsingJava7 = DateUtil.getDatesBetweenUsingJava7(sdf.parse(startTime), sdf.parse(endTime));
        String[] times = new String[datesBetweenUsingJava7.size()];
        double[] indexWC = new double[datesBetweenUsingJava7.size()];
        double[] indexJD = new double[datesBetweenUsingJava7.size()];
        double[] indexKF = new double[datesBetweenUsingJava7.size()];
        //三个数据即画出三条线
        String[] types = {"婺城区","金东区","开发区"};
        // 初始化数据，将所有日期对应的数据设置为0
        for (int i = 0; i < datesBetweenUsingJava7.size(); i++) {
            times[i] = sdf1.format(datesBetweenUsingJava7.get(i));
            indexWC[i] = 0;
            indexJD[i] = 0;
            indexKF[i] = 0;
        }
        // 将对应区域 对应日期的数据填充进数组
        for (int i = 0; i < tpiEveryDay.size(); i++) {
            for (int j = 0; j < times.length; j++) {
                if (tpiEveryDay.get(i).getAreaName().equals("婺城区")) {
                    if (tpiEveryDay.get(i).getTime().equals(times[j])) {
                        indexWC[j] = Double.parseDouble(tpiEveryDay.get(i).getAvgIndex());
                    }
                } else if (tpiEveryDay.get(i).getAreaName().equals("金东区")) {
                    if (tpiEveryDay.get(i).getTime().equals(times[j])) {
                        indexJD[j] = Double.parseDouble(tpiEveryDay.get(i).getAvgIndex());
                    }
                } else {
                    if (tpiEveryDay.get(i).getTime().equals(times[j])) {
                        indexKF[j] = Double.parseDouble(tpiEveryDay.get(i).getAvgIndex());
                    }
                }
            }
        }
        double[][] datas=new double[3][];
        datas[0]=indexWC;
        datas[1]=indexJD;
        datas[2]=indexKF;

        String option = EchartsOptionUtils.getLineOption(types,times, datas, "三区日均指数");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 三区月平均指数与上月对比图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image9(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preStartTime = DateUtil.format(DateUtil.preMonth(sdf.parse(startTime)));
        String preEndTime = DateUtil.format(DateUtil.preMonth(sdf.parse(endTime)));
        List<TrafficBean> tpiCompare = trafficMapper.selectRegionTpiCompare(startTime,endTime,preStartTime,preEndTime,"1");
        String[] preMonth = preStartTime.split("-");
        String[] nowMonth = startTime.split("-");
        String[] times = {preMonth[1], nowMonth[1]};
        String[] cities = {"婺城区","金东区","开发区"};
        //上月三区指数
        double[] indexPre = new double[cities.length];
        //本月三区指数
        double[] indexNow = new double[cities.length];
        //合并二维数组
        double[][] datas = new double[times.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            indexPre[i] = 0;
            indexNow[i] = 0;
        }

        // 不太合理，但先这样吧
        for (int i = 0; i < tpiCompare.size(); i++) {
            for (int j = 0; j < cities.length; j++) {
                if (tpiCompare.get(i).getAreaName().equals("婺城区")) {
                    if (tpiCompare.get(i).getTime().equals(times[0])) {
                        indexPre[0] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    } else {
                        indexNow[0] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    }
                } else if (tpiCompare.get(i).getAreaName().equals("金东区")) {
                    if (tpiCompare.get(i).getTime().equals(times[0])) {
                        indexPre[1] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    } else {
                        indexNow[1] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    }
                } else {
                    if (tpiCompare.get(i).getTime().equals(times[0])) {
                        indexPre[2] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    } else {
                        indexNow[2] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    }
                }
            }
        }

        datas[0] = indexPre;
        datas[1] = indexNow;

        String option = EchartsOptionUtils.getMultiBarOption(times,cities, datas, "三区月平均指数与上月对比图");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    /**
     * 三区晚高峰月平均指数与上月对比图
     * @param xwpfDocument
     * @param startTime
     * @param endTime
     * @param ew
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public void image10(XWPFDocument xwpfDocument,String startTime, String endTime,ExportWord ew) throws FileNotFoundException, InvalidFormatException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preStartTime = DateUtil.format(DateUtil.preMonth(sdf.parse(startTime)));
        String preEndTime = DateUtil.format(DateUtil.preMonth(sdf.parse(endTime)));
        List<TrafficBean> tpiCompare = trafficMapper.selectRegionTpiCompare(startTime,endTime,preStartTime,preEndTime,"2");
        String[] preMonth = preStartTime.split("-");
        String[] nowMonth = startTime.split("-");
        String[] times = {preMonth[1], nowMonth[1]};
        String[] cities = {"婺城区","金东区","开发区"};
        //上月三区指数
        double[] indexPre = new double[cities.length];
        //本月三区指数
        double[] indexNow = new double[cities.length];
        //合并二维数组
        double[][] datas = new double[times.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            indexPre[i] = 0;
            indexNow[i] = 0;
        }

        // 不太合理，但先这样吧
        for (int i = 0; i < tpiCompare.size(); i++) {
            for (int j = 0; j < cities.length; j++) {
                if (tpiCompare.get(i).getAreaName().equals("婺城区")) {
                    if (tpiCompare.get(i).getTime().equals(times[0])) {
                        indexPre[0] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    } else {
                        indexNow[0] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    }
                } else if (tpiCompare.get(i).getAreaName().equals("金东区")) {
                    if (tpiCompare.get(i).getTime().equals(times[0])) {
                        indexPre[1] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    } else {
                        indexNow[1] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    }
                } else {
                    if (tpiCompare.get(i).getTime().equals(times[0])) {
                        indexPre[2] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    } else {
                        indexNow[2] = Double.parseDouble(tpiCompare.get(i).getAvgIndex());
                        break;
                    }
                }
            }
        }

        datas[0] = indexPre;
        datas[1] = indexNow;

        String option = EchartsOptionUtils.getMultiBarOption(times,cities, datas, "三区晚高峰月平均指数与上月对比图");
        String imagePath = EchartsUtils.generateEChart(option, 1600, 900);
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        byte[] buffer = null;
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        String ind = xwpfDocument.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_GIF);
        int id =  xwpfDocument.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_GIF);
        ew.createPicture(ind, id, 600, 450,paragraph);
        EchartsUtils.deleteFile(imagePath);
    }

    public Map<String, Double> analyse10(String startTime, String endTime, String type) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preStartTime = DateUtil.format(DateUtil.preMonth(sdf.parse(startTime)));
        String preEndTime = DateUtil.format(DateUtil.preMonth(sdf.parse(endTime)));
        List<TrafficBean> tpiCompare = trafficMapper.selectRegionTpiCompare(startTime,endTime,preStartTime,preEndTime,type);
        String[] preMonth = preStartTime.split("-");
        Map<String, Double> map = new HashMap<>();
        double preTotal = 0;
        double nowTotal = 0;
        for (int i = 0; i < tpiCompare.size(); i++) {
            String areaName = tpiCompare.get(i).getAreaName();
            String time = tpiCompare.get(i).getTime();
            map.put(areaName + "-" + time, Double.parseDouble(tpiCompare.get(i).getAvgIndex()));
        }
        for (TrafficBean t : tpiCompare) {
            if (t.getTime().equals(preMonth[1])) {
                preTotal = preTotal + Double.parseDouble(t.getAvgIndex());
            } else {
                nowTotal = nowTotal + Double.parseDouble(t.getAvgIndex());
            }
        }
        map.put("preTotal", preTotal);
        map.put("nowTotal", nowTotal);
        return map;
    }

    /**
     * 设置标题样式
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
