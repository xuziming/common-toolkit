package com.simon.credit.toolkit.ext;

import java.io.IOException;

import com.simon.credit.toolkit.match.KMPMatcher;
import com.simon.credit.toolkit.match.ViolentMatcher;

public class StringMatcherTest {

	public static void main(String[] args) throws IOException {
		System.out.println(ViolentMatcher.indexOf("helloworld", "ow"));

		System.out.println(KMPMatcher.indexOf("helloworld", "ow"));

		System.out.println(KMPMatcher.indexOf("fasdfa_f002_hellowtorld", "owt"));

//		String content = FileUtils.readFileToString(new File("d:/76873.txt"));
//		System.out.println(content.indexOf("d3cca8af-21b8-4963-878b-f8ba115d21e4"));
//		System.out.println(KMPMatcher.indexOf(content, "d3cca8af-21b8-4963-878b-f8ba115d21e4"));
	}

}
