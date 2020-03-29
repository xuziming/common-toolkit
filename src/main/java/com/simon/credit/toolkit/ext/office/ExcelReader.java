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
	 * 根据sheet name 读取对应sheet的数据
	 * @param sheetName
	 * @return
	 */
	public List<Object[]> getData(String sheetName) {
		List<Object[]> allRows = new ArrayList<Object[]>();
		if (sheetName == null) {
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				List<Object[]> oneSheetRows = this.read(sheet);
				allRows.addAll(oneSheetRows);
			}
		} else {
			Sheet sheet = workbook.getSheet(sheetName);
			List<Object[]> subResult = this.read(sheet);
			return subResult;
		}
		return allRows;
	}

	/**
	 * 读取指定sheet的所有行
	 * @param sheet
	 * @return
	 */
	protected List<Object[]> read(Sheet sheet) {
		if (sheet == null) return null;

		List<Object[]> allRowValues = new ArrayList<Object[]>(10);
		int cellNums = 0;
		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			if (row == null) continue;

			if (row.getRowNum() == 0) {
				cellNums = row.getLastCellNum();
				if (!includeHead) {
					continue;
				}
			}

			Object[] oneRowValues = new Object[cellNums];
			Iterator<Cell> columns = row.iterator();
			while (columns.hasNext()) {
				Cell cell = columns.next();
				try {
					Object columnValue = getValue(cell);
					oneRowValues[cell.getColumnIndex()] = columnValue;
				} catch (Exception e) {
					continue;
				}
			}

			if (CommonToolkits.isAllEmpty(oneRowValues)) continue;

			allRowValues.add(oneRowValues);
		}
		return allRowValues;
	}

	protected Object getValue(Cell cell) {
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
