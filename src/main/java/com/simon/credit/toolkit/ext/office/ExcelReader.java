package com.simon.credit.toolkit.ext.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.io.IOToolkits;

/**
 * Excel Reader
 * @author XUZIMING 2018-07-14
 */
public class ExcelReader {

	private Workbook workbook;
	private boolean includeHead = false;

	private ExcelReader() {}

	/**
	 * excel读操作
	 * @param excelFile 读入excel文件
	 */
	public static ExcelReader newInstance(File excelFile) {
		if (excelFile == null || !excelFile.exists()) {
			throw new IllegalArgumentException("illegal excel file!");
		}

		ExcelReader instance = new ExcelReader();
		InputStream input = null;
		try {
			input = new FileInputStream(excelFile);
			if (StringUtils.endsWithIgnoreCase(excelFile.getName(), ".xls")) {
				instance.workbook = new HSSFWorkbook(input);
				return instance;
			}

			if (StringUtils.endsWithIgnoreCase(excelFile.getName(), ".xlsx")) {
				instance.workbook = new XSSFWorkbook(input);
				return instance;
			}

			throw new IllegalStateException("unsupport file type!");
		} catch (Exception e) {
			throw new IllegalStateException("read excel file error!");
		} finally {
			IOToolkits.close(input);
		}
	}

	/**
	 * 读取excel所有sheet的数据
	 * @return
	 */
	public List<Object[]> getData() {
		return getData(null);
	}

	/**
	 * 根据sheet name 读取对应分页的数据
	 * @param sheetName
	 * @return
	 */
	public List<Object[]> getData(String sheetName) {
		List<Object[]> result = new ArrayList<Object[]>();
		if (sheetName == null) {
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				List<Object[]> subResult = this.reader(sheet);
				result.addAll(subResult);
			}
		} else {
			Sheet sheet = workbook.getSheet(sheetName);
			List<Object[]> subResult = this.reader(sheet);
			return subResult;
		}
		return result;
	}

	protected List<Object[]> reader(Sheet sheet) {
		if (sheet == null) {
			return null;
		}
		List<Object[]> result = new ArrayList<Object[]>();
		int cellNums = 0;
		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			if (row.getRowNum() == 0) {
				cellNums = row.getLastCellNum();
				if (!includeHead) {
					continue;
				}
			}
			Iterator<Cell> cells = row.iterator();

			Object[] datas = new Object[cellNums];
			while (cells.hasNext()) {
				Cell cell = cells.next();
				try {
					Object obj = formatCell(cell);
					datas[cell.getColumnIndex()] = obj;
				} catch (Exception e) {
					continue;
				}
			}

			if (CommonToolkits.isAllEmpty(datas)) {
				continue;
			}

			result.add(datas);
		}
		return result;
	}

	protected Object formatCell(Cell cell) {
		if (cell == null) {
			return null;
		}
		Object obj = null;
		int cellType = cell.getCellType();
		switch (cellType) {
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					obj = cell.getDateCellValue();
				} else {
					obj = cell.getNumericCellValue();
				}
	
				break;
			case Cell.CELL_TYPE_STRING:
				obj = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				obj = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				obj = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				obj = cell.getStringCellValue();
				break;
			default:
				obj = cell.getStringCellValue();
				break;
		}
		return obj;
	}

	public boolean isIncludeHead() {
		return includeHead;
	}

	public void setIncludeHead(boolean includeHead) {
		this.includeHead = includeHead;
	}

}
