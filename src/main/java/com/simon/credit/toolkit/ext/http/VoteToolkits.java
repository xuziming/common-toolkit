package com.simon.credit.toolkit.ext.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FTQW投票神器(依赖Spring RestTemplate)
 * @author XUZIMING953 2020-01-06
 */
public class VoteToolkits {

    /**
     * 带缓存的线程池(建议自己实现线程池)，充分利用电脑的资源
     */
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        vote(10);// 需要投多少票，自行修改即可
    }

    /**
     * 投票方法
     * @param voteTimes 投票票数，可根据实际需要进行修改
     * <pre>
     * 区委项目：http://www.huadiled.com.cn/zdggxm/115.html
     * 查询票数：http://www.huadiled.com.cn/php_ok/pl.php?aid=115
     * </pre>
     */
    private static void vote(int voteTimes) {
        // vote url
        String uri = "http://www.huadiled.com.cn/plus/diy.php";

        // build http parameters
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("action", "post");
        parameters.add("diyid", "1");
        parameters.add("do", "2");
        parameters.add("title", "6.构建“知事识人”干部队伍管理评价体系");
        parameters.add("wzid", "115");// 这个wzid是我们项目的id, 别搞错了! 之前写成了156，投给了《46.推进韧性健康城区公共卫生应急体系改革（区应急管理局）》，给他们投了好几百票
        parameters.add("pingjia", "1");
        parameters.add("vdcode", "7524");// 图形验证码，这个url(http://www.huadiled.com.cn/include/vdimgck.php??)返回的数字
        parameters.add("dede_fields", "title,text;wzid,text;pingjia,radio;liuyan,multitext;ip,text");
        parameters.add("dede_fieldshash", "0d6bd5c6fb3e3a27f87eb576f4afe38f");

        // build http headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "PHPSESSID=ttejddlem60l2vn122gbu2p5f0");// 请求http://www.huadiled.com.cn/include/vdimgck.php??返回的cookie值，Name为PHPSESSID
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

        // 构建请求实体: parameters + headers
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);

        // for循环执行
        for (int i = 0; i < voteTimes; i++) {
            THREAD_POOL.submit(() -> {
                String result = new RestTemplate().postForObject(uri, httpEntity, String.class);
                System.out.println(result);// 打印投票结果，观察投票效果
            });
        }
    }

}
