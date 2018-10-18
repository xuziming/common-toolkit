package com.simon.credit.toolkit.diff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.io.ConsoleToolkits;
import com.simon.credit.toolkit.io.IOToolkits;

/**
 * 差异比较器
 * @author XUZIMING 2018-06-11
 */
public class DiffComparer<T> {

	/**
	 * 对比两个从控制台输入的JSON串的字段差异
	 * @return left:多出的字段, right:缺少的字段
	 */
	public Pair<Collection<T>, Collection<T>> inputJsonDiff() {
		String leftJson  = ConsoleToolkits.readString("输入第一个JSON字符串: ");
		String rightJson = ConsoleToolkits.readString("输入第二个JSON字符串: ");

		return diff(leftJson, rightJson);
	}

	/**
	 * 对比JSON串的差异
	 * @param leftJson
	 * @param rightJson
	 * @return left:多出的字段, right:缺少的字段
	 */
	public Pair<Collection<T>, Collection<T>> diff(String leftJson, String rightJson) {
		Map<T, Object> left = JSON.parseObject(leftJson, new TypeReference<Map<T, Object>>() {});
		Map<T, Object> right = JSON.parseObject(rightJson, new TypeReference<Map<T, Object>>() {});

		return diff(left.keySet(), right.keySet());
	}

	/**
	 * 对比文件差异
	 * @param leftFile
	 * @param rightFile
	 * @return left:多出的字段, right:缺少的字段
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Pair<Collection<T>, Collection<T>> diff(File leftFile, File rightFile) throws IOException {
		Collection<T> leftList = FileUtils.readLines(leftFile, "UTF-8");
		Collection<T> rightList = FileUtils.readLines(rightFile, "UTF-8");

		return diff(leftList, rightList);
	}

	/**
	 * 对比两个集合的字段差异
	 * @param left
	 * @param right
	 * @return left:多出的字段, right:缺少的字段
	 */
	public Pair<Collection<T>, Collection<T>> diff(Collection<T> left, Collection<T> right) {
		Collection<T> more = leftDiff(left, right);
		Collection<T> less = rightDiff(left, right);
		return Pair.of(more, less);
	}

	private Collection<T> leftDiff(Collection<T> left, Collection<T> right) {
		Collection<T> diff = new ArrayList<T>(16);

		// 性能优化核心: 采用树结构进行校对
		Set<T> rightSet = new TreeSet<T>(right);

		for (T value : left) {
			if (!rightSet.contains(value)) {
				diff.add(value);
			}
		}

		return diff;
	}

	private Collection<T> rightDiff(Collection<T> left, Collection<T> right) {
		return leftDiff(right, left);
	}

	public void printDiff(Pair<Collection<String>, Collection<String>> diff) {
		if (diff == null) {
			return;
		}

		//System.out.println("more to delete: " + JSON.toJSONString(diff.getLeft()));
		// System.out.println("less to insert: " + JSON.toJSONString(diff.getRight()));

		JSONObject jsonObj = JSON.parseObject(getAreaInfo(new File("e:/area.txt")));
		for (String key : diff.getLeft()) {
			System.out.println(key + "	" + jsonObj.getString(key));
		}
	}

	public static void main(String[] args) throws IOException {
//		long start = System.currentTimeMillis();
//		DiffComparer<String> comparer = new DiffComparer<String>();
//		Pair<Collection<String>, Collection<String>> diff = 
//			comparer.diff(new File("d:/codes1.txt"), new File("d:/codes2.txt"));
//		long end = System.currentTimeMillis();
//		System.out.println("waste time: " + (end - start) + "ms");
//
//		comparer.printDiff(diff);

		long start = System.currentTimeMillis();
		DiffComparer<String> comparer = new DiffComparer<String>();
		String json = getAreaInfo(new File("e:/area.txt"));
		String regionalism = IOToolkits.readFile(new File("e:/regionalism.json"));

		Pair<Collection<String>, Collection<String>> diff = comparer.diff(json, regionalism);
		long end = System.currentTimeMillis();
		System.out.println("waste time: " + (end - start) + "ms");

		comparer.printDiff(diff);
	}

	private static String getAreaInfo(File file) {
		JSONObject json = new JSONObject();
		LineIterator lineIterator = null;
		try {
			lineIterator = IOToolkits.lineIterator(file);
			while(lineIterator.hasNext()) {
				String line = lineIterator.nextLine();
				String[] ary = StringUtils.split(line, "	");
				json.put(CommonToolkits.recurseRemoveEnd(ary[0], "00"), ary[1]);
			}
		} catch (Exception e) {
			// ignore
		} finally {
			LineIterator.closeQuietly(lineIterator);
		}

		return json.toJSONString();
	}

}
