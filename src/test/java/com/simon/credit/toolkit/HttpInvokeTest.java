package com.simon.credit.toolkit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.simon.credit.toolkit.http.HttpToolkits;

public class HttpInvokeTest {

	class HttpCallable implements Callable<String> {

		public String call() throws Exception {
			String url = "http://www.kuaidi100.com/query";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("type", "yuantong");
			paramMap.put("postid", "11111111111");

			return HttpToolkits.getInstance().httpPost(url, paramMap);
		}

	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int nThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);

		for (int i = 0; i < 10; i++) {
			FutureTask<String> task = new FutureTask<String>(new HttpInvokeTest().new HttpCallable());

			Future<?> request = executor.submit(task);
			if (!request.isCancelled()) {
				System.out.println("=== resp: " + task.get());
			}
		}

		executor.shutdown();
	}

}
