package com.simon.credit.toolkit.ext.http;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simon.credit.exception.MultipleInstanceException;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.io.IOToolkits;
import com.simon.credit.toolkit.ext.lang.ObjectToolkits;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * HTTP请求工具类
 * @author XUZIMING 2017-08-01
 */
public class OkHttpToolkits {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();

		// build uri
		String uri = "http://www.huadiled.com.cn/plus/diy.php";

		// build http parameters
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("action", "post");
		parameters.add("diyid", "1");
		parameters.add("do", "2");
		parameters.add("title", "6.构建“知事识人”干部队伍管理评价体系");
		parameters.add("wzid", "115");// 这个是我们项目的id, 别搞错了，之前成156，投给了：46.推进韧性健康城区公共卫生应急体系改革（区应急管理局）
		parameters.add("pingjia", "1");
		parameters.add("vdcode", "7524");// 验证码
		parameters.add("dede_fields", "title,text;wzid,text;pingjia,radio;liuyan,multitext;ip,text");
		parameters.add("dede_fieldshash", "0d6bd5c6fb3e3a27f87eb576f4afe38f");

		// build http headers
		HttpHeaders headers = new HttpHeaders();
		headers.clear();
		headers.add("Cookie", "PHPSESSID=ttejddlem60l2vn122gbu2p5f0");
		headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		headers.add("Accept-Encoding", "gzip, deflate");
		headers.add("Accept-Language", "zh-CN,zh;q=0.9");
		headers.add("Cache-Control", "no-cache");
		headers.add("Connection", "keep-alive");
		headers.add("Content-Length", "1049");
		headers.add("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7sMwCxMVh6A6R72n");
		headers.add("Host", "www.huadiled.com.cn");
		headers.add("Origin", "http://www.huadiled.com.cn");
		headers.add("Pragma", "no-cache");
		headers.add("Referer", "http://www.huadiled.com.cn/zdggxm/115.html");
		headers.add("Upgrade-Insecure-Requests", "1");
		headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);

		int voteTimes = 1000;// 投票票数，可根据需要进行修改
		for (int i = 0; i < voteTimes; i++) {
			threadPool.execute(() -> {
				String result = new RestTemplate().postForObject(uri, httpEntity, String.class);
				System.out.println(result);
			});
		}
	}

	private static final int CONNECT_TIMEOUT = 5;
	private static final int READ_TIMEOUT 	 = 10;
	private static final int WRITE_TIMEOUT 	 = 10;

	/** 静态内部类实现单例模式 */
	private static class SingletonHolder {
		private static final OkHttpToolkits INSTANCE = new OkHttpToolkits();
	}

	private OkHttpToolkits() {
		// 避免采用反射方式直接调用私有构造器, 从而破解单例模式
		if (SingletonHolder.INSTANCE != null) {
			throw new MultipleInstanceException(OkHttpToolkits.class);
		}
	}

	private Object readResolve() throws ObjectStreamException {
		// 避免反序列化破解单例模式: 发序列化时, 若定义了readResolve(), 
		// 则直接返回此方法指定的对象, 而无需单独再创建新对象.
		return SingletonHolder.INSTANCE;
	}

	/** 获取单例对象 */
	public static OkHttpToolkits getInstance() {
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
//			byte[] bytes = response.body().bytes();
//			FileUtils.writeByteArrayToFile(new File("d:/img.jpg"), bytes);
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
	private String doPostInternally(String url, Map<String, Object> paramMap) {
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
			return this.doPostInternally(url, new JSONObject());
		} else {
			return this.doPostInternally(url, JSON.parseObject(paramJSONString));
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

	/**
	 * 向指定URL发送GET方法的请求
	 * @param url 发送请求的URL
	 * @param jsonParams 请求参数(JSON字符串格式)
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String jsonGet(String url, String jsonParams) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + jsonParams;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			// connection.setRequestProperty("accept", "*/*");
			// connection.setRequestProperty("connection", "Keep-Alive");
			// connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url 发送请求的 URL
	 * @param jsonParams 请求参数(JSON字符串格式)
	 * @return 所代表远程资源的响应结果
	 */
	public static String jsonPost(String url, String jsonParams) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "applicaton/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(jsonParams);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
