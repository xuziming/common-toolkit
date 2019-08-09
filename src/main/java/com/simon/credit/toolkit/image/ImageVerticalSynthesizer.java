package com.simon.credit.toolkit.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.simon.credit.exception.MultipleInstanceException;

/**
 * 图片纵向合成器
 * @author XUZIMING 2018-06-07
 */
public class ImageVerticalSynthesizer {

	/** 默认图片格式 */
	private static final String DEFAULT_IMAGE_FORMAT = "jpg";

	private ImageVerticalSynthesizer() {
		// 避免采用反射方式直接调用私有构造器, 从而破解单例模式
		if (SingletonHolder.INSTANCE != null) {
			throw new MultipleInstanceException(ImageVerticalSynthesizer.class);
		}
	}

	private Object readResolve() throws ObjectStreamException {
		// 避免反序列化破解单例模式: 发序列化时, 若定义了readResolve(),
		// 则直接返回此方法指定的对象, 而无需单独再创建新对象.
		return SingletonHolder.INSTANCE;
	}

	private static final class SingletonHolder {
		private static final ImageVerticalSynthesizer INSTANCE = new ImageVerticalSynthesizer();
	}

	public static ImageVerticalSynthesizer getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public static void main(String[] args) {
		try {
			String[] filePaths = { 
				"d:/123/p1.jpg", 
				"d:/123/p2.jpg", 
				"d:/123/p3.jpg", 
				"d:/123/p4.jpg" 
			};

			ImageVerticalSynthesizer.getInstance().imageSynthesize(filePaths);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * 图片合成(注:待合并的几张图片宽度均相同,故在定义BufferedImage宽度时取其中一张的宽度即可)
	 * @param imagePaths 待合并的图片文件路径
	 * @throws Exception
	 */
	public File imageSynthesize(String... imagePaths) throws IOException {
		if (imagePaths == null || imagePaths.length == 0) {
			return null;
		}

		File destImageFile = null;
		for (String imagePath : imagePaths) {
			if (imagePath == null || imagePath.trim().isEmpty()) {
				continue;
			}
			File imageFile = new File(imagePath.trim());
			if (!imageFile.exists() || imageFile.isDirectory()) {
				continue;
			}

			// 构造合成图片文件
			destImageFile = new File(imageFile.getParent() + File.separator + 
				"combined-" + System.currentTimeMillis() + "." + DEFAULT_IMAGE_FORMAT);
			break;
		}

		// 合成图片
		imageSynthesize(destImageFile, imagePaths);

		return destImageFile;
	}

	/**
	 * 图像合成
	 * @param destImageFile 合成后的图像文件
	 * @param imagePaths 待合成的图像路径列表
	 * @throws IOException
	 */
	public void imageSynthesize(File destImageFile, String... imagePaths) throws IOException {
		if (imagePaths == null || imagePaths.length == 0) {
			return;
		}

		List<File> imageFiles = new ArrayList<File>(imagePaths.length);
		for (String imagePath : imagePaths) {
			if (imagePath == null || imagePath.trim().isEmpty()) {
				continue;
			}
			File imageFile = new File(imagePath.trim());
			if (!imageFile.exists() || imageFile.isDirectory()) {
				continue;
			}
			imageFiles.add(imageFile);
		}

		// 图片合成
		imageSynthesize(destImageFile, imageFiles.toArray(new File[imageFiles.size()]));
	}

	/**
	 * 图片合成(待合并的几张图片宽度均相同,故在定义BufferedImage宽度时取其中一张的宽度即可)
	 * @param imageFiles 待合并的图片文件
	 * @throws Exception
	 */
	public File imageSynthesize(File... imageFiles) throws IOException {
		if (imageFiles == null || imageFiles.length == 0) {
			return null;
		}

		File destImageFile = new File(imageFiles[0].getParent() + 
			File.separator + "synthesize_" + System.currentTimeMillis() + ".jpg");

		// 合成图片
		imageSynthesize(destImageFile, imageFiles);

		return destImageFile;
	}

	/**
	 * 图片合成(注:待合并的几张图片宽度均相同,故在定义BufferedImage宽度时取其中一张的宽度即可)
	 * @param destImageFile 合成后的图片文件
	 * @param imageFiles 待合并的图片文件
	 * @throws Exception
	 */
	public void imageSynthesize(File destImageFile, File... imageFiles) throws IOException {
		if (imageFiles == null || imageFiles.length == 0) {
			return;
		}

		Image[] imageAry = new Image[imageFiles.length];

		// 定义合成后的图片高度
		int height = 0;

		for (int i = 0; i < imageFiles.length; i++) {
			imageAry[i] = ImageIO.read(imageFiles[i]);

			// 计算合成图的总高度
			height += imageAry[i].getHeight(null);
		}

		// 定义合成后的图片宽度(取其中一张待合成图的宽度)
		int width = imageAry[0].getWidth(null);

		// 构造一个类型为预定义图像类型之一的 BufferedImage。 宽度为第一只的宽度，高度为各个图片高度之和
		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 绘制合成图像
		Graphics g = combined.createGraphics();

		int y = 0;
		for (int i = 0; i < imageAry.length; i++) {
			int imgHeight = imageAry[i].getHeight(null);
			// imgHeight++目的是画一条一个像素的分割线
			g.drawImage(imageAry[i], 0, y, width, imgHeight++, null);
			y += imgHeight;
		}

		// 释放此图形的上下文以及它使用的所有系统资源
		g.dispose();

		// 保存合成后的图像文件
		BufferedImageToolkits.saveAs(combined, destImageFile);

		System.out.println("image synthesize completed.");
	}

}
