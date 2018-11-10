package com.simon.credit.toolkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.simon.credit.toolkit.office.ExcelReader;
import com.simon.credit.toolkit.office.ExcelWriter;

public class ExcelTest {

	public static void main(String[] args) {
//		writeExcel();
//		readExcel();

//		readRegionalism(new File("d:/省编码.xlsx"));
//		readRegionalism(new File("d:/市编码.xlsx"));
		readRegionalism(new File("d:/区编码.xlsx"));
	}

	public static void writeExcel() {
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

	public static void readExcel() {
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
				System.out.print(obj.toString() + " \t\t");
			}
			System.out.println();
		}
	}

	public static void readRegionalism(File regionalismFile) {
		ExcelReader excelReader = ExcelReader.newInstance(regionalismFile);
		excelReader.setIncludeHead(true);
		List<Object[]> rows = excelReader.getData();

		System.out.println("excel has " + rows.size() + " columns");

		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			Object[] rowCells = rows.get(rowIndex);

			for (int columnIndex = 0; columnIndex < rowCells.length; columnIndex++) {
				Object cell = rowCells[columnIndex];
				if (cell == null) {
					cell = "";
				}
				System.out.print(cell.toString() + " \t");
			}

			System.out.println();
		}
	}

}
