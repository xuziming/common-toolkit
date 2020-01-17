package com.simon.credit.toolkit.ext;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.ext.diff.TimeDiff;
import com.simon.credit.toolkit.ext.office.ExcelReader;
import com.simon.credit.toolkit.ext.office.ExcelWriter;

public class ExcelTest {

	public static void main(String[] args) {
//		writeExcel();
//		readExcel();

//		readRegionalism(new File("d:/省编码.xlsx"));
//		readRegionalism(new File("d:/市编码.xlsx"));
//		readRegionalism(new File("d:/区编码.xlsx"));

		ExcelReader excelReader = ExcelReader.newInstance(new File("C:/Users/Administrator/Desktop/信贷财务近一个月发标情况.xlsx"));
		excelReader.setIncludeHead(false);
		List<Object[]> cells = excelReader.getData();

		System.out.println("excel contain " + cells.size() + " columns");
		
		long sumMinutes = 0;
		long paySuccessCount = 0;

		for (int i = 0; i < cells.size(); i++) {
			Object[] cell = cells.get(i);
//			for (int j = 0; j < cell.length; j++) {
//				Object obj = cell[j];
//				if (obj == null) obj = "";
//				if(obj instanceof Date){
//					System.out.print(CommonToolkits.formatDate((Date)obj) + " \t\t");
//				} else {
//					System.out.print(obj.toString() + " \t\t");
//				}
//			}
//			System.out.println();

			Date addTime = (Date) cell[0];// 接收订单的时间
			Date borrowFullTime = (Date) cell[1];// 满标时间
//			Date paymentTime = (Date) cell[2];// 放款时间
			int status = (int) CommonToolkits.parseDouble(cell[3].toString());// 状态, 6:放款成功
//			int borrowStatus = (int) CommonToolkits.parseDouble(cell[4].toString());// 0 招标中， =3满标 =5流标
			String orgDept = cell[5].toString();// 来源渠道

			if (status == 6 && "iqiyi".equals(orgDept)) {
//			if (status == 6 && "XD_51GJJ".equals(orgDept)) {
				long min = TimeDiff.between(addTime, borrowFullTime, TimeUnit.MINUTES);
				sumMinutes += min;
				paySuccessCount++;
				System.out.println(min);
			}
		}
		
		System.out.println("\n\n");
		System.out.println(paySuccessCount);
		System.out.println(sumMinutes/paySuccessCount);
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
