package com.simon.credit.toolkit.ext;

import java.io.File;
import java.io.IOException;

import com.simon.credit.toolkit.ext.image.ImageVerticalSynthesizer;

/**
 * PDF文件转为长图片
 * @author XUZIMING 2018-06-07
 */
public class PdfToLongImageTest {

	public static void main(String[] args) throws IOException {
//		File pdfDir = new File("C:\\Users\\simon\\Desktop\\奥迪保险");
//		File[] pdfFiles = pdfDir.listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File file) {
//				System.out.println(file.getName());
//				return file.getName().endsWith("pdf");
//			}
//		});
//
//		for (File pdfFile : pdfFiles) {
//			try {
//				System.out.println(pdfFile.getPath());
//				File[] imageFiles = PdfConverter.getInstance().castToImages(pdfFile);
//				System.out.println(imageFiles);
//			} catch (Exception e) {
//				e.printStackTrace();
//				// TODO: handle exception
//			}
//		}

		// PDF文件转为图片
//		File[] imageFiles = PdfConverter.getInstance().castToImages(pdfFile);
//		System.out.println(imageFiles);
		String[] imageFiles = {
				"C:\\Users\\Administrator\\Desktop\\img\\1.jpg",
				"C:\\Users\\Administrator\\Desktop\\img\\2.jpg",
				"C:\\Users\\Administrator\\Desktop\\img\\3.jpg",
				"C:\\Users\\Administrator\\Desktop\\img\\4.jpg",
				"C:\\Users\\Administrator\\Desktop\\img\\5.jpg"
		};

		// 图片合成
		File synthesizeFile = ImageVerticalSynthesizer.getInstance().imageSynthesize(imageFiles);
		System.out.println("=== synthesizeFile: " + synthesizeFile.getPath());

		// 图片压缩
//		String compressImage = ImageProcesser.getInstance().compress(synthesizeFile);
//		System.out.println(compressImage);
	}

}