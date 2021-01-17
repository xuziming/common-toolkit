package com.simon.credit.toolkit.ext.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.simon.credit.exception.MultipleInstanceException;

/**
 * PDF转换器
 * @author XUZIMING 2018-06-12
 */
public class PdfConverter {

	private PdfConverter() {
		// 避免采用反射方式直接调用私有构造器, 从而破解单例模式
		if (SingletonHolder.INSTANCE != null) {
			throw new MultipleInstanceException(PdfConverter.class);
		}
	}

	private Object readResolve() throws ObjectStreamException {
		// 避免反序列化破解单例模式: 发序列化时, 若定义了readResolve(),
		// 则直接返回此方法指定的对象, 而无需单独再创建新对象.
		return SingletonHolder.INSTANCE;
	}

	private static final class SingletonHolder {
		private static final PdfConverter INSTANCE = new PdfConverter();
	}

	public static PdfConverter getInstance() {
		return SingletonHolder.INSTANCE;
	}

//	public static void main(String[] args) throws IOException {
//		File pdfFile = new File("d:\\zxdk.pdf");
//		String imageDir = "d:\\";
//		PdfConverter.getInstance().castToImages(pdfFile, imageDir);
//	}

//	/**
//	 * PDF文件转为一张张图片
//	 * @param pdfFile
//	 * @return 图片文件数组
//	 * @throws IOException
//	 */
//	public File[] castToImages(File pdfFile) throws IOException {
//		String dir = pdfFile.getParent() + File.separator + "Images(" + pdfFile.getName() + ")";
//		if (!new File(dir).exists()) {
//			new File(dir).mkdirs();
//		}
//		return castToImages(pdfFile, dir);
//	}

//	/**
//	 * PDF文件转为文件
//	 * @param pdfFile PDF文件
//	 * @param imageDir 图片存储文件夹
//	 * @return
//	 * @throws IOException
//	 */
//	public File[] castToImages(File pdfFile, String imageDir) throws IOException {
//		// 加载指定PDF文件
//		PDDocument doc = PDDocument.load(pdfFile);
//		// 获取PDF文件页码
//		int pageCount = doc.getNumberOfPages();
//		// 输出页码，可以注释掉
//		System.out.println("pdf total page: " + pageCount);
//
//		@SuppressWarnings("unchecked")
//		List<PDPage> pages = doc.getDocumentCatalog().getAllPages();
//		File[] imageFiles = new File[pages.size()];
//
//		for (int i = 1; i <= pages.size(); i++) {
//			PDPage page = pages.get(i - 1);
//
//			String suffix = "jpg";// 后缀
//
//			BufferedImage image = page.convertToImage();// 将页面转换为图片
//			Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix(suffix);
//			while (iter.hasNext()) {
//				ImageWriter writer = iter.next();
//				imageFiles[i - 1] = new File(imageDir + File.separator + "p" + i + "." + suffix);
//				FileOutputStream out = new FileOutputStream(imageFiles[i - 1]);
//				ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
//				writer.setOutput(outImage);
//				writer.write(new IIOImage(image, null, null));
//			}
//		}
//
//		// 关闭资源
//		doc.close();
//		System.out.println("pdf cast to image completed.");
//		return imageFiles;
//	}

}