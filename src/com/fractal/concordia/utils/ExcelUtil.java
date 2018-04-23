package com.fractal.concordia.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	Logger log = Logger.getLogger(ExcelUtil.class);

	public void readExcel() {

		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(new File("C:/Users/angad.singh/Downloads/concordia/Global-Indo(CPW).xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			List<String> sheetNames = new ArrayList<String>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				sheetNames.add(workbook.getSheetName(i));
				System.out.println(workbook.getSheetName(i));
			}
			System.out.println();
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				String value = "";
				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();

					if (currentCell != null) {
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (value.length() > 0) {
								value = value + " | " + currentCell.getStringCellValue();
							} else {
								value = currentCell.getStringCellValue();
							}
						} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							if (value.length() > 0) {
								value = value + " | " + currentCell.getNumericCellValue();
							} else {
								value = currentCell.getNumericCellValue() + "";
							}
						}
					}

				}
				System.out.print(value);
				System.out.println();

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
	}

	private static Logger logger = Logger.getLogger(ExcelUtil.class.getName());
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	private static BufferedInputStream input = null;
	private static BufferedOutputStream output = null;

	public static Boolean fileDownload(String path, String filename) {
		// Prepare.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		Boolean success = false;

		File file = new File(path);

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {

				output.write(buffer, 0, length);
			}
			output.flush();
		} catch (FileNotFoundException e) {
			logger.error("fileDownload : " + e.getMessage());

			success = true;
		} catch (IOException e) {
			logger.error("fileDownload io: " + e.getMessage());
			success = true;
		} finally {
			// Gently close streams.
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("error in closing input stream", e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error("error in closing output stream", e);
				}
			}

		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following
		// exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has
		// been committed.
		facesContext.responseComplete();
		return success;
	}
}
