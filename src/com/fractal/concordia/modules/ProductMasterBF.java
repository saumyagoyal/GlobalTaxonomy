package com.fractal.concordia.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.common.BaseBF;
import com.fractal.concordia.utils.CCDConstants;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.ProductHierarchyVO;
import com.fractal.concordia.vo.Varient;
import com.fractal.concordia.vo.VarientRowVO;

public class ProductMasterBF extends BaseBF {

	Logger log = Logger.getLogger(ProductMasterBF.class);

	// public List<Category> readProductMastersFromGlobalFile() {
	//
	// List<Category> categories = new ArrayList<Category>();
	// Category category = null;
	// List<String> varients = null;
	// FileInputStream excelFile = null;
	// try {
	// excelFile = new FileInputStream(new File("C:/Users/angad.singh/Downloads/concordia/Global-Indo(CPW).xlsx"));
	// Workbook workbook = new XSSFWorkbook(excelFile);
	// for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	// category = new Category();
	// varients = new ArrayList<String>();
	//
	// Sheet datatypeSheet = workbook.getSheetAt(i);
	// Iterator<Row> iterator = datatypeSheet.iterator();
	//
	// while (iterator.hasNext()) {
	// Row currentRow = iterator.next();
	// Iterator<Cell> cellIterator = currentRow.iterator();
	// String varient = "";
	//
	// while (cellIterator.hasNext()) {
	// Cell currentCell = cellIterator.next();
	// if (currentCell != null) {
	// if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
	// if (varient.length() > 0) {
	// varient = varient + " | " + currentCell.getStringCellValue();
	// } else {
	// varient = currentCell.getStringCellValue();
	// }
	// } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	// if (varient.length() > 0) {
	// varient = varient + " | " + currentCell.getNumericCellValue();
	// } else {
	// varient = currentCell.getNumericCellValue() + "";
	// }
	// }
	// }
	// }
	// varients.add(varient);
	// }
	// category.setName(workbook.getSheetName(i));
	// category.setVarients(varients);
	// categories.add(category);
	// }
	//
	// } catch (IOException e) {
	// log.error("Error in reading excel", e);
	// } finally {
	// if (excelFile != null) {
	// try {
	// excelFile.close();
	// } catch (IOException e) {
	// log.error("Error in closing input stream", e);
	// }
	// }
	// }
	// return categories;
	// }

	public List<ProductHierarchyVO> readProductMastersFromGlobalFileTest(CCDMappingVO ccdMappingVO) {

		List<ProductHierarchyVO> productHierarchyVOs = new ArrayList<ProductHierarchyVO>();
		ProductHierarchyVO productHierarchyVO = null;
		List<Varient> varients = null;
		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(
					new File(ccdMappingVO.getGlobalFile()));
			Workbook workbook = new XSSFWorkbook(excelFile);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				productHierarchyVO = new ProductHierarchyVO();
				int maxVariantCount = 0;
				varients = new ArrayList<Varient>();

				Sheet datatypeSheet = workbook.getSheetAt(i);
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();
					Varient varient = new Varient();
					List<VarientRowVO> varientRows = null;
					VarientRowVO varientRow = null;
					while (cellIterator.hasNext()) {

						varientRow = new VarientRowVO();

						Cell currentCell = cellIterator.next();
						if (currentCell != null) {
							if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
								varientRow.setName(currentCell.getStringCellValue());

								if ((currentCell.getCellStyle() != null)
										&& (currentCell.getCellStyle().getFillBackgroundColorColor() != null)) {
									CellStyle clone = workbook.createCellStyle();
									clone.cloneStyleFrom(currentCell.getCellStyle());
									XSSFColor c = (XSSFColor) clone.getFillForegroundColorColor();
									String color = c.getARGBHex().substring(2);
									if ((color != null) && (c.getARGBHex() != null)
											&& (c.getARGBHex().substring(2) != null)) {
										if (c.getARGBHex().substring(2).equalsIgnoreCase(CCDConstants.GREEN)) {
											varientRow.setColour("lightgreen");
										} else {
											varientRow.setColour("wheat");
										}
									}
								}
								// varientRow.setColour("green");
								// if ((currentCell.getCellStyle() != null)
								// && (currentCell.getCellStyle().getFillBackgroundColorColor() != null)) {
								// Color color = currentCell.getCellStyle().getFillBackgroundColorColor();
								// if (color.equals(IndexedColors.YELLOW)) {
								// varientRow.setColour("green");
								// }
								// }
							} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								varientRow.setName(currentCell.getNumericCellValue() + "");
							}
						}
						if (varientRows == null) {
							varientRows = new ArrayList<VarientRowVO>();
						}
						varientRows.add(varientRow);
					}
					varient.setVarientRow(varientRows);
					varients.add(varient);
					if ((varientRows != null) && (maxVariantCount < varientRows.size())) {
						maxVariantCount = varientRows.size();
					}
				}
				productHierarchyVO.setName(workbook.getSheetName(i));
				List<Integer> maxList = new ArrayList<>();
				for (int j = 0; j < (maxVariantCount - 2); j++) {
					maxList.add(0);
				}
				productHierarchyVO.setMaxList(maxList);
				productHierarchyVO.setVarients(varients);
				productHierarchyVO.setNoMatchCount(maxVariantCount);
				productHierarchyVOs.add(productHierarchyVO);
			}

		} catch (IOException e) {
			log.error("Error in reading excel", e);
		} finally {
			if (excelFile != null) {
				try {
					excelFile.close();
				} catch (IOException e) {
					log.error("Error in closing input stream", e);
				}
			}
		}
		return productHierarchyVOs;
	}

}
