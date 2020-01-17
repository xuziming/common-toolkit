package com.simon.credit.toolkit.ext;

import java.io.File;

import com.simon.credit.toolkit.ext.image.QRCodeBuilder;

public class QRCodeTest {

	public static void main(String[] args) throws Exception {
		String content = "https://baike.baidu.com/item/%E9%92%A2%E9%93%81%E4%BE%A03/8468661?fr=aladdin";
		File qrCodeFile = new File("d:/ironman.jpg");

		// 创建二维码
		QRCodeBuilder.buildQRCode(qrCodeFile, "JPG", content);

		System.out.println(qrCodeFile.getPath());
		System.out.println("QR Code Built Over!");
	}

}
