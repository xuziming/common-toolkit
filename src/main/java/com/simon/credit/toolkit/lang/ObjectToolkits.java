package com.simon.credit.toolkit.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.esotericsoftware.kryo.Kryo;
import com.simon.credit.toolkit.common.CommonToolkits;

/**
 * 对象基础工具类
 *
 * @author XUZIMING 2018-08-19
 */
public class ObjectToolkits {

    /**
     * kryo是非线程安全的, 每个线程要使用自己的kryo
     */
    private static final ThreadLocal<Kryo> KRYO_TL = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            return new Kryo();
        }
    };

    /**
     * 深度复制
     *
     * @param object 待复制对象
     * @return
     */
    public static <T> T deepClone(T object) {
        if (object == null) {
            return null;
        }
        return KRYO_TL.get().copy(object);
    }

    public static <T> String toJSONString(T object) {
        if (object == null) {
            return "null";
        }
        return JSON.toJSONStringWithDateFormat(object,
                CommonToolkits.DEFFAULT_DATE_FORMAT, SerializerFeature.WriteMapNullValue);
    }

    public static String toString(Object object) {
        return toString(object, null);
    }

    public static String toString(Object object, String nullDefault) {
        return (object != null) ? object.toString() : nullDefault;
    }

    public static final String trimToNull(Object object) {
        return CommonToolkits.trim(toString(object), null);
    }

    public static final String trimToEmpty(Object object) {
        return CommonToolkits.trim(toString(object), "");
    }

}
