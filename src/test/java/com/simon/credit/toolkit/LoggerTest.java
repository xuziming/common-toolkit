package com.simon.credit.toolkit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.simon.credit.toolkit.logger.FormatLoggerFactory;

public class LoggerTest {
	private static final Logger LOGGER = FormatLoggerFactory.getLogger(LoggerTest.class);

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", new Date());
		map.put("where", null);
		map.put("number", 123456);
		map.put("name", "java");

		LOGGER.info("map: {}", map);
	}

}
