package com.example.mystudydemo.utils;

//import com.drew.imaging.ImageMetadataReader;
//import com.drew.imaging.ImageProcessingException;
//import com.drew.imaging.jpeg.JpegMetadataReader;
//import com.drew.imaging.jpeg.JpegProcessingException;
//import com.drew.metadata.Directory;
//import com.drew.metadata.Metadata;
//import com.drew.metadata.Tag;

//import com.drew.metadata.exif.ExifDirectory;
//import com.drew.metadata.exif.ExifInteropDirectory;
//import com.drew.metadata.exif.ExifReader;
//import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
//import com.supconit.ticc.util.upload.FileUploadUtil;
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Level;

/**
 * 图片操作工具类
 * Created by IntelliJ IDEA.
 * User: gary chen
 * Date: 2015-4-29
 * Time: 13:25
 * Email:chenzhe871101@126.com
 */
public class ImageUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);


	public enum AUTO_SIZE{

		//自动计算合成图片大小时，按所有待合成图片中的第一张有效图片的长宽作为基准大小，缩放所有图片
		BASE_ON_FIRST(){
			@Override
			public Point autoSize(Image... images) {
				for(Image image :images){
					if(image!=null){
						return new Point(image.getWidth(null),image.getHeight(null));
					}
				}
				return new Point(0,0);
			}
		},

		//自动计算合成图片大小时，按所有待合成图片中的最小的长宽作为基准大小，缩放所有图片
		@Deprecated
		BASE_ON_MINSIZE(){
			@Override
			public Point autoSize(Image... images) {
				// TODO Auto-generated method stub
				return null;
			}
		},

		//自动计算合成图片大小时，按所有待合成图片中的最大的长宽作为基准大小，缩放所有图片
		@Deprecated
		BASE_ON_MAXSIZE(){
			@Override
			public Point autoSize(Image... images) {
				// TODO Auto-generated method stub
				return null;
			}
		},

		//自动计算合成图片大小时，按指定的长宽作为基准大小，缩放所有图片
		@Deprecated
		BASE_ON_FIXEDSIZE(){
			@Override
			public Point autoSize(Image... images) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		public abstract Point autoSize(Image... images);
	}


	/**
	 * 四张图片按照2×2方式合成（左上、右上、左下、右下）
	 * @param out
	 * @param leftTop
	 * @param rightTop
	 * @param leftButtom
	 * @param rightButtom
	 * @param autoSize
	 */
	public static void meger2_2(OutputStream out ,File leftTop,File rightTop,File leftButtom,File rightButtom,AUTO_SIZE autoSize){
		try {
			Image leftTopImg = getImage(leftTop);
			Image rightTopImg = getImage(rightTop);
			Image leftButtomImg = getImage(leftButtom);
			Image rightButtomImg = getImage(rightButtom);
			Point size = autoSize.autoSize(leftTopImg,rightTopImg,leftButtomImg,rightButtomImg);//借用下point  不再单独定义包含长宽属性的对象了

			BufferedImage mBufferedImage = new BufferedImage(size.x*2, size.y*2, BufferedImage.TYPE_INT_RGB);
			//			Graphics2D g2 = mBufferedImage.createGraphics();
			//			g2.drawImage(leftTopImg, 0,0,size.x,size.y, Color.white, null);
			//			g2.drawImage(rightTopImg, size.x,0,size.x,size.y, Color.white, null);
			//			g2.drawImage(leftButtomImg, 0,size.y,size.x,size.y, Color.white, null);
			//			g2.drawImage(rightButtomImg, size.x,size.y,size.x,size.y, Color.BLACK, null);
			//			g2.dispose();
			compress(mBufferedImage,leftTopImg, 0,0,size.x,size.y);
			compress(mBufferedImage,rightTopImg, size.x,0,size.x,size.y);
			compress(mBufferedImage,leftButtomImg, 0,size.y,size.x,size.y);
			compress(mBufferedImage,rightButtomImg,size.x,size.y,size.x,size.y);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(mBufferedImage);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(mBufferedImage);

			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	/**
	 * 2*2图片合成
	 * @param leftTop
	 * @param rightTop
	 * @param leftButtom
	 * @param rightButtom
	 */
	public static void meger2_2(OutputStream out ,InputStream leftTop,InputStream rightTop,InputStream leftButtom,InputStream rightButtom,AUTO_SIZE autoSize){
		try {
			Image leftTopImg = getImage(leftTop);
			Image rightTopImg = getImage(rightTop);
			Image leftButtomImg = getImage(leftButtom);
			Image rightButtomImg = getImage(rightButtom);
			Point size = autoSize.autoSize(leftTopImg,rightTopImg,leftButtomImg,rightButtomImg);//借用下point  不再单独定义包含长宽属性的对象了

			BufferedImage mBufferedImage = new BufferedImage(size.x*2, size.y*2, BufferedImage.TYPE_INT_RGB);
			//			Graphics2D g2 = mBufferedImage.createGraphics();
			//			g2.drawImage(leftTopImg, 0,0,size.x,size.y, Color.white, null);
			//			g2.drawImage(rightTopImg, size.x,0,size.x,size.y, Color.white, null);
			//			g2.drawImage(leftButtomImg, 0,size.y,size.x,size.y, Color.white, null);
			//			g2.drawImage(rightButtomImg, size.x,size.y,size.x,size.y, Color.BLACK, null);
			//			g2.dispose();
			compress(mBufferedImage,leftTopImg, 0,0,size.x,size.y);
			compress(mBufferedImage,rightTopImg, size.x,0,size.x,size.y);
			compress(mBufferedImage,leftButtomImg, 0,size.y,size.x,size.y);
			compress(mBufferedImage,rightButtomImg,size.x,size.y,size.x,size.y);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(mBufferedImage);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(mBufferedImage);

			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("",e);
		}
	}

	/**
	 * 1*1图片合成
	 * @param left
	 * @param right
	 */
	public static void meger1_1(OutputStream out ,InputStream left,InputStream right,AUTO_SIZE autoSize){
		try {
			Image leftTopImg = getImage(left);
			Image rightTopImg = getImage(right);
			Point size = autoSize.autoSize(leftTopImg,rightTopImg);//借用下point  不再单独定义包含长宽属性的对象了

			BufferedImage mBufferedImage = new BufferedImage(size.x*2, size.y, BufferedImage.TYPE_INT_RGB);
			//			Graphics2D g2 = mBufferedImage.createGraphics();
			//			g2.drawImage(leftTopImg, 0,0,size.x,size.y, Color.white, null);
			//			g2.drawImage(rightTopImg, size.x,0,size.x,size.y, Color.white, null);
			//			g2.drawImage(leftButtomImg, 0,size.y,size.x,size.y, Color.white, null);
			//			g2.drawImage(rightButtomImg, size.x,size.y,size.x,size.y, Color.BLACK, null);
			//			g2.dispose();
			compress(mBufferedImage,leftTopImg, 0,0,size.x,size.y);
			compress(mBufferedImage,rightTopImg, size.x,0,size.x,size.y);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(mBufferedImage);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(mBufferedImage);

			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("",e);
		}
	}

	/**
	 * 尺寸缩放，默认策略为拉伸自适应长宽，不做平铺，多余部分填黑色
	 * @param mBufferedImage 画板
	 * @param image 原始图片
	 * @param pX 在mBufferedImage中基准起始点
	 * @param pY 在mBufferedImage中基准起始点
	 * @param width  在mBufferedImage中目标宽
	 * @param height 在mBufferedImage中目标高
	 * @return
	 */
	private static Image compress(BufferedImage mBufferedImage ,Image image ,int pX ,int pY ,int width, int height){
		Graphics2D g2 = mBufferedImage.createGraphics();

		int x =pX;
		int y =pY;
		int w =width;
		int h =height;
		if(image==null){
			g2.setColor(Color.BLACK);
			g2.fillRect(x, y, w, h);
		}else{
			double scale = (double)width/height;
			double imageScale = (double)image.getWidth(null)/image.getHeight(null);
			if(imageScale/scale>1){//拉伸自适应后过矮
				y = (int) (pY + (height-(double)width/image.getWidth(null)*image.getHeight(null))/2);
				h = (int)(image.getHeight(null)*((double)width/image.getWidth(null)));
			}else if(imageScale/scale<1){//拉伸自适应后过窄
				x = (int) (pX + (width-(double)height/image.getHeight(null)*image.getWidth(null))/2);
				w = (int)(image.getWidth(null)*((double)height/image.getHeight(null)));
			}else{//拉伸自适应后比例刚好
				w = (int)(width*(imageScale/scale));
				h = (int)(height*(imageScale/scale));
			}
			g2.drawImage(image, x ,y ,w ,h, Color.BLACK, null);
		}

		g2.dispose();
		return mBufferedImage;
	}

	/**
	 * 图片添加水印
	 * @param srcImgPath 需要添加水印的图片的路径
	 * @param outImgPath 添加水印后图片输出路径
	 * @param markContentColor 水印文字的颜色
	 * @param waterMarkContent 水印的文字
	 */
	public static void addText(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {
		try {
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);
			Image srcImg = ImageIO.read(srcImgFile);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			//Font font = new Font("Courier New", Font.PLAIN, 12);
			Font font = new Font("黑体", Font.PLAIN, 15);
			g.setColor(markContentColor); //根据图片的背景设置水印颜色

			g.setFont(font);
			int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;
			int y = srcImgHeight - 3;
			if(x>6)
			{
				//字数可以全部显示
				g.drawString(waterMarkContent, x, y);
			}else {
				//需要换行显示
				x=3;
				int fontlength = waterMarkContent.length();
				int ft = (fontlength*(getWatermarkLength(waterMarkContent, g)-6-srcImgWidth))/getWatermarkLength(waterMarkContent, g);
				String news1 = waterMarkContent.substring(0,fontlength-ft-1);
				String news2 = waterMarkContent.substring(fontlength-ft-1);

				int yy = g.getFontMetrics(g.getFont()).getHeight();
				g.drawString(news1, x, y-yy-3);
				g.drawString(news2, x, y);

			}
			g.dispose();
			// 输出图片
			FileOutputStream outImgStream = new FileOutputStream(outImgPath);
			ImageIO.write(bufImg, "jpg", outImgStream);
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取水印文字总长度
	 * @param waterMarkContent 水印的文字
	 * @param g
	 * @return 水印文字总长度
	 */
	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	private static Image getImage(File file) throws IOException{
		if(file ==null || !file.exists() || !file.isFile()){
			return null;
		}else{
			return ImageIO.read(file);
		}
	}

	private static Image getImage(InputStream in) throws IOException{
		if(in ==null ){
			return null;
		}else{
			return ImageIO.read(in);
		}
	}

	/**
	 *@方法名称:getPropertiesByName
	 *@输    入:name 字段名称，imagePath 测速照片路径 , imageURL 远程图片路径
	 *@输    出：String
	 *@作    者: tongyingming
	 *@创建日期:2016-5-31
	 *@方法描述: 获取大华测速照片信息
	 */
	public static String getPropertiesByName(String name , String imagePath,String imageURL){
		FileReader file = null;
		BufferedReader br = null;
		String rts = new String("");
		try{
			//本地图片
			if(imagePath!=null && !imagePath.equals(""))
			{
				file = new FileReader(imagePath);
				br = new BufferedReader(file);
			}else if(imageURL!=null && !imageURL.equals(""))
			{
				//远程图片
//				byte[] bs = FileUploadUtil.getImageByteByURL(imageURL);
//				StringReader sr = new StringReader(new String (bs,"UTF-8"));
//				br = new BufferedReader(sr);
			}

			String s = new String();
			while ((s =br.readLine())!=null)
			{
				if(s!=null && s.indexOf(name)>=0){
					rts = s.substring(s.indexOf("=")+1);
					break;
				}

			}

		}catch (Exception e)
		{
			e.printStackTrace();
		}finally {
			try {
				if(br!=null)
					br.close();
				if(file!=null)
					file.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		return rts;
	}


	/**
	 *
	 *@方法名称:parseDigitalImage
	 *@输    入:filePath 文件名称，inputStream 图片流，二者输入任意一种格式
	 *@输    出：Map<name,value>
	 *@作    者: tongyingming
	 *@创建日期:2016-5-31
	 *@方法描述: 解析JPG数码照片
	 */
//
//	public static Map<String,String> parseDigitalImage(File file,BufferedInputStream inputStream){
//
//		String JpegComment="";
//		Map<String,String> reMap = new HashMap<String, String>();
// 		try {
//			Metadata metadata = null;
//			if(file!=null)
//			{
//				metadata = ImageMetadataReader.readMetadata(file);
//			}else if(inputStream!=null){
//				metadata = ImageMetadataReader.readMetadata(inputStream,true);
//				metadata = JpegMetadataReader.readMetadata(inputStream);
//			}
//			for(Directory directory : metadata.getDirectories())
//			{
//				for(Tag tag : directory.getTags())
//				{
//					if(directory.getName().equals("JpegComment") || tag.getTagName().equals("Jpeg Comment"))
//					{
//						JpegComment=tag.getDescription();
//					}
//				}
//				if(directory.hasErrors())
//				{
//					for(String error : directory.getErrors())
//					{
//						System.err.format("ERROR:%s",error);
//					}
//				}
//			}
//
//			//解析JpegComment
//			String[] rows = JpegComment.split("\n");
//			for (String row : rows) {
//				if(row.indexOf("=")>0)
//				{
//					String key = row.substring(0,row.indexOf("="));
//					String value = row.substring(row.indexOf("=")+1);
//					reMap.put(key,value);
//				}
//			}
//
//		} catch (JpegProcessingException e) {
//			System.err.println("not jpeg file");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ImageProcessingException e) {
//			e.printStackTrace();
//		}
//		/*System.out.println(DateUtil.parse(reMap.get("IMG_DATI"),"MM.dd.yyyy HH:mm:ss.SSS"));*/
//		return reMap;
//	}
	/**
	 *
	 *@方法名称:parseDigitalImage
	 *@输    入:
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2012-5-31
	 *@方法描述: 解析数码照片
	 *
	 */
//	public static Map<String,String> parseDigitalImage(String local) throws Exception {
//
//		File file=new File(local);
//		String picTime = "";
//		Map<String,String> reMap = new HashMap<String, String>();
//		try {
//			Metadata metadata = JpegMetadataReader.readMetadata(file);
//			Directory  directory = metadata.getDirectory(ExifSubIFDDirectory.class);
//			Iterator tags= directory.getTags().iterator();
//
//			while (tags.hasNext()) {
//				Tag tag =  (Tag) tags.next();
//				//System.out.println(tag.getTagName());
//				if(tag.getTagName().equals("Date/Time Original")){
//					String time =tag.getDescription();
//					String []times =time.split(" ");
//					String date = times[0].replace(":","-")+" "+times[1];
//					reMap.put("dateTime", date);
//				}
//			}
//
//
//		} catch (JpegProcessingException e) {
//			reMap.put("dateTime", null);
////
//		}
//		return reMap;
//
//	}



	public static byte[] readInputStream(InputStream input) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = input.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			input.close();
			return output.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
//	public static File writeImageToDisk(byte[] img,String flieName){
//		try {
//			String local=ColletionUtil.getConfig("temp_file_path");
//			try{
//				if(!(new File(local)).isDirectory()){
//					new File(local).mkdir();
//				}
//			}catch (SecurityException e){
//				e.printStackTrace();
//			}
//			File file=new File(local+flieName);
//			FileOutputStream fops=new FileOutputStream(file);
//			fops.write(img);
//			fops.flush();
//			fops.close();
//			return file;
//		}catch (Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	}

	/**
	 * @author liyongjie
	 * @date 2019/4/4  15:50
	 * @ description 取图片转base64
	 */
	public static String img2Base64(String imgURL) {
		byte[] data = null;
		try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			URL url = new URL(imgURL);
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
//			conn.getContentLength();   获取字节长度
			InputStream inStream = conn.getInputStream();
			byte[]buffer=new byte[1024];
			int len =0 ;
			while ((len=inStream.read(buffer))!=-1){
				outputStream.write(buffer,0,len);
			}
			inStream.close();
			outputStream.flush();
			data = outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}


	public static boolean generateImage(String imgStr, String path) {
		if(imgStr == null){
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			//解密
			byte[] b = decoder.decodeBuffer(imgStr);
			//处理数据
			for (int i = 0;i<b.length;++i){
				if(b[i]<0){
					b[i]+=256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * @author liyongjie
	 * @date 2019/4/12  11:54
	 * @ description  转存网路图片到本地
	 */
	public static String url2Local(String urlString,String savePath, String filename) {
		URL url = null;
		OutputStream os =null;
		InputStream is=null;
		String imgPath ="";
		try {
			url = new URL(urlString);
			URLConnection con = url.openConnection();
			//设置请求超时为5s
			con.setConnectTimeout(5*1000);
			is= con.getInputStream();
			byte[] bs = new byte[1024];
			int len;
			File sf=new File(savePath);
			if(!sf.exists()){
				sf.mkdirs();
			}
			os=new FileOutputStream(savePath+"\\"+filename);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return savePath+"\\"+filename;
	}







	public static void main(String...args){
		String srcPath = "D:\\Picture\\100757-15573676775cf7.jpg";
		addText(srcPath,"D:\\csv\\1.jpg",Color.white,"测试水印");
	}

}
