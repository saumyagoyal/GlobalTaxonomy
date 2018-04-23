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
public class Metrics {

	private static final Logger log = Logger.getLogger(EncoderAndDecoderUtil.class.getName());

	public static ProductHierarchyVO metricMapping(String level, int[] columns,
			String main, String globalpath, String outputfile, ArrayList<String> global, String[] sCon, String[] rCon) {


		String locline;
		String gloline = null;
		String globallist;
		int i = 0;
		int j = 0;

		int len = 0;
		int glen = 0;
		int var = 0;
		//        String[] globalArray = null;
		String[] localArray = null;
		String[] varientArr = null;
		int gmax = 0;

		int flag = 0;     //1 for JnJ

		//double min;
		double max;
		int m1 = 0;
		int n1 = 0;
		String lineIWant;
		int[] value;
		int[] index;
		int[] flagALLGreen = null;
		int[] flagALLYellow = null;
		//No.of lines in Global file
		int glinenumber = 0;
		int llinenumber = 0;


		int countGreen = 0;
		int countYellow = 0;
		int countNomatch = 0;

		HashMap<Integer, Integer> Perfect = new HashMap<>();
		HashMap<Integer, Integer> All = new HashMap<>();
		Iterator<String> globArrayIterator = null;

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

			// System.out.println(glinenumber + "" + llinenumber);

			Iterator<Row> locIterator = lsheet.iterator();
			while (locIterator.hasNext()) {
				Row lrow = locIterator.next();
				locline = lrow.getCell(0).getStringCellValue();
				locline = StringUtils.replaceEach(locline.toLowerCase(), Constants.searchmetric, Constants.replacemetric);

				locline = StringUtils.replaceEach(locline, sCon, rCon);
				String[] sString = new String[]{"x0", "x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "0x", "1x", "2x", "3x", "4x", "5x", "6x", "7x", "8x", "9x"};
				String[] rString = new String[]{"x 0", "x 1", "x 2", "x 3", "x 4", "x 5", "x 6", "x 7", "x 8", "x 9", "0 x", "1 x", "2 x", "3 x", "4 x", "5 x", "6 x", "7 x", "8 x", "9 x"};

				locline = StringUtils.replaceEach(locline.toLowerCase(), sString, rString);
				locline = StringUtils.replaceEach(locline.toLowerCase(), sString, rString);
				//System.out.println(locline);
				localArray = locline.replace("  ", " ").split(" ");

				Iterator<Row> globIterator = gsheet.iterator();
				int PerfectMatch = -1;
				int AllMatch = -1;
				int no_grow = 0;

				double line_max;
				globArrayIterator = global.iterator();
				while (globArrayIterator.hasNext()) {
					globallist = globArrayIterator.next();
					String[] globalListArray = globallist.split(",");

					// int no_cell = grow.getPhysicalNumberOfCells();
					int no_cell = globalListArray.length;

					value = new int[globalListArray.length];
					index = new int[1000];
					int loc_pos = 0;

					int green_cell = -1;
					int yellow_cell = -1;
					int green_len = -1;
					int yellow_len = -1;

					boolean sorted = true;
					XSSFCellStyle style5;

					glen = 0;
					// while (cellIterator.hasNext()) {
					for (int globalList = 0; globalList < globalListArray.length; globalList++) {

						gloline = globalListArray[globalList];
						// ----->>ystem.out.println(gloline);

						diff[i][j] = 0;

						//gloline = StringUtils.replaceEach(gloline, sCon, rCon);
						String ordercheck = "";
						gloline = StringUtils.replaceEach(gloline, sCon, rCon);
						gloline = gloline.replace("  ", " ").replace("  ", " ");
						gloline = gloline.replace("  ", " ");
						value[glen] = 0;

						varientArr = gloline.trim().split(" ");
						flagALLGreen = new int[varientArr.length];
						flagALLYellow = new int[varientArr.length];

						for (len = 0; len < localArray.length; len++) {

							for (var = 0; var < varientArr.length; var++) {

								//for (len = 0; len < localArray.length; len++) {
								localArray[len] = localArray[len].replace("'", "");
								varientArr[var] = varientArr[var].replace("'", "");
								//System.out.println(cell.getCellStyle().getFillBackgroundColorColor());
								if ((localArray[len].toLowerCase().trim()).equals(varientArr[var].toLowerCase().trim())) {

									if (gloline.contains(("[ExactOrderMatch]").toLowerCase())) {
										//=====================================
										//System.out.println("Perfect Order ");
										ordercheck = ordercheck + " " + localArray[len];
										green_len = varientArr.length;
										value[glen]++;
										//index[loc_pos++] = len;
										index[loc_pos++] = var;
										green_cell = globalList; //cell.getColumnIndex();
										flagALLGreen[var] = 1;
										// greenStyle = cell.getCellStyle();
										//} else if (style5.getFillForegroundColorColor().getARGBHex().equals(CCDConstants.PATTERN_COLOR)) {
									} else if (gloline.contains(("[PattternMatch]").toLowerCase())) {
										//System.out.println("All-match");
										yellow_len = varientArr.length;
										value[glen]++;
										//index[loc_pos++] = len;
										yellow_cell = globalList; // cell.getColumnIndex();
										flagALLYellow[var] = 1;
										// yellowStyle = cell.getCellStyle();
									}
									else {
										//flagALLGreen = null;
										//flagALLYellow = null;
										value[glen]++;

									}
									// put the condition for 2
									if ((flag == 1) && (varientArr[var].trim().length() < 2)) {
										value[glen]--;

									}
								}
							}
						}
						//System.out.println(ordercheck);
						if (green_cell == globalList) {
							if (!ordercheck.contains(gloline.replace("[exactordermatch]", ""))) {
								value[glen] = -1;
							}
						}
						if (green_cell == globalList) {
							for (int f = 0; f < flagALLGreen.length; f++) {
								if (flagALLGreen[f] == 0) {
									value[glen] = -1;
									// System.out.println("Perfect order failed");
								}
							}
						}
						if (yellow_cell == globalList) {
							for (int f = 0; f < flagALLYellow.length; f++) {
								if (flagALLYellow[f] == 0) {
									value[glen] = -1;
									// System.out.println("All match failed");
								}
							}
						}
						glen++;
					}
					gmax = 0;

					for (n1 = 0; n1 < no_cell; n1++) {
						if ((value[n1] > gmax) && (n1 != green_cell) && (n1 != yellow_cell)) {
							gmax = value[n1];
							//System.out.println(pos);
						} else if ((value[n1] >= gmax) && (n1 == yellow_cell) && (PerfectMatch == -1)) {      //grow.getCell(n1).getCellStyle().getFillBackgroundColorColor() == Color.RED ) {
							gmax = value[n1];
							// if (gloline.contains(("[PattternMatch]").toLowerCase())) {
							AllMatch = no_grow; // grow.getRowNum();
							// All.put(grow.getRowNum(), gmax);
							All.put(no_grow, gmax);
							// }
						} else if ((value[n1] >= gmax) && (n1 == green_cell)) {      //grow.getCell(n1).getCellStyle().getFillBackgroundColorColor() == Color.RED ) {
							//System.out.println(n1);         //.getCellStyle().getFillBackgroundColorColor());
							gmax = value[n1];
							// if (style5.getFillForegroundColorColor().getARGBHex().equals(CCDConstants.EOM_COLOR)) {
							// if (gloline.contains(("[ExactOrderMatch]").toLowerCase())) {
							PerfectMatch = no_grow; // grow.getRowNum();
							// Perfect.put(grow.getRowNum(), gmax);
							Perfect.put(no_grow, gmax);
							// }
						}
					}
					diff[i][j] = gmax;

					// --------->>System.out.println(diff[i][j] + " ");
					j++;
					no_grow++;
					value = null;
				}
				line_max = diff[i][0];
				for (int p = 0; p < j; p++) {
					//System.out.println(diff[i][p]);
					if (diff[i][p] > line_max) {
						line_max = diff[i][p];
					}
				}

				int valuekey = (int) line_max;
				int perfectlineno = Perfect.entrySet().stream().filter(e -> e.getValue().equals(valuekey))
						.map(Map.Entry::getKey).findFirst().orElse(-1);
				int alllineno = All.entrySet().stream().filter(e -> e.getValue().equals(valuekey))
						.map(Map.Entry::getKey).findFirst().orElse(-1);

				if (perfectlineno != -1) {
					diff[i][perfectlineno] = 1000 + diff[i][perfectlineno];
					countGreen++;
				} else if (alllineno != -1) {
					diff[i][alllineno] = 500 + diff[i][alllineno];
					countYellow++;
				}

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

			FileOutputStream fileOut = new FileOutputStream(new File(outputfile));

			XSSFSheet mainsheet = outworkbook.getSheetAt(0);
			Row r = mainsheet.getRow(0);
			int maxCell = r.getLastCellNum();

			XSSFSheet spreadsheet = outworkbook.createSheet(level + " Mapping");

			int pos = 0;
			locIterator = lsheet.iterator();

			XSSFCellStyle greenWriteStyle = outworkbook.createCellStyle();
			greenWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80)));
			greenWriteStyle.setFillPattern(CellStyle.BORDER_THIN);

			XSSFCellStyle orangeWriteStyle = outworkbook.createCellStyle();
			orangeWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(153, 204, 255)));
			orangeWriteStyle.setFillPattern(CellStyle.BORDER_THIN);

			XSSFCellStyle cyanWriteStyle = outworkbook.createCellStyle();
			cyanWriteStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 204, 153)));
			cyanWriteStyle.setFillPattern(CellStyle.BORDER_THIN);

			for (m1 = 0; m1 < diff.length; m1++) {
				locline = locIterator.next().getCell(0).getStringCellValue();
				//           switch (cell.getCellType()) {
				//                        case Cell.CELL_TYPE_NUMERIC:
				// locline = Double.toString(cell.getNumericCellValue());
				//                            break;
				//                        case Cell.CELL_TYPE_STRING:
				// locline = cell.getStringCellValue();
				//                            break;
				//                        case Cell.CELL_TYPE_BLANK:
				// locline = "";
				//                            break;
				//                    }
				//System.out.print(locline);

				max = diff[m1][0];
				pos = 0;
				for (n1 = 0; n1 < glinenumber; n1++) {
					if (diff[m1][n1] > max) {
						max = diff[m1][n1];
						pos = n1;
						//System.out.println(pos);
					}
				}
				//System.out.print("~" + max);
				lineIWant = gsheet.getRow(pos).getCell(0).getStringCellValue();

				// System.out.println(locline + "~" + max + "~" + lineIWant);

				XSSFRow rownew = spreadsheet.createRow(m1);

				XSSFSheet colsheet = workbook.getSheetAt(0);
				Row colrow = colsheet.getRow(m1);
				int col = 0;
				String colline = null;
				for (i = 0; i < columns.length; i++) {
					int val = columns[i] - 1;
					Cell cell = colrow.getCell(val);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						colline = Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						colline = cell.getStringCellValue();
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
				//cellmax.setCellStyle(style);

			}
			mainsheet.getRow(0).getCell(maxCell).setCellValue("Global " + level);

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
		}
		finally {
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
