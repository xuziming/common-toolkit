package com.simon.credit.toolkit.logger;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.logger.format.Slf4jFormatter;

/**
 * Format Logger(delegation pattern)
 * @author XUZIMING 2018-08-15
 */
public final class FormatLogger extends DelegateLogger {

	public FormatLogger(Logger delegator) {
		super(delegator);
	}

	/****************************************************************************************************/
	// LOGGER.debug(...)
	/****************************************************************************************************/

	@Override
	public void debug(String messagePattern, Object arg) {
		if (delegator.isDebugEnabled()) {
			delegator.debug(messagePattern, format(arg));
		} else {
			consolePrint(messagePattern, format(arg));
		}
	}

	@Override
	public void debug(String messagePattern, Object arg1, Object arg2) {
		if (delegator.isDebugEnabled()) {
			delegator.debug(messagePattern, format(arg1), format(arg2));
		} else {
			consolePrint(messagePattern, format(arg1), format(arg2));
		}
	}

	public void debug(String messagePattern, Object[] args) {
		if (delegator.isDebugEnabled()) {
			delegator.debug(messagePattern, format(args));
		} else {
			consolePrint(messagePattern, format(args));
		}
	}

	/****************************************************************************************************/
	// LOGGER.info(...)
	/****************************************************************************************************/

	@Override
	public void info(String messagePattern, Object arg) {
		if (delegator.isInfoEnabled()) {
			delegator.info(messagePattern, format(arg));
		} else {
			consolePrint(messagePattern, format(arg));
		}
	}

	@Override
	public void info(String messagePattern, Object arg1, Object arg2) {
		if (delegator.isInfoEnabled()) {
			delegator.info(messagePattern, format(arg1), format(arg2));
		} else {
			consolePrint(messagePattern, format(arg1), format(arg2));
		}
	}

	public void info(String messagePattern, Object[] args) {
		if (delegator.isInfoEnabled()) {
			delegator.info(messagePattern, format(args));
		} else {
			consolePrint(messagePattern, format(args));
		}
	}

	/****************************************************************************************************/
	// LOGGER.error(...)
	/****************************************************************************************************/

	@Override
	public void error(String messagePattern, Object arg) {
		if (delegator.isErrorEnabled()) {
			delegator.error(messagePattern, format(arg));
		} else {
			consolePrint(messagePattern, format(arg));
		}
	}

	@Override
	public void error(String messagePattern, Object arg1, Object arg2) {
		if (delegator.isErrorEnabled()) {
			delegator.error(messagePattern, format(arg1), format(arg2));
		} else {
			consolePrint(messagePattern, format(arg1), format(arg2));
		}
	}

	public void error(String messagePattern, Object[] args) {
		if (delegator.isErrorEnabled()) {
			delegator.error(messagePattern, format(args));
		} else {
			consolePrint(messagePattern, format(args));
		}
	}

	public static <T> String format(T object) {
		return JSON.toJSONStringWithDateFormat(object,
			CommonToolkits.DEFFAULT_DATE_FORMAT, SerializerFeature.WriteMapNullValue);
	}

	public static Object[] format(Object[] args) {
		for (int i = 0; i < args.length; i++) {
			args[i] = format(args[i]);
		}
		return args;
	}

	/**
	 * print message to console
	 * @param messagePattern
	 * @param args
	 */
	private void consolePrint(String messagePattern, Object... args) {
		if (CommonToolkits.isNotEmpty(messagePattern)) {
			System.out.println(Slf4jFormatter.format(messagePattern, args));
		}
	}

}
