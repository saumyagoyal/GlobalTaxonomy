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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.utils.EncoderAndDecoderUtil;
import com.fractal.concordia.vo.ProductHierarchyVO;

/**
 *
 * @author saumya.goyal
 */

/**
 * This class provides an interface to various encoders for producing phonetic or reduced forms from words.
 */
public class Mapping {

	private static final Logger log = Logger.getLogger(EncoderAndDecoderUtil.class.getName());

	public static ProductHierarchyVO levelMapping(String level, int[] columns, String main, String globalpath,
			String outputfile, ArrayList<String> global, String[] sCon, String[] rCon) {

		String locline = null;
		String gloline = null;
		String globallist;
		int i = 0;
		int j = 0;

		int len = 0;
		int glen = 0;
		int var = 0;
		String[] localArray = null;
		String[] varientArr = null;
		int gmax = 0;

		int flag = 0;
		double max;
		int m1 = 0;
		int n1 = 0;
		String lineIWant;
		int[] value;
		int[] index;
		int[] flagALLGreen = null;
		int[] flagALLYellow = null;

		int glinenumber = 0;
		int llinenumber = 0;

		int countGreen = 0;
		int countYellow = 0;
		int countNomatch = 0;

		HashMap<Integer, Integer> Perfect = new HashMap<>();
		HashMap<Integer, Integer> All = new HashMap<>();
		Iterator<String> globArrayIterator = null;

		// Creating a tab with all columns concatenated value of local. (Will delete this tab later)
		LocalConcatFile.readfile(main, columns, level, outputfile);

		FileInputStream gReader = null;
		FileInputStream outReader = null;
		FileInputStream mReader = null;
		ProductHierarchyVO categoryVO = null;

		try {
			gReader = new FileInputStream(new File(globalpath));
			outReader = new FileInputStream(new File(outputfile));
			mReader = new FileInputStream(new File(main));

			XSSFWorkbook globalworkbook = new XSSFWorkbook(gReader);
			XSSFWorkbook outworkbook = new XSSFWorkbook(outReader);
			XSSFWorkbook workbook = new XSSFWorkbook(mReader);

			XSSFSheet gsheet = globalworkbook.getSheet(level);
			XSSFSheet lsheet = outworkbook.getSheet(level);

			glinenumber = gsheet.getPhysicalNumberOfRows();
			llinenumber = lsheet.getPhysicalNumberOfRows();

			double[][] diff = new double[llinenumber][glinenumber];

			Iterator<Row> locIterator = lsheet.iterator();
			while (locIterator.hasNext()) {
				Row lrow = locIterator.next();
				Cell cell = lrow.getCell(0);

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					locline = Double.toString(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					locline = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					locline = "";
					break;
				default:
					locline = "";
					break;
				}
				// Replace all metrics like g,kg,l,ml with different spellings to g and kg
				locline = StringUtils.replaceEach(locline.toLowerCase(), Constants.searchmetric,
						Constants.replacemetric);
				// Converting g and kg to units for better pattern match
				for (int reg = 0; reg < Constants.pattern.length; reg++) {
					Matcher match0 = Constants.pattern[reg].matcher(locline);
					locline = match0.replaceAll(Constants.regularex[reg]);
				}
				// Handling special characters
				locline = StringUtils.replaceEach(locline, sCon, rCon);
				locline = locline.replace("  ", " ").replace("  ", " ").replace("  ", " ");

				// Split the local concatenated phrase by space
				localArray = locline.trim().split(" ");

				// ------>>System.out.println(locline);

				int PerfectMatch = -1;
				int AllMatch = -1;

				int no_grow = 0;
				double line_max;
				globArrayIterator = global.iterator();

				// Using global array instead of reading the global file
				while (globArrayIterator.hasNext()) {
					globallist = globArrayIterator.next();
					// Splitting global array by space one by one
					String[] globalListArray = globallist.split(",");

					int no_cell = globalListArray.length;
					value = new int[globalListArray.length];
					index = new int[1000];
					int loc_pos = 0;
					int green_cell = -1;
					int yellow_cell = -1;

					glen = 0;
					for (int globalList = 0; globalList < globalListArray.length; globalList++) {
						gloline = globalListArray[globalList];
						// Replace all metrics like g,kg,l,ml with different spellings to g and kg
						gloline = StringUtils.replaceEach(gloline.toLowerCase(), Constants.searchmetric,
								Constants.replacemetric);
						// Converting g and kg to units for better pattern match
						for (int reg = 0; reg < Constants.pattern.length; reg++) {
							Matcher match0 = Constants.pattern[reg].matcher(gloline);
							gloline = match0.replaceAll(Constants.regularex[reg]);
						}
						// Handling special characters
						gloline = StringUtils.replaceEach(gloline, sCon, rCon);
						gloline = gloline.replace("  ", " ").replace("  ", " ").replace("  ", " ");
						// ----->> System.out.println(gloline);

						value[glen] = 0;

						// Count to check pattern matches for every local line vs every global entry
						diff[i][j] = 0;

						varientArr = gloline.trim().split(" ");

						// To check if all the words are present in green and tan color
						flagALLGreen = new int[varientArr.length];
						flagALLYellow = new int[varientArr.length];
						// To check the order of the string in green color
						String ordercheck = "";

						for (len = 0; len < localArray.length; len++) {

							for (var = 0; var < varientArr.length; var++) {

								localArray[len] = localArray[len].replace("'", "");
								varientArr[var] = varientArr[var].replace("'", "");

								// It checks first if the value is equal and then checks individual color
								if ((localArray[len].toLowerCase().trim())
										.equals(varientArr[var].toLowerCase().trim())) {

									// EOM_COLOR-->Exact order match
									// PATTERN_COLOR-->Pattern match
									if (gloline.contains(("[ExactOrderMatch]").toLowerCase())) {

										// System.out.println("Perfect Order ");
										// To check the order, concatenate all local values
										ordercheck = ordercheck + " " + localArray[len];
										value[glen]++;
										index[loc_pos++] = var;
										green_cell = globalList;
										flagALLGreen[var] = 1;
									} else if (gloline.contains(("[PattternMatch]").toLowerCase())) {

										// System.out.println("All-match");
										value[glen]++;
										yellow_cell = globalList;
										flagALLYellow[var] = 1;
									} else {

										flagALLGreen = null;
										flagALLYellow = null;
										value[glen]++;
									}
									// Condition for 2 characters or more
									if ((flag == 1) && (localArray[len].trim().length() <= 2)) {
										value[glen]--;

									}
								}

							}
						}
						// If the exact string is present as sub string in global, it is EOM
						if (green_cell == globalList) {
							if (!ordercheck.contains(gloline.replace("[exactordermatch]", ""))) {
								// Otherwise value is -1
								value[glen] = -1;
							}
						}
						// Check if all are present, if the flag is 1 for all words in global value
						if (green_cell == globalList) {
							for (int f = 0; f < flagALLGreen.length; f++) {
								if (flagALLGreen[f] == 0) {
									// Otherwise value is -1
									value[glen] = -1;
									// System.out.println("Perfect order failed");
								}
							}
						}
						// Check if all are present, if the flag is 1 for all words in global value
						if (yellow_cell == globalList) {
							for (int f = 0; f < flagALLYellow.length; f++) {
								if (flagALLYellow[f] == 0) {
									// Otherwise value is -1
									value[glen] = -1;
									// System.out.println("All match failed");
								}
							}
						}
						glen++;
					}
					gmax = 0;

					// To pick the first in color with same number of matches, used a hash map
					for (n1 = 0; n1 < no_cell; n1++) {
						if ((value[n1] > gmax) && (n1 != green_cell) && (n1 != yellow_cell)) {
							gmax = value[n1];

						} else if ((value[n1] >= gmax) && (n1 == yellow_cell) && (PerfectMatch == -1)) {
							gmax = value[n1];
							AllMatch = no_grow;
							All.put(no_grow, gmax);
						} else if ((value[n1] >= gmax) && (n1 == green_cell)) {
							gmax = value[n1];
							// if (gloline.contains(("[ExactOrderMatch]").toLowerCase())) {
							PerfectMatch = no_grow;
							Perfect.put(no_grow, gmax);
							// }
						}
					}
					diff[i][j] = gmax;

					// --------->> System.out.println(diff[i][j] + " ");
					j++;
					no_grow++;
					value = null;
				}
				// For one local, get the highest number of matches. (Setting priority of number of matches over color)
				line_max = diff[i][0];
				for (int p = 0; p < j; p++) {
					if (diff[i][p] > line_max) {
						line_max = diff[i][p];
					}
				}

				// Get the first value in colors, if number of matches are same.
				int valuekey = (int) line_max;
				int perfectlineno = Perfect.entrySet().stream().filter(e -> e.getValue().equals(valuekey))
						.map(Map.Entry::getKey).findFirst().orElse(-1);
				int alllineno = All.entrySet().stream().filter(e -> e.getValue().equals(valuekey))
						.map(Map.Entry::getKey).findFirst().orElse(-1);

				// If EOM= 1000 + no of matches, if PATTERN=500 + no of matches, NoColor=no of matches
				if (perfectlineno != -1) {
					diff[i][perfectlineno] = 1000 + diff[i][perfectlineno];
					countGreen++;
				} else if (alllineno != -1) {
					diff[i][alllineno] = 500 + diff[i][alllineno];
					countYellow++;
				}

				// Nullifying all the variables.
				i++;
				j = 0;
				Perfect = new HashMap<>();
				All = new HashMap<>();
			}
			flagALLGreen = null;
			flagALLYellow = null;
			localArray = null;
			varientArr = null;
			Perfect = null;
			All = null;
			// System.out.println();
			// System.out.println();

			FileOutputStream fileOut = new FileOutputStream(new File(outputfile));

			XSSFSheet mainsheet = outworkbook.getSheetAt(0);
			Row r = mainsheet.getRow(0);
			int maxCell = r.getLastCellNum();

			// Creating a tab with column used and final global mapping with colors
			XSSFSheet spreadsheet = outworkbook.createSheet(level + " Mapping");

			int pos = 0;
			locIterator = lsheet.iterator();

			// Setting color for EOM
			XSSFCellStyle greenWriteStyle = outworkbook.createCellStyle();
			greenWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80)));
			greenWriteStyle.setFillPattern(CellStyle.BORDER_THIN);

			// Setting color for Pattern
			XSSFCellStyle orangeWriteStyle = outworkbook.createCellStyle();
			orangeWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(153, 204, 255)));// java.awt.Color.ORANGE));
			orangeWriteStyle.setFillPattern(CellStyle.BORDER_THIN);

			// Setting color for Probabilistic
			XSSFCellStyle cyanWriteStyle = outworkbook.createCellStyle();
			cyanWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 204, 153)));// java.awt.Color.CYAN));
			cyanWriteStyle.setFillPattern(CellStyle.BORDER_THIN);

			// Writing values in a different tab with local columns and global mappings
			for (m1 = 0; m1 < diff.length; m1++) {
				Cell cell = locIterator.next().getCell(0);
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					locline = Double.toString(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					locline = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					locline = "";
					break;
				default:
					locline = "";
					break;
				}
				// System.out.print(locline);

				max = diff[m1][0];
				pos = 0;
				for (n1 = 0; n1 < glinenumber; n1++) {
					if (diff[m1][n1] > max) {
						max = diff[m1][n1];
						pos = n1;
					}
				}

				// Getting the global mapping according to the max value of pattern match
				Cell cellGlobal = gsheet.getRow(pos).getCell(0);
				switch (cellGlobal.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					lineIWant = Double.toString(cellGlobal.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					lineIWant = cellGlobal.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					lineIWant = "";
					break;
				default:
					lineIWant = "";
					break;
				}

				// -----> System.out.println(locline + "~" + max + "~" + lineIWant);

				XSSFRow rownew = spreadsheet.createRow(m1);

				XSSFSheet colsheet = workbook.getSheetAt(0);
				// Writing global mapping in the main sheet with style
				Row colrow = colsheet.getRow(m1);
				int col = 0;
				String colline = null;
				for (i = 0; i < columns.length; i++) {
					int val = columns[i] - 1;
					Cell cellcol = colrow.getCell(val);
					switch (cellcol.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						colline = Double.toString(cellcol.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						colline = cellcol.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						colline = "";
						break;
					}
					XSSFCell cellA1 = rownew.createCell(col++);
					cellA1.setCellValue(colline);
				}
				XSSFCell cellA12 = rownew.createCell(col);
				cellA12.setCellValue(lineIWant);
				spreadsheet.getRow(0).getCell(col).setCellValue("Global");

				Row mainrow = mainsheet.getRow(m1);
				XSSFCell cellmax = (XSSFCell) mainrow.createCell(maxCell);
				cellmax.setCellValue(lineIWant);
				if (max >= 1000) {
					cellmax.setCellStyle(greenWriteStyle);
					cellA12.setCellStyle(greenWriteStyle);
				} else if ((max >= 500) && (max < 1000)) {
					cellmax.setCellStyle(orangeWriteStyle);
					cellA12.setCellStyle(orangeWriteStyle);
				} else if (0 == max) {
					countNomatch++;
				} else {
					cellmax.setCellStyle(cyanWriteStyle);
					cellA12.setCellStyle(cyanWriteStyle);
				}
				// cellmax.setCellStyle(style);
			}

			mainsheet.getRow(0).getCell(maxCell).setCellValue("Global " + level);

			// Removing intermediate concat local sheet
			XSSFSheet sheet = outworkbook.getSheet(level);
			if (sheet != null) {
				int rindex = outworkbook.getSheetIndex(sheet);
				outworkbook.removeSheetAt(rindex);
			}

			outworkbook.write(fileOut);
			fileOut.close();

			categoryVO = new ProductHierarchyVO();
			categoryVO.setName(level);
			categoryVO.setPerfectMatchCount(countGreen);
			categoryVO.setPatternMatchCount(countYellow);
			categoryVO.setNoMatchCount(countNomatch);
			categoryVO.setTotalCount(llinenumber);
		} catch (FileNotFoundException e) {
			log.error("File not found", e);
		} catch (IOException e) {
			log.error("Error in reading file", e);
		} catch (NullPointerException e) {
			log.error("Check if file is open", e);
		} finally {
			if (mReader != null) {
				try {
					mReader.close();
				} catch (IOException e) {
					log.error("error in closing local input file", e);
				}
			}
			if (outReader != null) {
				try {
					outReader.close();
				} catch (IOException e) {
					log.error("error in closing output file", e);
				}
			}
			if (gReader != null) {
				try {
					gReader.close();
				} catch (IOException e) {
					log.error("error in closing global mapping file", e);
				}
			}
		}
		return categoryVO;

	}
}
