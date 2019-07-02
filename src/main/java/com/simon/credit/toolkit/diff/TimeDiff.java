package com.simon.credit.toolkit.diff;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

public class TimeDiff {

	public static final long between(Date date1, Date date2, TimeUnit timeUnit) {
		// 调用.getTime()方法进行相减, 得出来的是毫秒
		long millisecond = date2.getTime() - date1.getTime();

		// 计算小时
		long hour = millisecond / 1000 / 60 / 60;

		// 计算分钟
		long minute = millisecond / 1000 / 60;

		// 计算秒数
		long second = millisecond / 1000;

		switch (timeUnit) {
			case MILLISECONDS: return millisecond;
			case SECONDS	 : return second;
			case MINUTES	 : return minute;
			case HOURS		 : return hour;
			default			 : return millisecond;
		}
	}

	public static void main(String[] args) {
		System.out.println(between(new Date(), DateUtils.addDays(new Date(), 1), TimeUnit.MINUTES));
	}

}
