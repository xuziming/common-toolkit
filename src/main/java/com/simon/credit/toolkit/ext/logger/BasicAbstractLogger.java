package com.simon.credit.toolkit.ext.logger;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Basic Abstract Logger
 * @author XUZIMING 2018-08-15
 */
public abstract class BasicAbstractLogger implements Logger {

	protected abstract boolean isConsoleEnabled();

	protected Logger delegator;

	public BasicAbstractLogger(Logger delegator) {
		this.delegator = delegator;
	}

	public String getName() {
		return delegator.getName();
	}

	public boolean isTraceEnabled() {
		return delegator.isTraceEnabled();
	}

	public void trace(String msg) {
		delegator.trace(msg);
	}

	public void trace(String format, Object arg) {
		delegator.trace(format, arg);
	}

	public void trace(String format, Object arg1, Object arg2) {
		delegator.trace(format, arg1, arg2);
	}

	public void trace(String format, Object[] argArray) {
		delegator.trace(format, argArray);
	}

	public void trace(String msg, Throwable t) {
		delegator.trace(msg, t);
	}

	public boolean isTraceEnabled(Marker marker) {
		return delegator.isTraceEnabled();
	}

	public void trace(Marker marker, String msg) {
		delegator.trace(marker, msg);
	}

	public void trace(Marker marker, String format, Object arg) {
		delegator.trace(marker, format, arg);
	}

	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		delegator.trace(marker, format, arg1, arg2);
	}

	public void trace(Marker marker, String format, Object[] argArray) {
		delegator.trace(marker, format, argArray);
	}

	public void trace(Marker marker, String msg, Throwable t) {
		delegator.trace(marker, msg, t);
	}

	public boolean isDebugEnabled() {
		return delegator.isDebugEnabled();
	}

	public void debug(String msg) {
		delegator.debug(msg);
	}

	public void debug(String format, Object arg) {
		delegator.debug(format, arg);
	}

	public void debug(String format, Object arg1, Object arg2) {
		delegator.debug(format, arg1, arg2);
	}

	public void debug(String format, Object[] argArray) {
		delegator.debug(format, argArray);
	}

	public void debug(String msg, Throwable t) {
		delegator.debug(msg, t);
	}

	public boolean isDebugEnabled(Marker marker) {
		return delegator.isDebugEnabled(marker);
	}

	public void debug(Marker marker, String msg) {
		delegator.debug(marker, msg);
	}

	public void debug(Marker marker, String format, Object arg) {
		delegator.debug(marker, format, arg);
	}

	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		delegator.debug(marker, format, arg1, arg2);
	}

	public void debug(Marker marker, String format, Object[] argArray) {
		delegator.debug(marker, format, argArray);
	}

	public void debug(Marker marker, String msg, Throwable t) {
		delegator.debug(marker, msg, t);
	}

	public boolean isInfoEnabled() {
		return delegator.isInfoEnabled();
	}

	public void info(String msg) {
		delegator.info(msg);
	}

	public void info(String format, Object arg) {
		delegator.info(format, arg);
	}

	public void info(String format, Object arg1, Object arg2) {
		delegator.info(format, arg1, arg2);
	}

	public void info(String format, Object[] argArray) {
		delegator.info(format, argArray);
	}

	public void info(String msg, Throwable t) {
		delegator.info(msg, t);
	}

	public boolean isInfoEnabled(Marker marker) {
		return delegator.isInfoEnabled(marker);
	}

	public void info(Marker marker, String msg) {
		delegator.info(marker, msg);
	}

	public void info(Marker marker, String format, Object arg) {
		delegator.info(marker, format, arg);
	}

	public void info(Marker marker, String format, Object arg1, Object arg2) {
		delegator.info(marker, format, arg1, arg2);
	}

	public void info(Marker marker, String format, Object[] argArray) {
		delegator.info(marker, format, argArray);
	}

	public void info(Marker marker, String msg, Throwable t) {
		delegator.info(marker, msg, t);
	}

	public boolean isWarnEnabled() {
		return delegator.isWarnEnabled();
	}

	public void warn(String msg) {
		delegator.warn(msg);
	}

	public void warn(String format, Object arg) {
		delegator.warn(format, arg);
	}

	public void warn(String format, Object[] argArray) {
		delegator.warn(format, argArray);
	}

	public void warn(String format, Object arg1, Object arg2) {
		delegator.warn(format, arg1, arg2);
	}

	public void warn(String msg, Throwable t) {
		delegator.warn(msg, t);
	}

	public boolean isWarnEnabled(Marker marker) {
		return delegator.isDebugEnabled(marker);
	}

	public void warn(Marker marker, String msg) {
		delegator.warn(marker, msg);
	}

	public void warn(Marker marker, String format, Object arg) {
		delegator.warn(marker, format, arg);
	}

	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		delegator.warn(marker, format, arg1, arg2);
	}

	public void warn(Marker marker, String format, Object[] argArray) {
		delegator.warn(marker, format, argArray);
	}

	public void warn(Marker marker, String msg, Throwable t) {
		delegator.warn(marker, msg, t);
	}

	public boolean isErrorEnabled() {
		return delegator.isErrorEnabled();
	}

	public void error(String msg) {
		delegator.error(msg);
	}

	public void error(String format, Object arg) {
		delegator.error(format, arg);
	}

	public void error(String format, Object arg1, Object arg2) {
		delegator.error(format, arg1, arg2);
	}

	public void error(String format, Object[] argArray) {
		delegator.error(format, argArray);
	}

	public void error(String msg, Throwable t) {
		delegator.error(msg, t);
	}

	public boolean isErrorEnabled(Marker marker) {
		return delegator.isErrorEnabled(marker);
	}

	public void error(Marker marker, String msg) {
		delegator.error(marker, msg);
	}

	public void error(Marker marker, String format, Object arg) {
		delegator.error(marker, format, arg);
	}

	public void error(Marker marker, String format, Object arg1, Object arg2) {
		delegator.error(marker, format, arg1, arg2);
	}

	public void error(Marker marker, String format, Object[] argArray) {
		delegator.error(marker, format, argArray);
	}

	public void error(Marker marker, String msg, Throwable t) {
		delegator.error(marker, msg, t);
	}

}
