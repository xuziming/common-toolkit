package com.simon.credit.toolkit;

import java.io.File;
import java.io.IOException;

import com.simon.credit.toolkit.image.ImageProcesser;
import com.simon.credit.toolkit.image.ImageVerticalSynthesizer;
import com.simon.credit.toolkit.pdf.PdfConverter;

/**
 * PDF文件转为长图片
 * @author XUZIMING 2018-06-07
 */
public class PdfToLongImageTest {

	public static void main(String[] args) throws IOException {
		File pdfFile = new File("d:/电商订单系统海量数据分表方案.pdf");

		// PDF文件转为图片
		File[] imageFiles = PdfConverter.getInstance().castToImages(pdfFile);
		// String[] imageFiles = {"d:/1.jpg","d:/2.jpg","d:/3.jpg","d:/4.jpg","d:/5.jpg","d:/6.jpg"};

		// 图片合成
		File synthesizeFile = ImageVerticalSynthesizer.getInstance().imageSynthesize(imageFiles);
		System.out.println("=== synthesizeFile: " + synthesizeFile.getPath());

		// 图片压缩
		String compressImage = ImageProcesser.getInstance().compress(synthesizeFile);
		System.out.println(compressImage);
	}

}
