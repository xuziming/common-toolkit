package com.simon.credit.toolkit.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 合成图像保存工具类
 * @author XUZIMING 2018-06-07
 */
public class BufferedImageToolkits {

	private BufferedImageToolkits() {
		// 构造器私有化
	}

	/**
	 * 保存合成后的图像文件
	 * @param combined
	 * @param destImageFile
	 */
//	public static void save(BufferedImage combined, File destImageFile) {
//		// 输出流
//		OutputStream out = null;
//		try {
//			out = new FileOutputStream(destImageFile);
//			// 将绘制的图像生成至输出流
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(combined);
//		} catch (Exception e) {
//			// ignore
//		} finally {
//			// 关闭输出流
//			IOUtils.closeQuietly(out);
//		}
//	}

	/**
	 * 保存合成后的图像文件
	 * @param combined
	 * @param destImageFile
	 */
	public static void saveAs(BufferedImage combined, File destImageFile) {
		try {
			ImageIO.write(combined, "JPG", destImageFile);
		} catch (Exception e) {
			// ignore
		}
	}

}
