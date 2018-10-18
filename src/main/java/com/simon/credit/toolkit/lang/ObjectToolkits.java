package com.simon.credit.toolkit.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.esotericsoftware.kryo.Kryo;
import com.simon.credit.toolkit.common.CommonToolkits;

/**
 * 对象基础工具类
 * @author XUZIMING 2018-08-19
 */
public class ObjectToolkits {

	/** kryo是非线程安全的, 每个线程要使用自己的kryo */
	private static final ThreadLocal<Kryo> KRYO_TL = new ThreadLocal<Kryo>() {
		protected Kryo initialValue() {
			return new Kryo();
		}
	};

	/**
	 * 深度复制
	 * @param object 待复制对象
	 * @return
	 */
	public static <T> T deepClone(T object) {
		return KRYO_TL.get().copy(object);
	}

	public static <T> String toJSONString(T object) {
		return JSON.toJSONStringWithDateFormat(object,
			CommonToolkits.DEFFAULT_DATE_FORMAT, SerializerFeature.WriteMapNullValue);
	}

	public static String[] toJSONStringArray(Object[] args) {
		String[] strAry = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			strAry[i] = toJSONString(args[i]);
		}
		return strAry;
	}

	public static String toString(Object object, String nullDefault) {
        return (object != null) ? object.toString() : nullDefault;
    }

	public static final String trimToNull(Object object) {
		if (CommonToolkits.isEmptyContainNull(String.valueOf(object))) {
			return null;
		}
		return object.toString().trim();
	}

	public static final String trimToEmpty(Object object) {
		return CommonToolkits.trimToEmpty(String.valueOf(object));
	}

}
