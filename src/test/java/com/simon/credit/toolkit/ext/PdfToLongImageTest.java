package com.simon.credit.toolkit.ext;

import java.io.File;
import java.io.IOException;

import com.simon.credit.toolkit.ext.pdf.PdfConverter;

/**
 * PDF文件转为长图片
 * @author XUZIMING 2018-06-07
 */
public class PdfToLongImageTest {

	public static void main(String[] args) throws IOException {
		File pdfFile = new File("C:\\Users\\Administrator\\Desktop\\Java\\面试\\Dubbo源码解析\\Dubbo源码解析（01）Hello,Dubbo.pdf");

		// PDF文件转为图片
		File[] imageFiles = PdfConverter.getInstance().castToImages(pdfFile);
		System.out.println(imageFiles);
//		String[] imageFiles = {"d:/r1.png","d:/r2.png","d:/r3.png","d:/r4.png"};

		// 图片合成
//		File synthesizeFile = ImageVerticalSynthesizer.getInstance().imageSynthesize(imageFiles);
//		System.out.println("=== synthesizeFile: " + synthesizeFile.getPath());

		// 图片压缩
//		String compressImage = ImageProcesser.getInstance().compress(synthesizeFile);
//		System.out.println(compressImage);
	}

}