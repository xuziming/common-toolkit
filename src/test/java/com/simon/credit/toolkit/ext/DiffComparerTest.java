package com.simon.credit.toolkit.ext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.tuple.Pair;

import com.alibaba.fastjson.JSONObject;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.ext.diff.DiffComparer;
import com.simon.credit.toolkit.io.IOToolkits;
import com.simon.credit.toolkit.io.LineIterator;

/**
 * 差异比较器测试
 * @author XUZIMING 2018-11-09
 */
public class DiffComparerTest<T> {

	public static final String[] nations = { 
		"汉族"	, "壮族"		, "回族"		, "满族"		, "维吾尔族"	, "苗族"		, "彝族"		, 
		"土家族"	, "藏族"		, "蒙古族"	, "侗族"		, "布依族"	, "瑶族"		, "白族"		, 
		"朝鲜族"	, "哈尼族"	, "黎族"		, "哈萨克族"	, "傣族"		, "畲族"		, "傈僳族"	, 
		"东乡族"	, "仡佬族"	, "拉祜族"	, "佤族"		, "水族"		, "纳西族"  	, "羌族"		, 
		"土族"	, "仫佬族"	, "锡伯族"	, "柯尔克孜族", "景颇族"	, "达斡尔族"	, "撒拉族"	, 
		"布朗族"	, "毛南族"	, "塔吉克族"	, "普米族"	, "阿昌族"	, "怒族"		, "鄂温克族"	, 
		"京族"	, "基诺族"	, "德昂族"	, "保安族"	, "俄罗斯族"	, "裕固族"	, "乌孜别克族"	, 
		"门巴族"	, "鄂伦春族"	, "独龙族"	, "赫哲族"	, "高山族"	, "珞巴族"	, "塔塔尔族" 
	};

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		DiffComparer<String> comparer = new DiffComparer<String>();
		Pair<Collection<String>, Collection<String>> diff = 
			comparer.diff(new File("d:/codes1.txt"), new File("d:/codes2.txt"));
		long end = System.currentTimeMillis();
		System.out.println("waste time: " + (end - start) + "ms");

		comparer.printDiff(diff);

//		long start = System.currentTimeMillis();
//		DiffComparer<String> comparer = new DiffComparer<String>();
//		Collection<String> json = getAreaInfo(new File("d:/district-less.txt"), true);
//		Collection<String> regionalism = getAreaInfo(new File("d:/小贷区县名称.txt"), true);
//
//		Pair<Collection<String>, Collection<String>> diff = comparer.diff(json, regionalism);
//		long end = System.currentTimeMillis();
//		System.out.println("waste time: " + (end - start) + "ms");
//
//		Collection<String> more = diff.getLeft();
//		Collection<String> less = comparer.diff(more, json).getRight();
//		System.out.println("more to delete: " + JSON.toJSONString(more));
//		System.out.println("less to insert: " + JSON.toJSONString(less));
//		comparer.printDiff(diff);
//
//		JSONObject json = getCityInfo(new File("d:/district-less.txt"));
//		JSONArray ary = JSONArray.parseArray(FileUtils.readFileToString(new File("d:/小贷区县名称.txt")));
//		List<String> lines = FileUtils.readLines(new File("d:/insert-city.txt"));
//
//		JSONObject lessJSONObj = new JSONObject();
//
//		for (Object element : ary) {
//			// citycode
//			String key = Objects.toString(element).substring(0, 4) + "00";
//			lessJSONObj.put(Objects.toString(element), json.get(key));
//		}
//
//		for (int i = 0; i < lines.size(); i++) {
//			String line = lines.get(i);
//			
//			for (String key : lessJSONObj.keySet()) {
//				if(!line.contains(key)){
//					continue;
//				} else {
//					String newLine = StringUtils.replace(line, "'', '" + key + "'", "'" + lessJSONObj.get(key) + "', '" + key + "'");
//					System.out.println(newLine);
//				}
//			}
//		}

//		List<String> lines = IOToolkits.readLines(new File("d:/final.txt"));
//		TreeMap<Integer, String> map = new TreeMap<Integer, String>(
//			new Comparator<Integer>() {
//				@Override
//				public int compare(Integer id1, Integer id2) {
//					// 升序排序
//					return id1 - id2;
//					// 降序排序
//					// return id2 - id1;
//				}
//			}
//		);
//
//		for (String line : lines) {
//			// UPDATE tb_sys_cityzone set CITYZONECODE='130627' WHERE CITYZONEID=134; -- 唐县
//			int start = "UPDATE tb_sys_cityzone set CITYZONECODE='".length();
//			String code = line.substring(start, start + 6);
//			map.put(Integer.parseInt(code), line);
//		}
//
//		for (String sql : map.values()) {
//			System.out.println(sql);
//		}
	}

	public static Collection<String> getAreaInfo(File file, boolean removeNation) {
		Collection<String> coll = new ArrayList<String>();
		LineIterator lineIterator = null;
		try {
			lineIterator = IOToolkits.lineIterator(file);
			while(lineIterator.hasNext()) {
				String line = lineIterator.nextLine();
				// String[] ary = StringUtils.split(line, "	");
				line = CommonToolkits.deleteWhitespace(line);
				if (removeNation) {
					for (String nation : nations) {
						if (line.contains(nation)) {
							line = CommonToolkits.remove(line, nation);
							line = CommonToolkits.replace(line, "自治", "");
						}
					}
				}
				coll.add(line);
			}
		} catch (Exception e) {
			// ignore
		} finally {
			LineIterator.closeQuietly(lineIterator);
		}

		return coll;
	}

	public static JSONObject getCityInfo(File file) {
		JSONObject coll = new JSONObject();
		LineIterator lineIterator = null;
		try {
			lineIterator = IOToolkits.lineIterator(file);
			while(lineIterator.hasNext()) {
				String line = lineIterator.nextLine();
				String[] ary = line.split(" ");
				coll.put(ary[1].trim(), CommonToolkits.recurseRemoveEnd(ary[0], "00").trim());
			}
		} catch (Exception e) {
			// ignore
		} finally {
			LineIterator.closeQuietly(lineIterator);
		}

		return coll;
	}

}
