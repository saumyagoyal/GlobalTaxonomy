package com.fractal.concordia.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.common.BaseBF;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.HeaderVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

public class SetupBF extends BaseBF {

	Logger log = Logger.getLogger(SetupBF.class);

	// public void saveCategories(List<CategoryVO> categories) throws Exception {
	// String query = "CategoryVO.insertCategory";
	// fetchList(query, categories);
	// }

	public List<ProductHierarchyVO> readCategories(CCDMappingVO ccdMappingVO) {
		List<ProductHierarchyVO> hierarchyVOs = new ArrayList<ProductHierarchyVO>();
		ProductHierarchyVO productHierarchyVO = null;
		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(ccdMappingVO.getGlobalFile());
			Workbook workbook = new XSSFWorkbook(excelFile);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				productHierarchyVO = new ProductHierarchyVO();
				productHierarchyVO.setName(workbook.getSheetName(i));
				hierarchyVOs.add(productHierarchyVO);
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
		return hierarchyVOs;
	}

	public List<HeaderVO> readHeaders(CCDMappingVO ccdMappingVO) {
		List<HeaderVO> headerVOs = new ArrayList<HeaderVO>();
		HeaderVO headerVO = null;
		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(
					new File(ccdMappingVO.getLocalFile()));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Row row = datatypeSheet.getRow(0);
			Iterator<Cell> cellIterator = row.iterator();
			int cellNo = 1;
			while (cellIterator.hasNext()) {
				headerVO = new HeaderVO();
				Cell currentCell = cellIterator.next();
				if (currentCell != null) {
					if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
						headerVO.setName(currentCell.getStringCellValue());
						headerVO.setNumber(cellNo);
					} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						headerVO.setName(currentCell.getNumericCellValue() + "");
						headerVO.setNumber(cellNo);
					}
					headerVOs.add(headerVO);
				}
				cellNo++;
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
		return headerVOs;

	}

}
