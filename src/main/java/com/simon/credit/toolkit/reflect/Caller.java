package com.simon.credit.toolkit.reflect;

public class Caller {

	/**
	 * 获取调用者的类名
	 */
	public static String getCallerClassName() {
		StackTraceElement[] stack = new Throwable().getStackTrace();
		for (int i = 0; i < stack.length; i++)
		{
			StackTraceElement s = stack[i];
			System.out.format(" ClassName:%d\t%s\n", i, s.getClassName());
			System.out.format("MethodName:%d\t%s\n", i, s.getMethodName());
			System.out.format("  FileName:%d\t%s\n", i, s.getFileName());
			System.out.format("LineNumber:%d\t%s\n\n", i, s.getLineNumber());
		}
		return stack[1].getClassName();
	}

	/**
	 * 获取调用者的方法名
	 */
	public static String getCallerMethodName() {
		return new Throwable().getStackTrace()[1].getMethodName();
	}

}
