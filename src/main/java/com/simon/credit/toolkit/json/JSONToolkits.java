package com.simon.credit.toolkit.json;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * JSON工具类
 * @author XUZIMING 2018-11-14
 */
public class JSONToolkits {

	/**
	 * 为jsonObj添加key-value对(对象的属性名与属性值)
	 * <pre>默认:key存在时, 进行覆盖操作</pre>
	 * @param jsonObj 源JSON对象
	 * @param toAddObj 待添加的对象(可为任意对象)
	 */
	public static final void addEntries(JSONObject jsonObj, Object toAddObj) {
		addEntries(jsonObj, toAddObj, true);
	}

	/**
	 * 为jsonObj添加键值对(对象的属性名与属性值)
	 * <pre>
	 * cover为true , 当键存在则进行值覆盖操作.
	 * cover为false, 当键存在则不进行覆盖操作(即:跳过该键对应的键值对的添加).
	 * </pre>
	 * @param jsonObj 源JSON对象
	 * @param toAddObj 待添加的对象(可为任意对象)
	 * @param cover 是否覆盖(true:覆盖, false:不覆盖)
	 */
	public static final void addEntries(JSONObject jsonObj, Object toAddObj, boolean cover) {
		if (jsonObj == null || toAddObj == null) {
			return;
		}

		String jsonStr = JSON.toJSONString(toAddObj);
		try {
			JSONObject toAddJsonObj = JSON.parseObject(jsonStr);
			if (cover) {
				jsonObj.putAll(toAddJsonObj);
				return;
			}

			for (Map.Entry<String, Object> toAddEntry : toAddJsonObj.entrySet()) {
				if (jsonObj.containsKey(toAddEntry.getKey())) {
					continue;
				}
				jsonObj.put(toAddEntry.getKey(), toAddEntry.getValue());
			}
		} catch (JSONException e) {
			// ignore
		}
	}

	public static void main(String[] args) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("aa", "bb");
		jsonObj.put("bb", "bb");

		JSONObject toAddJsonObj = new JSONObject();
		toAddJsonObj.put("cc", "cc");
		toAddJsonObj.put("aa", "ff");

		// addEntries(jsonObj, toAddJsonObj);
		addEntries(jsonObj, toAddJsonObj, false);
		System.out.println(jsonObj.toJSONString());
	}

}
