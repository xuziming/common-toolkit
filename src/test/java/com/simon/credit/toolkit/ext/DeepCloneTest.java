package com.simon.credit.toolkit.ext;

import org.slf4j.Logger;

import com.simon.credit.toolkit.ext.lang.ObjectToolkits;
import com.simon.credit.toolkit.ext.logger.FormatLoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 深度克隆测试
 * @author XUZIMING 2018-08-19
 */
public class DeepCloneTest {
	private static final Logger LOGGER = FormatLoggerFactory.getLogger(DeepCloneTest.class);

	public static void main(String[] args) {
//		LOGGER.info("origin date: {}", date);
//		long time = date.getTime();

		Map<String, User> map = new HashMap<String, User>(16);
		User user = new User("tom", 18);
		map.put("user", user);
		Map<String, User> map1 = ObjectToolkits.deepClone(map);
		Map<String, User> map2 = ObjectToolkits.deepClone(map);

		System.out.println(map1.get("user"));
		System.out.println(map2.get("user"));

		System.out.println(map1.get("user") == map2.get("user"));

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
//			date.setTime(time);
//			User cloneUser = ObjectToolkits.deepClone(user);
//			date.setTime(111111111111L);
//			LOGGER.info("change date: {}", date);
//			LOGGER.info("cu: {}", cloneUser);
//			System.out.println();

//			ObjectToolkits.deepClone(user);
		}

		long end = System.currentTimeMillis();
		LOGGER.info("waste time: {}", new Object[] { (end - start) });
	}

}