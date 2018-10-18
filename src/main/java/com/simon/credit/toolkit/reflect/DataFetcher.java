package com.simon.credit.toolkit.reflect;

/**
 * 数据抓取接口
 * @author XUZIMING 2018-07-29
 * @param <S> 源对象类型
 * @param <T> 目标值类型
 */
public interface DataFetcher<S, T> {

	T fetch(S source);

}