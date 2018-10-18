package com.simon.credit.toolkit.office;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.simon.credit.toolkit.common.CommonToolkits;
import com.simon.credit.toolkit.io.IOToolkits;

/**
 * Excel Writer
 * @author XUZIMING 2018-07-14
 */
public class ExcelWriter {

	private File outputFile;
	private Workbook workbook;
	private List<Sheet> sheets = new ArrayList<Sheet>();

	private ExcelWriter() {}

	/**
	 * excel写操作
	 * @param excelFile 写出excel文件
	 */
	public static ExcelWriter newInstance(File excelFile) {
		ExcelWriter instance = new ExcelWriter();
		instance.outputFile = instance.renameWhenExists(excelFile);

		if (StringUtils.endsWithIgnoreCase(excelFile.getName(), ".xls")) {
			instance.workbook = new HSSFWorkbook();
			return instance;
		}

		if (StringUtils.endsWithIgnoreCase(excelFile.getName(), ".xlsx")) {
			instance.workbook = new XSSFWorkbook();
			return instance;
		}

		throw CommonToolkits.illegalArgumentException("input file isn't an excel file");
	}

	private File renameWhenExists(File file) {
		return renameWhenExists(file, 0);
	}

	private File renameWhenExists(File file, Integer index) {
		if (file == null) {
			throw CommonToolkits.illegalArgumentException("file is null");
		}

		if (!file.exists()) {
			return file;
		}

		String baseName = FilenameUtils.getBaseName(file.getName());
		if (StringUtils.contains(baseName, "(") && StringUtils.endsWith(baseName, ")")) {
			baseName = baseName.substring(0, baseName.lastIndexOf("("));
		}
		String extension = FilenameUtils.getExtension(file.getName());

		String filePath = file.getParent() + File.separator 
			+ baseName + "(" + (index + 1) + ")." + extension;

		return renameWhenExists(new File(filePath), index + 1);
	}

	/**
	 * 对excel文件写入单行记录
	 * @param data 要写入的单行记录
	 */
	public <T> void addData(T[] data) {
		this.addData(null, data);
	}

	/**
	 * 根据excel的sheet name 写入单行记录
	 * @param sheetName 对应excel的sheet name
	 * @param data 要写入的单行记录
	 */
	public <T> void addData(String sheetName, T[] data) {
		this.addData(null, new Object[][] { data });
	}

	/**
	 * 对excel文件写入多行记录
	 * @param datas
	 */
	public <T> void addData(T[][] datas) {
		this.addData(null, datas);
	}

	/**
	 * 根据excel的sheet name写入多行记录
	 * @param sheetName 对应excel的sheet name
	 * @param datas 要写入
	 */
	public <T> void addData(String sheetName, T[][] datas) {
		if (datas == null || datas.length == 0) {
			return;
		}
		Sheet sheet = null;
		int sheetNums = workbook.getNumberOfSheets();

		if (sheetName == null) {
			if (sheetNums > 0) {
				sheet = workbook.getSheetAt(0);
			} else {
				sheet = workbook.createSheet();
				sheets.add(sheet);
			}
		} else {
			sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetName);
				sheets.add(sheet);
			}
		}
		Row row = null;
		Cell cell = null;
		int rowIndex = sheet.getLastRowNum();

		for (int i = 0; i < datas.length; i++) {
			// iterator create a new row or get a row
			Object[] data = datas[i];
			if (data != null && data.length > 0) {
				// append excel
				if (rowIndex > 0) {
					rowIndex++;
					row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
					}
				} else {
					rowIndex = 0;
					row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
					} else {
						rowIndex++;
						row = sheet.getRow(rowIndex);
						if (row == null) {
							row = sheet.createRow(rowIndex);
						}
					}
				}
				this.writer(data, row, cell);
			}
		}

		OutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOToolkits.close(out);
		}
	}

	/**
	 * 对excel文件写入多行数据
	 * @param datas
	 */
	public <T> void addData(List<T[]> datas) {
		this.addData(null, datas);
	}

	/**
	 * 根据excel的sheet name写入多行记录
	 * @param sheetName
	 * @param datas
	 */
	public <T> void addData(String sheetName, List<T[]> datas) {
		if (datas == null || datas.size() == 0) {
			return;
		}
		Sheet sheet = null;
		int sheetNums = workbook.getNumberOfSheets();
		if (sheetName == null) {
			if (sheetNums > 0) {
				sheet = workbook.getSheetAt(0);
			} else {
				sheet = workbook.createSheet();
				sheets.add(sheet);
			}
		} else {
			sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetName);
				sheets.add(sheet);
			}
		}
		Row row = null;
		Cell cell = null;
		int rowIndex = sheet.getLastRowNum();

		for (int i = 0; i < datas.size(); i++) {
			// iterator create a new row or get a row
			Object[] data = datas.get(i);
			if (data != null && data.length > 0) {
				// append excel
				if (rowIndex > 0) {
					rowIndex++;
					row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
					}
				} else {
					rowIndex = 0;
					row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
					} else {
						rowIndex++;
						row = sheet.getRow(rowIndex);
						if (row == null) {
							row = sheet.createRow(rowIndex);
						}
					}
				}

				this.writer(data, row, cell);
			}
		}

		OutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOToolkits.close(out);
		}
	}

	/**
	 * 将要写入excel 的数据转换成Row & Cell
	 * @param data 需要插入的数据
	 * @param row 对应excel 中的 Row (行)
	 * @param cell 对应excel 中的Cell (单元格)
	 */
	protected void writer(Object[] data, Row row, Cell cell) {
		for (int j = 0; j < data.length; j++) {
			cell = row.getCell(j);
			if (cell == null) {
				cell = row.createCell(j);
			}

			Object cellData = data[j];
			if (cellData == null) {
				continue;
			}

			if (cellData instanceof String) {
				cell.setCellValue((String) cellData);
				cell.setCellType(Cell.CELL_TYPE_STRING);
			} else if (cellData instanceof Integer) {
				cell.setCellValue((Integer) cellData);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cellData instanceof Float) {
				cell.setCellValue((Float) cellData);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cellData instanceof Double) {
				cell.setCellValue((Double) cellData);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cellData instanceof Date) {
				cell.setCellValue((Date) cellData);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				// cell.setCellStyle(dateStyle);
			} else if (cellData instanceof Character) {
				cell.setCellValue(((Character) cellData).charValue());
				cell.setCellType(Cell.CELL_TYPE_STRING);
			} else if (cellData instanceof Byte) {
				cell.setCellValue(((Byte) cellData).byteValue());
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cellData instanceof Short) {
				cell.setCellValue(((Short) cellData).shortValue());
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cellData instanceof Long) {
				cell.setCellValue(((Long) cellData).longValue());
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cellData instanceof Float) {
				cell.setCellValue(((Float) cellData).floatValue());
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				// cell.setCellStyle(decimalStyle);
			} else if (cellData instanceof Calendar) {
				cell.setCellValue((Calendar) cellData);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				// cell.setCellStyle(dateStyle);
			} else if (cellData instanceof Boolean) {
				cell.setCellValue(((Boolean) cellData).booleanValue());
				cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
			} else {
				cell.setCellValue(cellData.toString());
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
		}
	}

}
