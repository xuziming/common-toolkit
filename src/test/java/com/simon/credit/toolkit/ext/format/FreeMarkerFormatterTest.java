package com.simon.credit.toolkit.ext.format;

import java.util.HashMap;
import java.util.Map;

public class FreeMarkerFormatterTest {

    public static void main(String[] args) {
        // 第一次：初始化对象
        formatOnce();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String str = formatOnce();
            System.out.println(str);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
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