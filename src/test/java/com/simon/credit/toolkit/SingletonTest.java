package com.simon.credit.toolkit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

import org.slf4j.Logger;

import com.simon.credit.toolkit.image.ImageProcesser;
import com.simon.credit.toolkit.io.IOToolkits;
import com.simon.credit.toolkit.logger.FormatLoggerFactory;

/**
 * 单例模式测试
 * @author XUZIMING 2018-08-19
 */
public class SingletonTest {
	private static final Logger LOGGER = FormatLoggerFactory.getLogger(SingletonTest.class);

	public static void main(String[] args) throws Exception {
		ImageProcesser inst1 = ImageProcesser.getInstance();
		ImageProcesser inst2 = ImageProcesser.getInstance();
		System.out.println(inst1);
		System.out.println(inst2);

		testCrackSingletonByReflection();

		testCrackSingletonByDeserialization(inst1);
	}

	private static void testCrackSingletonByReflection() throws Exception {
		// 通过反射的方式直接调用私有构造器
		@SuppressWarnings("unchecked")
		Class<ImageProcesser> clazz = (Class<ImageProcesser>) 
			Class.forName("com.simon.credit.toolkit.image.ImageProcesser");

		// 获取无参构造器
		Constructor<ImageProcesser> constructor = clazz.getDeclaredConstructor();

		// 将accessible标志设置为目标值: 
		// 值为true则表示反射的对象在使用时将取消Java语言访问检查, 从而提高了性能或访问private构造器
		// 值为false则表示反射的对象将实施Java语言访问检查.
		constructor.setAccessible(true);

		ImageProcesser inst3 = constructor.newInstance();
		ImageProcesser inst4 = constructor.newInstance();
		System.out.println(inst3);
		System.out.println(inst4);
	}

	private static void testCrackSingletonByDeserialization(ImageProcesser inst) {
		// 通过反序列化的方式构造多个对象
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			fos = new FileOutputStream("d:/obj.dat");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(inst);
			oos.flush();
			oos.close();
			fos.close();

			ois = new ObjectInputStream(new FileInputStream("d:/obj.dat"));
			ImageProcesser inst5 = (ImageProcesser) ois.readObject();
			System.out.println(inst5);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.info(e.getMessage(), e);
		} finally {
			IOToolkits.close(oos, fos, ois);
		}
	}

}
