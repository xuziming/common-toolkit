package com.simon.credit.toolkit.ext;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.ext.XmlFormat;
import com.simon.credit.toolkit.io.IOToolkits;
import com.simon.credit.toolkit.io.LineIterator;

public class FullTextReplication {

	public static void main(String[] args) {
		// File dir = new File("D:\\work\\eclipse-workspace\\dubbo-parent");
		// pomReplace(dir, "<version>2.5.4-SNAPSHOT</version>", "<version>2.5.4</version>");
		// File dir = new File("D:\\work\\sts4-workspace\\mynetty");
		// pomSimplify(dir, "<project xmlns");
		clearBinDir(new File("D:\\work\\eclipse-workspace\\spring-framework-4.3.18.RELEASE"));
	}

	public static void clearBinDir(File file) {
		if (file.isDirectory()) {
			if("bin".equals(file.getName()) || "classes".equals(file.getName())) {
				try {
					FileUtils.deleteDirectory(file);
				} catch (Exception e) {
					// ignore
				}
			} else {
				File[] subDirs = file.listFiles(new FileFilter() {
					@Override
					public boolean accept(File file) {
						return file.isDirectory();
					}
				});
				for (File subDir : subDirs) {
					clearBinDir(subDir);// 只递归目录
				}
			}
		}
	}

	public static void pomSimplify(File file, String startTag) {
		if (file.isFile()) {
			InputStream input = null;
			LineIterator iterator = null;
			List<String> lines = new ArrayList<String>(128);
			boolean startWrite = false;
			try {
				input = new FileInputStream(file);
				iterator = IOToolkits.lineIterator(input, CommonToolkits.UTF8);
				while (iterator.hasNext()) {
					String line = iterator.nextLine();
					if (startWrite) {
						lines.add(line);
						continue;
					}
					if (StringUtils.startsWith(line, startTag)) {
						startWrite = true;
						lines.add(line);
					}
				}

				iterator.close();// 关闭资源

				trimLast(lines);
				try {
					String text = StringUtils.join(lines, IOUtils.LINE_SEPARATOR);
					text = new XmlFormat().format(text);
					FileUtils.writeStringToFile(file, text, "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("text override.");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (iterator != null) {
					iterator.close();
				}
				IOToolkits.close(input);
			}
		} else if (file.isDirectory()) {
			File[] subFiles = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isDirectory() || (file.isFile() && "pom.xml".equals(file.getName()));
				}
			});
			for (File subFile : subFiles) {
				// System.out.println(subFile.getPath());
				pomSimplify(subFile, startTag);
			}
		}
	}

	private static void trimLast(List<String> lines) {
		String lastLine = lines.get(lines.size() - 1);
		if (StringUtils.isBlank(lastLine)) {
			lines.remove(lines.size() - 1);
			trimLast(lines);
		}
	}

	public static void pomReplace(File file, String searchString, String replacement) {
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
						if(StringUtils.contains(line, searchString)) {
							needOverride = true;
							line = StringUtils.replace(line, searchString, replacement);
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
			File[] subFiles = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isDirectory() || (file.isFile() && "pom.xml".equals(file.getName()));
				}
			});
			for (File subFile : subFiles) {
				// System.out.println(subFile.getPath());
				pomReplace(subFile, searchString, replacement);
			}
		}
	}

}