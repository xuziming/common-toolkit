package com.simon.credit.toolkit.ext.format;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FreeMarkerFormatterTest {

    public static void main(String[] args) throws InterruptedException {
        // 第一次：初始化对象(减少对象创建过程，提升后续格式化速度)
        formatOnce();

        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                String str = formatOnce();
                System.out.println(str);
                latch.countDown();
            }).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
    }

    private static String formatOnce() {
        String templateContent = "尊敬的用户：<#if (memberNo!0) != 0>${userName}, </#if>现在时间为：${now}";
        Map<String, Object> dataModel = new HashMap<>(8);
        dataModel.put("memberNo", 1000001);
        dataModel.put("userName", "simon");
        dataModel.put("now", "2020-08-15");
        return FreeMarkerFormatter.getInstance().format(templateContent, dataModel);
    }

}