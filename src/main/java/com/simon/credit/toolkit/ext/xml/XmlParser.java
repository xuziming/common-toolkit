package com.simon.credit.toolkit.ext.xml;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Dom4j解析xml
 * @author xuziming 2019-10-25
 */
public class XmlParser {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File("src/test/resources/demo.xml");
		parse(xmlFile);
	}

	public static final void parse(File xmlFile) throws DocumentException {
		// 1.创建Reader对象
		SAXReader reader = new SAXReader();
		// 2.加载XML
		Document document = reader.read(xmlFile);
		// 3.获取根节点
		Element root = document.getRootElement();// <class/>

		// 遍历子节点
		@SuppressWarnings("unchecked")
		Iterator<Element> iterator = root.elementIterator();// <student/>
		while (iterator.hasNext()) {
			Element student = iterator.next();
			@SuppressWarnings("unchecked")
			List<Attribute> attributes = student.attributes();
			System.out.println("======获取节点属性======");
			for (Attribute attribute : attributes) {
				System.out.println(attribute.getName() + "=" + attribute.getValue());
			}

			System.out.println("======遍历子节点======");
			@SuppressWarnings("unchecked")
			Iterator<Element> it = student.elementIterator();
			while (it.hasNext()) {
				Element field = it.next();
				System.out.println(field.getName() + "=" + field.getStringValue());
			}
		}
	}

}