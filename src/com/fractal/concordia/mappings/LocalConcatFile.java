/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractal.concordia.mappings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.utils.EncoderAndDecoderUtil;

/**
 *
 * @author saumya.goyal
 */
public class LocalConcatFile {

	private static final Logger log = Logger.getLogger(EncoderAndDecoderUtil.class.getName());

	public static void filterFile(String main, int[] filtercolumns, String outputfile) {

		// Create a output file with all the columns selected in the pick list
		FileInputStream mReader = null;
		SXSSFSheet unmapped = null;
		SXSSFWorkbook newworkBook = new SXSSFWorkbook();
		try {
			mReader = new FileInputStream(new File(main));
			XSSFWorkbook mainworkbook = new XSSFWorkbook(mReader);
			XSSFSheet msheet = mainworkbook.getSheetAt(0);
			File f = new File(outputfile);

			// Ideally same file with same time stamp will never exist before, if still it does it will delete the file
			if (f.exists() && !f.isDirectory()) {
				unmapped = (SXSSFSheet) newworkBook.getSheet("Mapping_output");

				if (f.delete()) {
					System.out.println("File exists, file deleted and running");
					log.error("File exists, file deleted and running");
				} else {
					System.out.println("Delete failed");
					log.error("Delete failed");
					// System.exit(0);
				}
				unmapped = (SXSSFSheet) newworkBook.createSheet("Mapping_output");
				// return;
			} else {
				unmapped = (SXSSFSheet) newworkBook.createSheet("Mapping_output");
			}
			int m = 0;

			// Reading from the local input file
			Iterator<Row> mainIterator = msheet.iterator();
			while (mainIterator.hasNext()) {
				Row allrow = mainIterator.next();
				Row rownew = unmapped.createRow(m++);
				int n = 0;
				for (int c = 0; c < filtercolumns.length; c++) {
					int val = filtercolumns[c] - 1;
					Cell cell = allrow.getCell(val);
					String value = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						value = Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = "";
						break;
					}
					// System.out.println(value);
					Cell cellA1 = rownew.createCell(n++, Cell.CELL_TYPE_STRING);
					cellA1.setCellValue(value);
				}
			}
		} catch (IOException e) {
			log.error("error in reading local input file", e);
		} finally {
			if (mReader != null) {
				try {
					mReader.close();
				} catch (IOException e) {
					log.error("error in closing local input file", e);
				}
			}
		}

		// Writing in a new output file where First sheet will have all the column and all the global hierarchies
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(outputfile);
			newworkBook.write(fileOut);
		} catch (FileNotFoundException e) {
			log.error("File not found", e);
		} catch (IOException e) {
			log.error("Error in closing local output file", e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					log.error("Error in closing output file", e);
				}
			}
		}
	}

	public static void readfile(String main, int[] columns, String level, String outputfile) {

		int i = 0;
		FileInputStream reader = null;
		FileInputStream tempreader = null;
		FileOutputStream fileOut = null;
		String concatline = "";

		try {
			reader = new FileInputStream(new File(main));
			tempreader = new FileInputStream(new File(outputfile));
			XSSFWorkbook mainworkbook = new XSSFWorkbook(reader);
			XSSFSheet lsheet = mainworkbook.getSheetAt(0);

			XSSFWorkbook tempworkbook = new XSSFWorkbook(tempreader);
			XSSFSheet tempsheet = tempworkbook.createSheet(level);
			int m = 0;
			Iterator<Row> locIterator = lsheet.iterator();
			String line = "";

			for (int h = 0; h <= lsheet.getLastRowNum(); h++) {
				Row row = locIterator.next();
				concatline = "";
				XSSFRow rownew = tempsheet.createRow(m++);
				for (i = 0; i < columns.length; i++) {
					int val = columns[i] - 1;
					Cell cell = row.getCell(val);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						line = Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						line = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						line = "";
						break;
					}
					concatline = line + "  " + concatline;
				}
				// Read the local file an create a tab with name as level that has all the concatenated value.
				XSSFCell cellA1 = rownew.createCell(0, Cell.CELL_TYPE_STRING);
				cellA1.setCellValue("[ExactOrderMatch] " + "[PattternMatch] " + concatline);

			}
			fileOut = new FileOutputStream(outputfile);
			tempworkbook.write(fileOut);

		} catch (FileNotFoundException e) {
			log.error("Input file not found", e);
		} catch (IOException e) {
			log.error("Error in reading file", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("Error in closing local input file", e);
				}
			}
			if (tempreader != null) {
				try {
					tempreader.close();
				} catch (IOException e) {
					log.error("Error in closing output file", e);
				}
			}
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					log.error("Error in closing output file", e);
				}
			}
		}
	}

}
