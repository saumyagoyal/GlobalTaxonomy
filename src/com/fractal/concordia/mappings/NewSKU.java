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
import java.util.regex.Matcher;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author saumya.goyal
 */
public class NewSKU {

	public static void matchMapping(String main, String newSKU, String globalpath, String localpath, String createNew) throws EncoderException, FileNotFoundException, IOException {

		FileInputStream nReader = new FileInputStream(new File(newSKU));
		FileInputStream mReader = new FileInputStream(new File(main));

		XSSFWorkbook newSkuworkbook = new XSSFWorkbook(nReader);
		XSSFWorkbook mainworkbook = new XSSFWorkbook(mReader);

		XSSFSheet nsheet = newSkuworkbook.getSheetAt(0);
		XSSFSheet msheet = mainworkbook.getSheetAt(0);

		int newSKUno = nsheet.getPhysicalNumberOfRows();
		int oldMappedno = msheet.getPhysicalNumberOfRows();
		Boolean match = false;

		/* Iterator<Row> mainIterator = msheet.iterator();
        while (mainIterator.hasNext()) {
            mainIterator.next();
        }*/
		String newline;
		String mainline;
		String[] newlineArray;
		String[] mainlineArray;
		String[] sCon = new String[]{"/", "&", "-", "+"};
		String[] rCon = new String[]{" ", " ", " ", " "};

		XSSFWorkbook newworkBook = new XSSFWorkbook();
		XSSFSheet unmapped = newworkBook.createSheet("Unmapped");
		int urow = 0;
		int ucell = 0;

		Row r = nsheet.getRow(0);
		int newmax = r.getLastCellNum();
		Row r1 = msheet.getRow(0);
		int oldmax = r1.getLastCellNum();

		System.out.println(newmax + "" + oldmax);

		Iterator<Row> locIterator = nsheet.iterator();
		while (locIterator.hasNext()) {
			Row lrow = locIterator.next();
			newline = lrow.getCell(1).getStringCellValue();
			newline = StringUtils.replaceEach(newline.toLowerCase(), Constants.searchmetric, Constants.replacemetric);
			//locline = StringUtils.replaceEach(locline, Constants.searchList, Constants.replacementList);
			for (int reg = 0; reg < Constants.pattern.length; reg++) {
				Matcher match0 = Constants.pattern[reg].matcher(newline);
				newline = match0.replaceAll(Constants.regularex[reg]);
			}
			//------>>    System.out.println(locline);

			newline = StringUtils.replaceEach(newline, sCon, rCon);
			newlineArray = newline.replace("  ", " ").split(" ");

			Iterator<Row> globIterator = msheet.iterator();
			while (globIterator.hasNext()) {
				Row grow = globIterator.next();
				mainline = grow.getCell(1).getStringCellValue();
				mainline = StringUtils.replaceEach(mainline.toLowerCase(), Constants.searchmetric, Constants.replacemetric);
				//locline = StringUtils.replaceEach(locline, Constants.searchList, Constants.replacementList);
				for (int reg = 0; reg < Constants.pattern.length; reg++) {
					Matcher match0 = Constants.pattern[reg].matcher(mainline);
					mainline = match0.replaceAll(Constants.regularex[reg]);
				}
				//------>>    System.out.println(locline);

				mainline = StringUtils.replaceEach(mainline, sCon, rCon);
				mainlineArray = mainline.replace("  ", " ").split(" ");

				if ((mainline.toLowerCase()).equals(newline.toLowerCase())) {
					System.out.println(mainline + newline);
					//while (grow.getCell(oldmax).getStringCellValue() != null) {
					for (int l = newmax; l < oldmax; l++) {
						System.out.println(l);
						String val = grow.getCell(l).getStringCellValue();
						XSSFCell cellA1 = (XSSFCell) lrow.createCell(l, Cell.CELL_TYPE_STRING);
						cellA1.setCellValue(val);
						match = true;
					}
				}
			}
			if (match == false) {
				ucell = 0;
				XSSFRow row = unmapped.createRow(urow++);
				Iterator<Cell> cellIterator = lrow.cellIterator();

				for (int y = 0; y < (newmax - 1); y++) {
					String val = lrow.getCell(ucell).getStringCellValue();
					XSSFCell cellA1 = row.createCell(ucell, Cell.CELL_TYPE_STRING);
					cellA1.setCellValue(val);
					/* }
//delete a row

int rowIndex=lrow.getRowNum(); //Assume already known and this is the row you want to get rid of
int lastIndex = nsheet.getLastRowNum();
nsheet.shiftRows(rowIndex + 1, lastIndex, -1);
					 */

					XSSFCell cellA2 = (XSSFCell) lrow.getCell(ucell);
					cellA2.setCellValue("  ");
					ucell++;
				}

			}
			match = false;
		}
		FileOutputStream output = new FileOutputStream(newSKU);
		newSkuworkbook.write(output);
		output.close();

		FileOutputStream output1 = new FileOutputStream(createNew);
		newworkBook.write(output1);
		output.close();
		output1.close();
		nReader.close();
		mReader.close();
	}

}
