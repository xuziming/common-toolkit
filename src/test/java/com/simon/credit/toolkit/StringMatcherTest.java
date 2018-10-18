package com.simon.credit.toolkit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.simon.credit.toolkit.match.KMPStringMatcher;
import com.simon.credit.toolkit.match.OptimizedKMPStringMatcher;
import com.simon.credit.toolkit.match.StringMatcher;
import com.simon.credit.toolkit.match.ViolentStringMatcher;

public class StringMatcherTest {

	public static void main(String[] args) throws IOException {
		StringMatcher matcher = new ViolentStringMatcher();
		System.out.println(matcher.indexOf("helloworld", "ow"));

		matcher = new KMPStringMatcher();
		System.out.println(matcher.indexOf("helloworld", "ow"));

		matcher = new OptimizedKMPStringMatcher();
		System.out.println(matcher.indexOf("fasdfa_f002_hellowtorld", "owt"));

		String content = FileUtils.readFileToString(new File("d:/76873.txt"));
		System.out.println(content.indexOf("d3cca8af-21b8-4963-878b-f8ba115d21e4"));
		System.out.println(matcher.indexOf(content, "d3cca8af-21b8-4963-878b-f8ba115d21e4"));
	}

}
