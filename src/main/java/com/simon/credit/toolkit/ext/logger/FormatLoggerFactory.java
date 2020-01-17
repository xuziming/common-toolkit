package com.simon.credit.toolkit.ext.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Format Logger Factory
 * @author XUZIMING 2018-08-15
 */
public final class FormatLoggerFactory {

	public static <T> Logger getLogger(Class<T> clazz) {
		return new FormatLogger(delegate(clazz));
	}

	private static <T> Logger delegate(Class<T> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

}
