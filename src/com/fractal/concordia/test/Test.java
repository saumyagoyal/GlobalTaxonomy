package com.fractal.concordia.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {

	public static void main(String []args) {

		String string = "Manufacturer Mapping";
		System.out.println(string.substring(0, string.indexOf(" Mapping")));
		String excelFilePath = "C:\\Users\\angad.singh\\Downloads\\test\\Global-Indo(CPW).xlsx";

		try {
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			Sheet sheet = workbook.getSheet("Sub-Brand");

			String[] bookData = { "Test 1", "Test 2" };

			int rowCount = sheet.getLastRowNum();

			for (String aBook : bookData) {
				Row row = sheet.createRow(++rowCount);

				int columnCount = 0;

				Cell cell = row.createCell(columnCount);
				cell.setCellValue(aBook);
				XSSFCellStyle greenWriteStyle = workbook.createCellStyle();
				greenWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80)));
				greenWriteStyle.setFillPattern(CellStyle.BORDER_THIN);
				cell.setCellStyle(greenWriteStyle);

			}

			inputStream.close();

			FileOutputStream outputStream = new FileOutputStream(
					"C:\\Users\\angad.singh\\Downloads\\test\\Global-Indo(CPW).xlsx");
			workbook.write(outputStream);
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
