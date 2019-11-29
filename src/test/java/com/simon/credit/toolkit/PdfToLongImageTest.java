package com.simon.credit.toolkit;

import java.io.File;
import java.io.IOException;

import com.simon.credit.toolkit.image.ImageVerticalSynthesizer;

/**
 * PDF文件转为长图片
 * @author XUZIMING 2018-06-07
 */
public class PdfToLongImageTest {

	public static void main(String[] args) throws IOException {
		// File pdfFile = new File("C:\\Users\\Administrator\\Desktop\\2222.pdf");

		// PDF文件转为图片
		// File[] imageFiles = PdfConverter.getInstance().castToImages(pdfFile);
		String[] imageFiles = {"d:/r1.png","d:/r2.png","d:/r3.png","d:/r4.png"};

		// 图片合成
		File synthesizeFile = ImageVerticalSynthesizer.getInstance().imageSynthesize(imageFiles);
		System.out.println("=== synthesizeFile: " + synthesizeFile.getPath());

		// 图片压缩
//		String compressImage = ImageProcesser.getInstance().compress(synthesizeFile);
//		System.out.println(compressImage);
	}

}
