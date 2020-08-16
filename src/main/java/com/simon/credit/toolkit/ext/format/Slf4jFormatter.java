package com.simon.credit.toolkit.ext.format;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 文本格式化(对slf4j的封装提取)
 * @author XUZIMING 2018-01-08
 */
public final class Slf4jFormatter {

    /** Don't let anyone instantiate this class. */
	private Slf4jFormatter() {
        // AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        // see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 格式化字符串,此方法是抽取slf4j的核心方法.
     * 
     * <p>
     * 在java中,常会拼接字符串生成新的字符串值,在字符串拼接过程中容易写错或者位置写错<br>
     * <br>
     * slf4j的log支持格式化输出log,比如:<br>
     * </p>
     * 
     * <ul>
     * <li>LOGGER.debug("{}", "log info...");</li>
     * <li>LOGGER.info("{},{}", "log1", "log2");</li>
     * </ul>
     * 
     * 这种写法非常简洁且有效,而且不易出错
     * 
     * <br>
     * 因此,你可以在代码中采用这样的写法:
     * 
     * <pre>
     * throw new IllegalArgumentException(Slf4jFormatter
     * .format("class:[{}], method:[{}], message:[{}]", clazz, method, msg));
     * 或者: 
     * return Slf4jUtil.format("{} [{}]", encode, encode.length());
     * </pre>
     * 
     * @param messagePattern message的格式,比如 callbackUrl:[{}] ,length:[{}]
     * @param args 参数
     * @return 若<code>messagePattern</code>是null,返回null<br>
     *         若<code>args</code>是null,返回<code>messagePattern</code><br>
     * @since slf4j-api-1.6.0
     */
    public static String format(String messagePattern, Object...args) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(messagePattern, args);
        return formattingTuple.getMessage();
    }

}