package com.example.mystudydemo.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/2 16:04
 * @description:
 */
@Component
public class EchartsUtil {
    private static final String JSpath = "D:\\echarts-convert\\echarts-convert1.js";
//    private static final String JSpath = "/usr/local/echarts-convert/echarts-convert1.js";
//    private static final String JSpath = "/nfs_root/file-upload/echarts-convert/echarts-convert1.js";
    private static final String Phantomjs="D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe ";
//    private static final String Phantomjs="/usr/local/phantomjs-2.1.1-linux-x86_64/bin/phantomjs ";
//    private static final String Phantomjs="/nfs_root/file-upload/phantomjs-2.1.1-linux-x86_64/bin/phantomjs ";

//    @PostConstruct
    public void test(){
        String option = "{xAxis: {type: 'category',data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']},yAxis: {type: 'value'},series: [{data: [820, 932, 901, 934, 1290, 1330, 1320],type: 'line'}]}";
        //String options = "test";
        String imgPath = generateEChart(option,1600,900);
        System.out.println(imgPath);
    }

    public String generateEChart(String options,int width,int height) {

        String fileName= "test-"+ UUID.randomUUID().toString().substring(0, 8) + ".png";
        String imgPath = "D:/平台/img/" +fileName;
//        String imgPath = "/usr/local/img/" +fileName;
//        String imgPath = "/nfs_root/file-upload/img/" +fileName;

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
             System.out.println(cmd);
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println("input------" + input.readLine());
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            // String base64Img = ImageToBase64(imgPath);

            //deleteFile(imgPath);
            //删除数据
//            deleteFile(dataPath);
            //调用完以后建议删除图片数据
            return imgPath;
        }
    }

    public String writeFile(String options) {
        String dataPath="D:/平台/data/data"+ UUID.randomUUID().toString().substring(0, 8) +".json";
//        String dataPath="/usr/local/data/"+ UUID.randomUUID().toString().substring(0, 8) +".json";
//        String dataPath="/nfs_root/file-upload/data/"+ UUID.randomUUID().toString().substring(0, 8) +".json";
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
     * @param imgPath
     */
    private String ImageToBase64(String imgPath) {
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
    public boolean deleteFile(String pathname){
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
