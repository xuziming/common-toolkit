package com.simon.credit.toolkit.ext.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片横向合成器
 * @author XUZIMING 2018-06-07
 */
public class ImageHorizontalSynthesizer {
	public static String path = "d:/123";

	public static void main(String[] args) throws IOException {
		File file1 = new File(path, "p1.jpg");
		File file2 = new File(path, "p2.jpg");
		imageSynthesize(file1, file2);
	}

	public static void imageSynthesize(File file1, File file2) throws IOException {
		BufferedImage image1 = ImageIO.read(file1);
		BufferedImage image2 = ImageIO.read(file2);

		BufferedImage combined = new BufferedImage(
			image1.getWidth() * 2, image1.getHeight(), BufferedImage.TYPE_INT_RGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image1, 0                , 0, null);
		g.drawImage(image2, image1.getWidth(), 0, null);

		// Save as new image
		BufferedImageToolkits.saveAs(combined, new File(path, "pp334.jpg"));

		System.out.println("image synthesize completed.");
	}

}