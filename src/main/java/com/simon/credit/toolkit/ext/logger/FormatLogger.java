package com.simon.credit.toolkit.ext.logger;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.ext.logger.format.Slf4jFormatter;

/**
 * Format Logger(delegation pattern)
 * @author XUZIMING 2018-08-15
 */
public final class FormatLogger extends DelegateLogger {

	/** 默认日期格式 */
	private static final String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private boolean isConsoleEnabled = false;

	public FormatLogger(Logger delegator) {
		this(delegator, true);
	}

	public FormatLogger(Logger delegator, boolean isConsoleEnabled) {
		super(delegator);
		this.isConsoleEnabled = isConsoleEnabled;
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

	protected static <T> String format(T object) {
		if (object == null) {
			return null;
		}
		return JSON.toJSONStringWithDateFormat(object, DEFFAULT_DATE_FORMAT, SerializerFeature.WriteMapNullValue);
	}

	protected static Object[] format(Object[] args) {
		if (args == null || args.length == 0) {
			return args;
		}

		Object[] formatArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			formatArgs[i] = format(args[i]);
		}
		return formatArgs;
	}

	/**
	 * print message to console
	 * @param messagePattern
	 * @param args
	 */
	public void consolePrint(String messagePattern, Object... args) {
		if (!isConsoleEnabled) {
			return;
		}
		if (CommonToolkits.isEmptyContainNull(messagePattern)) {
			return;
		}
		System.out.println(Slf4jFormatter.format(messagePattern, args));
	}

	public void setConsoleEnabled(boolean isConsoleEnabled) {
		this.isConsoleEnabled = isConsoleEnabled;
	}

}
