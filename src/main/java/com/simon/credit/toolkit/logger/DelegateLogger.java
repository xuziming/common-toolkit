package com.simon.credit.toolkit.logger;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Delegate Logger
 * @author XUZIMING 2018-08-15
 */
public class DelegateLogger implements Logger {

	protected Logger delegator;

	public DelegateLogger(Logger delegator) {
		this.delegator = delegator;
	}

	@Override
	public String getName() {
		return delegator.getName();
	}

	@Override
	public boolean isTraceEnabled() {
		return delegator.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		delegator.trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		delegator.trace(format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		delegator.trace(format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object[] argArray) {
		delegator.trace(format, argArray);
	}

	@Override
	public void trace(String msg, Throwable t) {
		delegator.trace(msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return delegator.isTraceEnabled();
	}

	@Override
	public void trace(Marker marker, String msg) {
		delegator.trace(marker, msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		delegator.trace(marker, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		delegator.trace(marker, format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object[] argArray) {
		delegator.trace(marker, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		delegator.trace(marker, msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return delegator.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		delegator.debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		delegator.debug(format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		delegator.debug(format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object[] argArray) {
		delegator.debug(format, argArray);
	}

	@Override
	public void debug(String msg, Throwable t) {
		delegator.debug(msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return delegator.isDebugEnabled(marker);
	}

	@Override
	public void debug(Marker marker, String msg) {
		delegator.debug(marker, msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		delegator.debug(marker, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		delegator.debug(marker, format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object[] argArray) {
		delegator.debug(marker, format, argArray);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		delegator.debug(marker, msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return delegator.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		delegator.info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		delegator.info(format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		delegator.info(format, arg1, arg2);
	}

	@Override
	public void info(String format, Object[] argArray) {
		delegator.info(format, argArray);
	}

	@Override
	public void info(String msg, Throwable t) {
		delegator.info(msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return delegator.isInfoEnabled(marker);
	}

	@Override
	public void info(Marker marker, String msg) {
		delegator.info(marker, msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		delegator.info(marker, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		delegator.info(marker, format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object[] argArray) {
		delegator.info(marker, format, argArray);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		delegator.info(marker, msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return delegator.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		delegator.warn(msg);
	}

	@Override
	public void warn(String format, Object arg) {
		delegator.warn(format, arg);
	}

	@Override
	public void warn(String format, Object[] argArray) {
		delegator.warn(format, argArray);
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		delegator.warn(format, arg1, arg2);
	}

	@Override
	public void warn(String msg, Throwable t) {
		delegator.warn(msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return delegator.isDebugEnabled(marker);
	}

	@Override
	public void warn(Marker marker, String msg) {
		delegator.warn(marker, msg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		delegator.warn(marker, format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		delegator.warn(marker, format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object[] argArray) {
		delegator.warn(marker, format, argArray);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		delegator.warn(marker, msg, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return delegator.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		delegator.error(msg);
	}

	@Override
	public void error(String format, Object arg) {
		delegator.error(format, arg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		delegator.error(format, arg1, arg2);
	}

	@Override
	public void error(String format, Object[] argArray) {
		delegator.error(format, argArray);
	}

	@Override
	public void error(String msg, Throwable t) {
		delegator.error(msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return delegator.isErrorEnabled(marker);
	}

	@Override
	public void error(Marker marker, String msg) {
		delegator.error(marker, msg);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		delegator.error(marker, format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		delegator.error(marker, format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object[] argArray) {
		delegator.error(marker, format, argArray);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		delegator.error(marker, msg, t);
	}

}
