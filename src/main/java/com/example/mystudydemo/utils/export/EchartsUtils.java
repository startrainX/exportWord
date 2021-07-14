package com.example.mystudydemo.utils.export;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;

/**
 * @author:CCA
 * @date:2020/9/15 10:02
 * @description:
 */
public class EchartsUtils {
    private static final String JSpath = "D:\\echarts-convert\\echarts-convert1.js";
//        private static final String JSpath = "/usr/local/echarts-convert/echarts-convert1.js";
    private static final String Phantomjs = "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe ";
//    private static final String Phantomjs="/usr/local/phantomjs-2.1.1-linux-x86_64/bin/phantomjs ";

    public static String option() {

        //由于是测试用例首先声明  1.标题   2.x轴代表的路段  3.路段数据
        //标题
        String title = "路段人流数据统计";
        String[] roads = {"金东区", "婺城区", "开发区"};
        String[] types = {"五月", "六月"};
        //路段数据
        int[][] datas = {{145,221,369},{123, 153, 643}};
//        int[] datas = {123, 153, 643};


        //####开始构建option对象 ######
        //这里先做最简单的测试用用例 其他渲染的属性 大家可以在了解之后自行测试使用
        //1.创建option对象    有两种方式 一种是直接创建option但是在封装好option之后要使用json转换工具进行格式的转换
        //这里使用第二种方式直接构建GsonOption 通过toString方法可转换成json
        GsonOption option = new GsonOption();

        //2.设置标题  可选
        //将标题传入即可 并且支持链式调用 设置显示位置 居左
        option.title().text(title).x("left");

        //3.设置图例  可选
//        option.legend("路段数据");
        //4.设置工具栏  可选
        option.toolbox().show(true).feature(Tool.mark,
                Tool.magicType); //设置可标记

        //5.设置显示工具
        option.tooltip().show(true).
                //设置显示的格式 当鼠标放到柱状图上时的显示格式
                formatter("{a}</br>{b}:{c}");

        //6.设置x轴数据
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.data(roads);
        option.xAxis(categoryAxis);
        Legend legend = new Legend();
        legend.data(types);
        legend.y("bottom");
        option.legend(legend);

        //7.设置y轴 这里不给指定数据  自动调整
        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        //8.填充柱状图数据
//        Bar bar = new Bar();
        Bar[] bar = new Bar[2];
        for (int j = 0; j < 2; j++) {
            Bar b = new Bar();
            // 设置数据显示
            ItemStyle itemStyle = new ItemStyle();
            Normal normal = new Normal();
            Label label = new Label();
            label.show(true);
            normal.setLabel(label);
            b.setItemStyle(itemStyle.normal(normal));

//            Map<String, Object> map = new HashMap<>();
            b.name(types[j]);
            //单独放三条数据
            for (int i = 0; i < 3; i++) {
                b.data(datas[j][i]);
            }
            //组成一个bar
            bar[j] = b;
        }


        //装入数据表中
        option.series(bar);
        //option进行json格式处理
        String result = option.toString();
        return result;
    }

    public static void main(String[] args) {
        String imgName = "D:/平台/tes" + UUID.randomUUID().toString().substring(0, 4) + ".png ";
        String option = "{xAxis: {type: 'category',data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']},yAxis: {type: 'value'},series: [{data: [820, 932, 901, 934, 1290, 1330, 1320],type: 'line'}]}";
//        String option = option();
        //String options = "test";
        String imgPath = generateEChart(option, 1600, 900);
        System.out.println(imgPath);

    }


    public static String generateEChart(String options, int width, int height) {

        String fileName = "test-" + UUID.randomUUID().toString().substring(0, 8) + ".png";
        String imgPath = "D:/平台/img/" + fileName;
//        String imgPath = "/usr/local/img/" +fileName;

        //数据json
        String dataPath = writeFile(options);
        try {
            //文件路径（路径+文件名）
            File file = new File(imgPath);
            //文件不存在则创建文件，先创建目录
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            String cmd = Phantomjs + JSpath + " -infile " + dataPath + " -outfile " + imgPath + " -width " + width + " -height " + height;
            // System.out.println(cmd);
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream stream = process.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
            String line = "";
            while ((line = input.readLine()) != null) {
                //System.out.println(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // String base64Img = ImageToBase64(imgPath);

            //deleteFile(imgPath);
            //删除数据
            deleteFile(dataPath);
            //调用完以后建议删除图片数据
            return imgPath;
        }
    }

    public static String writeFile(String options) {
        String dataPath = "D:/平台/data/data" + UUID.randomUUID().toString().substring(0, 8) + ".json";
//        String dataPath="/usr/local/data/"+ UUID.randomUUID().toString().substring(0, 8) +".json";
        try {
            /* 写入Txt文件 */
            File writename = new File(dataPath); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writename.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(writename.getParent());
                dir.mkdirs();
                writename.createNewFile(); // 创建新文件
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(options); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPath;
    }

    /**
     * 图片文件转为base64
     *
     * @param imgPath
     */
    private static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(Objects.requireNonNull(data));
    }

    /**
     * 删除文件
     *
     * @param pathname
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String pathname) {
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
            //System.out.println("文件已经被成功删除");
        }
        return result;
    }
}
