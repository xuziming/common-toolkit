package com.simon.credit.toolkit;

import java.io.IOException;

import com.simon.credit.toolkit.match.KMPMatcher;
import com.simon.credit.toolkit.match.ViolentMatcher;

public class StringMatcherTest {

	public static void main(String[] args) throws IOException {
		System.out.println(ViolentMatcher.indexOf("helloworld", "ow"));

		System.out.println(KMPMatcher.indexOf("helloworld", "ow"));

		System.out.println(KMPMatcher.indexOf("fasdfa_f002_hellowtorld", "owt"));
	}

}
