package com.simon.credit.toolkit.http;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simon.credit.exception.SingletonException;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.io.IOToolkits;
import com.simon.credit.toolkit.lang.ObjectToolkits;

/**
 * HTTP请求工具类(基于OkHttp组件)
 * @author XUZIMING 2017-08-01
 */
public class OKHttpToolkits {

	private static final int CONNECT_TIMEOUT = 5;
	private static final int READ_TIMEOUT 	 = 10;
	private static final int WRITE_TIMEOUT 	 = 10;

	/** 静态内部类实现单例模式 */
	private static class SingletonHolder {
		private static final OKHttpToolkits INSTANCE = new OKHttpToolkits();
	}

	private OKHttpToolkits() {
		// 避免采用反射方式直接调用私有构造器, 从而破解单例模式
		if (SingletonHolder.INSTANCE != null) {
			throw new SingletonException(OKHttpToolkits.class);
		}
	}

	private Object readResolve() throws ObjectStreamException {
		// 避免反序列化破解单例模式: 发序列化时, 若定义了readResolve(), 
		// 则直接返回此方法指定的对象, 而无需单独再创建新对象.
		return SingletonHolder.INSTANCE;
	}

	/** 获取单例对象 */
	public static OKHttpToolkits getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/** OkHttp官方并不建议开发者创建多个OkHttpClient, 因此全局使用一个 */
	private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
			.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)// 设置读取超时时间
			.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)// 设置写的超时时间
			.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)// 设置连接超时时间
			.build();

	/**
	 * HTTP get请求
	 * 
	 * @param url 网络地址
	 * @return
	 */
	public String httpGet(String url) {
		Request request = new Request.Builder().url(url).build();
		Response response = null;

		try {
			response = CLIENT.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOToolkits.close(response);
		}

		return null;
	}

	/**
	 * HTTP post请求
	 * 
	 * @param url 网络地址
	 * @param paramMap 请求参数map
	 * @return
	 */
	private String doPost(String url, Map<String, Object> paramMap) {
		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		if (paramMap != null && !paramMap.isEmpty()) {
			for (String key : paramMap.keySet()) {
				String value = ObjectToolkits.trimToEmpty(paramMap.get(key));
				formBodyBuilder.addEncoded(key, CommonToolkits.urlEncodeUTF8(value));
			}
		}

		Request request = new Builder().url(url).post(formBodyBuilder.build()).build();
		Response response = null;

		try {
			response = CLIENT.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOToolkits.close(response);
		}

		return null;
	}

	/**
	 * HTTP post请求
	 * 
	 * @param url 网络地址
	 * @param paramJSONString JSON字符串形式的请求参数
	 * @return
	 */
	private String httpPost(String url, String paramJSONString) {
		if (CommonToolkits.isEmptyContainNull(paramJSONString)) {
			return this.doPost(url, new JSONObject());
		} else {
			return this.doPost(url, JSON.parseObject(paramJSONString));
		}
	}

	/**
	 * HTTP post请求
	 * 
	 * @param url 网络地址
	 * @param paramMap 请求参数Map
	 * @return
	 */
	public String httpPost(String url, Map<String, Object> paramMap) {
		String paramJSONString = JSON.toJSONString(paramMap);
		return this.httpPost(url, paramJSONString);
	}

}
