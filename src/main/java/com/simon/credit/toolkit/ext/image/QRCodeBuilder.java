package com.simon.credit.toolkit.ext.image;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.simon.credit.toolkit.lang.ArrayToolkits;

/**
 * 二维码生成
 * @author XUZIMING 2018-08-16
 */
public class QRCodeBuilder {

	/** 二维码图片格式范畴 */
	private static final String[] formats = { 
		"bmp", "BMP", "gif", "GIF", "JPEG", "jpeg", "JPG" , "jpg", "pcx" , "PCX" , "png", 
		"PNG", "pnm", "PNM", "raw", "RAW" , "tiff", "TIFF", "tif", "TIF" , "wbmp", "WBMP" 
	};

	/**
	 * 生成二维码
	 * @param qrCodeFile 二维码文件路径
	 * @param format 二维码图片格式(可选项: 
	 * 			bmp | BMP | gif | GIF | JPEG| jpeg| JPG | 
	 * 			jpg | pcx | PCX | png | PNG | pnm | PNM | 
	 * 			raw | RAW | tif | TIF | tiff| TIFF| wbmp| WBMP)
	 * @param content 二维码内容
	 * @param width 二维码图片宽度(单位：像素)
	 * @param height 二维码图片高度(单位：像素)
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void buildQRCode(File qrCodeFile, String format, 
		String content, int width, int height) throws WriterException, IOException {
		// 规定二维码图片格式范畴
		if (ArrayToolkits.notContains(formats, format)) {
			throw new WriterException(String.format("无效的图片格式: %s", format));
		}

		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 二维码边界空白大小, 如: 0、1、2、3、4, 默认值为4
		hints.put(EncodeHintType.MARGIN, 0);// 设置为无边界空白
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToPath(bitMatrix, format, qrCodeFile.toPath());
	}

	/**
	 * 生成二维码(默认: 宽度与高度均为360像素)
	 * @param qrCodeFile 二维码文件路径
	 * @param format 二维码图片格式(可选项: 
	 * 			bmp | BMP | gif | GIF | JPEG| jpeg| JPG | 
	 * 			jpg | pcx | PCX | png | PNG | pnm | PNM | 
	 * 			raw | RAW | tif | TIF | tiff| TIFF| wbmp| WBMP)
	 * @param content 二维码内容
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void buildQRCode(File qrCodeFile, String format, String content) throws WriterException, IOException {
		buildQRCode(qrCodeFile, format, content, 360, 360);
	}

}