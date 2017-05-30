package com.shizuwei.utils;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import lombok.Data;

/**
 * @date 2012-11-26
 * @author xhw
 * 
 */
public class ImgUtil {

	@Data
	public static class ImgCutParam {

		public ImgCutParam(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		/**
		 * 剪切点x坐标
		 */
		private int x;

		/**
		 * 剪切点y坐标
		 */
		private int y;

		/**
		 * 剪切点宽度
		 */
		private int width;

		/**
		 * 剪切点高度
		 */
		private int height;

		/**
		 * 源图片路径名称如:c:\1.jpg
		 */
		private String srcpath = "e:/poool.jpg";
		/**
		 * 剪切图片存放路径名称.如:c:\2.jpg
		 */
		private String subpath = "e:/pool_end";
		/**
		 * jpg图片格式
		 */
		public static final String IMAGE_FORM_OF_JPG = "jpg";
		/**
		 * png图片格式
		 */
		public static final String IMAGE_FORM_OF_PNG = "png";

	}

	public static void main(String[] args) throws Exception {
		trimPic("D:\\PICS\\2017-5-21\\QQ截图20170521231807.png", "D:\\PICS\\2017-5-21\\1490968391(1)xxy.png");
	}

	/**
	 * 对图片裁剪，并把裁剪完蛋新图片保存 。
	 * 
	 * @param srcpath
	 *            源图片路径
	 * @param subpath
	 *            剪切图片存放路径
	 * @throws IOException
	 */
 

	public static void trimPic(String srcPath, String destPath) throws Exception {
		FileInputStream is = null;
		try {
			is = new FileInputStream(srcPath);
			BufferedImage bufferedImg = ImageIO.read(is);
			int min = Math.min(bufferedImg.getWidth(), bufferedImg.getHeight());
			 bufferedImg = cut(new Rectangle(0, 0, min, min), is);
			ImageIO.write(bufferedImg, getPostfix(destPath).toLowerCase(),
					new File(destPath));
		} finally {
			if (is != null)
				is.close();
		}
	}

	public static BufferedImage cut(Rectangle rect, FileInputStream is) throws IOException {
		
		Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("png");
		ImageReader reader = it.next();
		ImageInputStream iis = ImageIO.createImageInputStream(is);
		reader.setInput(iis, true);
		ImageReadParam imageReadParam = reader.getDefaultReadParam();
		imageReadParam.setSourceRegion(rect);
		return reader.read(0, imageReadParam);
	}

	/**
	 * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"<br>
	 * 
	 * @param inputFilePath
	 * @return
	 */
	public static String getPostfix(String inputFilePath) {
		return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
	}

}