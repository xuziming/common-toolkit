package com.simon.credit.toolkit.ext.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.esotericsoftware.kryo.Kryo;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.reflect.PropertyToolkits;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 对象基础工具类
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
     * @param object 待复制对象
     * @return
     */
    public static <T> T deepClone(T object) {
        if (object == null) {
            return null;
        }
        return KRYO_TL.get().copy(object);
    }

    /**
     * 对象转为JSON字符串
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String toJSONString(T object) {
        if (object == null) {
            return "null";
        }
        return JSON.toJSONStringWithDateFormat(object,
                CommonToolkits.DEFFAULT_DATE_FORMAT, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 对象转为字符串
     * @param object
     * @return
     */
    public static String toString(Object object) {
        return toString(object, null);
    }

    /**
     * 对象转为字符串
     * @param object 源对象
     * @param nullDefault 源对象为空时的默认值
     * @return
     */
    public static String toString(Object object, String nullDefault) {
        return (object != null) ? object.toString() : nullDefault;
    }

    /**
     * 针对对象的字符串属性进行trimToNull操作
     * @param obj
     * @return
     */
    public static final Object trimToNullForStringFileds(Object obj) {
        if (obj == null) {
            return null;
        }

        if (isBasicDataType(obj)) {
            return obj;
        }

        if (obj instanceof String) {
            return CommonToolkits.trimToNull((String) obj);
        }

        if (obj instanceof Date) {
            return obj;
        }

        if (obj instanceof Collection) {
            for (Object element : (Collection) obj) {
                trimToNullForStringFileds(element);
            }
        }

        if (obj instanceof Map) {
            Map<Object, Object> map = (Map) obj;
            for (Object key : map.keySet()) {
                Object value = trimToNullForStringFileds(map.get(key));
                map.put(key, value);
            }
        }

        String[] fieldNames = getFieldNames(obj);
        for (String fieldName : fieldNames) {
            String[] filter = {"MAX_ARRAY_SIZE", "EMPTY_ELEMENTDATA", "DEFAULT_CAPACITY", "serialVersionUID", "elementData", "size"};
            if (!ArrayUtils.contains(filter, fieldName)) {
                try {
                    Object fieldValue = PropertyToolkits.getProperty(obj, fieldName);

                    if (fieldValue != null) {
                        if (fieldValue instanceof String) {
                            PropertyToolkits.setProperty(obj, fieldName, CommonToolkits.trimToNull((String) fieldValue));
                        }

                        if (fieldValue instanceof Collection) {
                            for (Object element : (Collection) fieldValue) {
                                trimToNullForStringFileds(element);
                            }
                        }

                        if (fieldValue instanceof Map) {
                            Map<Object, Object> map = (Map) fieldValue;
                            for (Object key : map.keySet()) {
                                Object value = trimToNullForStringFileds(map.get(key));
                                map.put(key, value);
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        return obj;
    }

    public static final void copyProperties(Object destination, Object origin, String... properties) {
        if (CommonToolkits.isEmpty((Object[]) properties)) {
            return;
        }

        for (String property : properties) {
            try {
                Object value = PropertyToolkits.getProperty(origin, property);
                if (CommonToolkits.isNotEmpty(value)) {
                    PropertyToolkits.setProperty(destination, property, value);
                }
            } catch (Exception e) {
            }
        }
    }

    private static final boolean isBasicDataType(Object obj) {
        if (obj.getClass().isPrimitive()) {
            return true;
        }

        try {
            if (hasField(obj, "TYPE") && ((Class) obj.getClass().getField("TYPE").get(null)).isPrimitive()) {
                return true;
            }
        } catch (Exception e) {
            // TODO log error msg
        }

        return false;
    }

    public static final boolean hasField(Object obj, String field) {
        String[] fieldNames = getFieldNames(obj);
        for (String fieldName : fieldNames) {
            if (field.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public static final String[] getFieldNames(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

}
