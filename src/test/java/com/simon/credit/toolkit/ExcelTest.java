package com.simon.credit.toolkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.simon.credit.toolkit.office.ExcelReader;
import com.simon.credit.toolkit.office.ExcelWriter;

public class ExcelTest {

	public static void main(String[] args) {
		writeExcel();
		readExcel();
	}

	private static void writeExcel() {
		ExcelWriter excelWriter = ExcelWriter.newInstance(new File("d:/test.xls"));

		Object[] headDatas = new Object[] { 
			"合同编号", "客户姓名", "证件号码", "所属网店", 
			"放款日期", "贷款金额", "履约保证金", "通过日期", "状态" };

		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < 10000; i++) {
			list.add(headDatas);
		}
		// writer excel header...
		excelWriter.addData(headDatas);
		// writer excel content...
		excelWriter.addData(list);
		System.out.println("success.");
	}

	private static void readExcel() {
		ExcelReader excelReader = ExcelReader.newInstance(new File("d:/test.xls"));
		excelReader.setIncludeHead(false);
		List<Object[]> cells = excelReader.getData();

		System.out.println("excel contain " + cells.size() + " columns");
		for (int i = 0; i < cells.size(); i++) {
			Object[] cell = cells.get(i);
			for (int j = 0; j < cell.length; j++) {
				Object obj = cell[j];
				if (obj == null)
					obj = "";
				System.out.print(obj.toString() + " \t");
			}
			System.out.println();
		}
	}

}
