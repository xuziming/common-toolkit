package com.simon.credit.toolkit.ext.image;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;

import com.simon.credit.exception.MultipleInstanceException;

/**
 * 图片处理器
 * @author XUZIMING 2018-06-10
 */
public class ImageProcesser {

	private static final class SingletonHolder {
		private static final ImageProcesser INSTANCE = new ImageProcesser();
	}

	public ImageProcesser() {
		// 避免采用反射方式直接调用私有构造器, 从而破解单例模式
		if (SingletonHolder.INSTANCE != null) {
			throw new MultipleInstanceException(ImageProcesser.class);
		}
	}

	private Object readResolve() throws ObjectStreamException {
		// 避免反序列化破解单例模式: 发序列化时, 若定义了readResolve(),
		// 则直接返回此方法指定的对象, 而无需单独再创建新对象.
		return SingletonHolder.INSTANCE;
	}

	public static ImageProcesser getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public static void main(String[] args) throws IOException {
		String destImage = getInstance().compress("d:/ttt.jpg");
		System.out.println(destImage);
	}

	/**
	 * 压缩图片
	 * @param srcImageFile 源图片文件
	 * @return 压缩后的图片
	 * @throws IOException
	 */
	public String compress(File srcImageFile) throws IOException {
		return compress(srcImageFile.getPath());
	}

	/**
	 * 压缩图片
	 * @param srcImage 源图片
	 * @return 压缩后的图片
	 * @throws IOException
	 */
	public String compress(String srcImage) throws IOException {
		String destImage = FilenameUtils.getFullPathNoEndSeparator(srcImage) + File.separator + 
			"compress_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(srcImage);

		/**
		 * scale可指定图片的大小(值在0到1之间),1f即原图大小,0.5f就是原图一半大小,这里大小指图片的长宽;
		 * 而outputQuality是图片的质量(值在0到1之间),越接近于1质量越好,越接近于0质量越差.
		 */
		Thumbnails.of(srcImage).scale(1f).outputQuality(0.2382f).toFile(destImage);
		return destImage;
	}

}