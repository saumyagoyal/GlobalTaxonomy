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
import com.fractal.concordia.vo.OutputVO;
import com.fractal.concordia.vo.ResultMappingVO;

public class OutputBF extends BaseBF {
	Logger log = Logger.getLogger(OutputBF.class);

	public List<OutputVO> fetchOutputData() {

		FileInputStream excelFile = null;
		List<OutputVO> outputList = new ArrayList<OutputVO>();
		try {
			excelFile = new FileInputStream(new File(CCDConstants.ouptup_file_path));
			Workbook workbook = new XSSFWorkbook(excelFile);
			for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
				OutputVO outputVO = new OutputVO();
				Sheet datatypeSheet = workbook.getSheetAt(i);
				Iterator<Row> iterator = datatypeSheet.iterator();
				ResultMappingVO resultMappingVO = null;

				List<ResultMappingVO> greenMapping = new ArrayList<ResultMappingVO>();
				List<ResultMappingVO> blueMapping = new ArrayList<ResultMappingVO>();
				List<ResultMappingVO> orangeMapping = new ArrayList<ResultMappingVO>();
				List<ResultMappingVO> greyMapping = new ArrayList<ResultMappingVO>();
				List<String> headers = new ArrayList<String>();
				int rowNo = 0;
				int totalColumns = 0;
				while (iterator.hasNext()) {
					Row currentRow = iterator.next();
					resultMappingVO = new ResultMappingVO();
					List<String> columnList = new ArrayList<String>();
					if (rowNo == 0) {
						Iterator<Cell> cellIterator = currentRow.iterator();
						while (cellIterator.hasNext()) {
							Cell currentCell = cellIterator.next();
							totalColumns++;
							headers.add(currentCell.getStringCellValue());
						}
					}
					else {
						Iterator<Cell> cellIterator = currentRow.iterator();
						int columnNo = 1;


						while (cellIterator.hasNext()) {
							Cell currentCell = cellIterator.next();
							if (currentCell != null) {
								if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
									columnList.add(currentCell.getStringCellValue());
									if (columnNo == totalColumns) {
										resultMappingVO.setColumnList(columnList);
										if ((currentCell.getCellStyle() != null)) {
											CellStyle clone = workbook.createCellStyle();
											clone.cloneStyleFrom(currentCell.getCellStyle());
											XSSFColor c = (XSSFColor) clone.getFillForegroundColorColor();
											if ((c != null) && (c.getARGBHex() != null)) {
												String color = c.getARGBHex().substring(2);
												if (color != null) {
													if (c.getARGBHex().substring(2)
															.equalsIgnoreCase(CCDConstants.GREEN)) {
														// green
														greenMapping.add(resultMappingVO);
													} else if (c.getARGBHex().substring(2)
															.equalsIgnoreCase(CCDConstants.BLUE)) {
														// blue
														// blueMapping.add(resultMappingVO);
														orangeMapping.add(resultMappingVO);
													} else {
														// orange
														// orangeMapping.add(resultMappingVO);
														blueMapping.add(resultMappingVO);
													}
												}
											} else {
												greyMapping.add(resultMappingVO);
											}
										} else {
											greyMapping.add(resultMappingVO);
										}
									}


								} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									columnList.add(currentCell.getNumericCellValue() + "");
									if (columnNo == totalColumns) {
										resultMappingVO.setColumnList(columnList);
										if ((currentCell.getCellStyle() != null)) {
											CellStyle clone = workbook.createCellStyle();
											clone.cloneStyleFrom(currentCell.getCellStyle());
											XSSFColor c = (XSSFColor) clone.getFillForegroundColorColor();
											if ((c != null) && (c.getARGBHex() != null)) {
												String color = c.getARGBHex().substring(2);
												if (color != null) {
													if (c.getARGBHex().substring(2)
															.equalsIgnoreCase(CCDConstants.GREEN)) {
														// green
														greenMapping.add(resultMappingVO);
													} else if (c.getARGBHex().substring(2)
															.equalsIgnoreCase(CCDConstants.BLUE)) {
														// blue
														// blueMapping.add(resultMappingVO);
														orangeMapping.add(resultMappingVO);
													} else {
														// orange
														// orangeMapping.add(resultMappingVO);
														blueMapping.add(resultMappingVO);
													}
												}
											} else {
												greyMapping.add(resultMappingVO);
											}
										} else {
											greyMapping.add(resultMappingVO);
										}
									}


								}

							}
							columnNo++;
						}
					}
					rowNo++;
				}
				outputVO.setProduchHierarchyName(datatypeSheet.getSheetName());
				outputVO.setHeaders(headers);
				outputVO.setGreenMappingVOs(greenMapping);
				outputVO.setOrangeMappingVOs(orangeMapping);
				outputVO.setBlueMappingVOs(blueMapping);
				outputVO.setGreyMappingVOs(greyMapping);
				outputList.add(outputVO);
			}

		} catch (IOException e) {
			log.error("error in reading output file", e);
		} finally {
			if (excelFile != null) {
				try {
					excelFile.close();
				} catch (IOException e) {
					log.error("error in closing output file", e);
				}
			}
		}
		return outputList;

	}
}
