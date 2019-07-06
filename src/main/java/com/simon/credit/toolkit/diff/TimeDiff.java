package com.simon.credit.toolkit.diff;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

public class TimeDiff {

	public static final long between(Date date1, Date date2, TimeUnit timeUnit) {
		// 调用.getTime()方法进行相减, 得出来的是毫秒
		long millisecond = date2.getTime() - date1.getTime();

		switch (timeUnit) {
			case MILLISECONDS: return millisecond;
			case SECONDS	 : return millisecond / 1000;
			case MINUTES	 : return millisecond / 1000 / 60;
			case HOURS		 : return millisecond / 1000 / 60 / 60;
			default			 : return millisecond;
		}
	}

	public static void main(String[] args) {
		System.out.println(between(new Date(), DateUtils.addDays(new Date(), 1), TimeUnit.MINUTES));
	}

}
