package com.simon.credit.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.io.IOToolkits;
import com.simon.credit.toolkit.io.LineIterator;

public class FileChecker {

	public static void main(String[] args) {
		File dir = new File("D:\\work\\eclipse-workspace\\spring-framework-4.3.18.RELEASE");
		recursiveRename(dir);
	}

	@SuppressWarnings("unused")
	private static void recursiveCheck(File file) {
		if (file.isFile()) {
			if (file.length() == 0) {
				System.out.println(file.getAbsolutePath());
				file.delete();
			}
			if (file.getName().contains("(1)")) {
				System.out.println(file.getAbsolutePath());
				file.delete();
			}
		} else if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for (File subFile : subFiles) {
				recursiveCheck(subFile);
			}
		}
	}

	private static void recursiveRename(File file) {
		if (file.isFile()) {
			InputStream input = null;
			LineIterator iterator = null;
			Collection<String> lines = new ArrayList<String>(128);
			boolean needOverride = false;
			try {
				input = new FileInputStream(file);
				iterator = IOToolkits.lineIterator(input, CommonToolkits.UTF8);
				while (iterator.hasNext()) {
					String line = iterator.nextLine();
					if (line != null) {
						if(StringUtils.contains(line, "@SuppressWarnings(\"serial\")")) {
							needOverride = true;
							line = StringUtils.remove(line, "@SuppressWarnings(\"serial\")");
						}
						lines.add(line);
					}
				}
				// 关闭资源
				iterator.close();

				if (needOverride) {
					IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR, new FileOutputStream(file), "UTF-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (iterator != null) {
					iterator.close();
				}
				IOToolkits.close(input);
			}
		} else if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for (File subFile : subFiles) {
				recursiveRename(subFile);
			}
		}
	}

}