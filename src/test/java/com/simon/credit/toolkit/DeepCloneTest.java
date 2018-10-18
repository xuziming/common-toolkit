package com.simon.credit.toolkit;

import java.util.Date;

import org.slf4j.Logger;

import com.simon.credit.toolkit.lang.ObjectToolkits;
import com.simon.credit.toolkit.logger.FormatLoggerFactory;

/**
 * 深度克隆测试
 * @author XUZIMING 2018-08-19
 */
public class DeepCloneTest {
	private static final Logger LOGGER = FormatLoggerFactory.getLogger(DeepCloneTest.class);

	public static void main(String[] args) {
		Date date = new Date();
//		LOGGER.info("origin date: {}", date);
//		long time = date.getTime();

		User user = new User("tom", 18, date);

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
//			date.setTime(time);
//			User cloneUser = ObjectToolkits.deepClone(user);
//			date.setTime(111111111111L);
//			LOGGER.info("change date: {}", date);
//			LOGGER.info("cu: {}", cloneUser);
//			System.out.println();

			ObjectToolkits.deepClone(user);
		}

		long end = System.currentTimeMillis();
		LOGGER.info("waste time: {}", new Object[] { (end - start) });
	}

}
