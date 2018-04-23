/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractal.concordia.mappings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.utils.CCDConstants;
import com.fractal.concordia.utils.EncoderAndDecoderUtil;
import com.fractal.concordia.vo.ProductHierarchyVO;

/**
 *
 * @author saumya.goyal
 */

/**
 * This class provides an interface to various encoders for producing phonetic or reduced forms from words.
 */
public class Calling {

	private static final Logger log = Logger.getLogger(EncoderAndDecoderUtil.class.getName());

	public static ProductHierarchyVO taxonomyCalling(String level, int[] column, String main, String globalpath,
			String outputfile) {

		// List of special characters taken care of in the algorithm
		String[] sCon = new String[] { "/", "\\", "-", "+", "(", ")", "&", ".", ",", "'" };
		String[] rCon = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

		// List of special characters they do not want to handle for the specific level of hierarchy
		String[] not_sCon = new String[] { "&" };

		// Logic to remove special characters from consideration
		for (int delete = 0; delete < not_sCon.length; delete++) {
			for (int special = 0; special < sCon.length; special++) {
				if (not_sCon[delete] == sCon[special]) {
					sCon[special] = " ";
				}
			}
		}

		for (int special = 0; special < sCon.length; special++) {
			System.out.print(sCon[special]);
		}
		System.out.println();

		FileInputStream gReader = null;
		int glinenumber;
		String gloline = null;
		XSSFCellStyle style5;
		ArrayList<String> global = null;

		// For all level hierarchy one by one converting global excel file into a global array
		try{
			gReader = new FileInputStream(new File(globalpath));
			XSSFWorkbook globalworkbook = new XSSFWorkbook(gReader);
			XSSFSheet gsheet = globalworkbook.getSheet(level);
			glinenumber = gsheet.getPhysicalNumberOfRows();
			Iterator<Row> globIterator = gsheet.iterator();

			global = new ArrayList<String>();

			while (globIterator.hasNext()) {
				Row grow = globIterator.next();
				Iterator<Cell> cellIterator = grow.cellIterator();
				String globalarray = null;
				while (cellIterator.hasNext()) {

					XSSFCell cell = (XSSFCell) cellIterator.next();
					if (cell.getCellStyle().getFillForegroundColorColor() != null) {

						// Putting markers for colored cells in excel for exact order match and pattern match
						style5 = globalworkbook.createCellStyle();
						style5.cloneStyleFrom(cell.getCellStyle());

						// System.out.println(style5.getFillForegroundColorColor().getARGBHex());
						if (style5.getFillForegroundColorColor().getARGBHex().equals(CCDConstants.EOM_COLOR)) {
							// System.out.println("Perfect Order ");
							gloline = "[ExactOrderMatch]  ";
						} else if (style5.getFillForegroundColorColor().getARGBHex().equals(CCDConstants.PATTERN_COLOR)) {
							// System.out.println("All-match");
							gloline = "[PattternMatch]  ";
						} else {
							gloline = "";
						}
					} else {
						gloline = "[ProbabilisticMatch]  ";
					}

					// gloline = cell.getStringCellValue();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						gloline = gloline + Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						gloline = gloline + cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						gloline = gloline + "";
						break;
					default:
						gloline = "";
						break;
					}
					gloline = gloline.replace("  ", " ").replace("  ", " ");
					gloline = gloline.replace("  ", " ");
					// ----->> System.out.println(gloline);
					if (globalarray != null) {
						globalarray = globalarray + "," + gloline;
					} else {
						globalarray = gloline;
					}
				}
				// ----> System.out.println(globalarray);
				// Creating the whole global array
				global.add(globalarray);
			}
		} catch (IOException e) {
			log.error("Error in reading file", e);
		} finally {
			if (gReader != null) {
				try {
					gReader.close();
				} catch (IOException e) {
					log.error("error in closing local input file", e);
				}
			}
		}

		// Calling function for mapping by looking at the hierarchy
		ProductHierarchyVO productHierarchyVO;
		if (level.toLowerCase().equals("Size".toLowerCase())) {
			// Size has a different rule for mappings
			productHierarchyVO = Metrics.metricMapping(level, column, main, globalpath, outputfile, global, sCon, rCon);

		} else {
			// Mapping for all other non-numeric hierarchy
			productHierarchyVO = Mapping.levelMapping(level, column, main, globalpath, outputfile, global, sCon, rCon);

		}
		global = null;
		return productHierarchyVO;
	}
}
