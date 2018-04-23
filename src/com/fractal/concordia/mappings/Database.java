/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractal.concordia.mappings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.utils.EncoderAndDecoderUtil;
import com.fractal.concordia.vo.CCDMappingVO;

/**
 *
 * @author saumya.goyal
 */

/**
 * This class provides an interface to various encoders for producing phonetic or reduced forms from words.
 */
public class Database {

	private static final Logger log = Logger.getLogger(EncoderAndDecoderUtil.class.getName());

	public static void saveFile(CCDMappingVO ccdMappingVO) {

		String url = "jdbc:mysql://localhost:3306/concordiaui?zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true&amp;autoCommit=false";
		// "jdbc:mysql://localhost:3306/contactdb";
		String user = "root";
		String password = "1qaz2wsx";

		String filePath = ccdMappingVO.getGlobalFile(); // "C://Mapping//CPW//Global-Indo(CPW).xlsx";

		InputStream inputStream = null;
		PreparedStatement statement = null;

		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO inventory ( excel) values (?)";
			statement = conn.prepareStatement(sql);
			// statement.setString(1, "1");

			inputStream = new FileInputStream(new File(filePath));
			statement.setBlob(1, inputStream);

			int row = statement.executeUpdate();
			if (row > 0) {
				System.out.println("Excel");
			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("error in closing local input file", e);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("error in closing local input file", e);
				}
			}
		}

	}

	// public static void main(String[] args) {
	// readFile();
	// }
	public static void readFile(CCDMappingVO ccdMappingVO) {

		String url = "jdbc:mysql://localhost:3306/concordiaui?zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true&amp;autoCommit=false";
		// "jdbc:mysql://localhost:3306/contactdb";
		String user = "root";
		String password = "1qaz2wsx";
		ResultSet resultSet = null;

		String newname = "_" + (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")).format(new java.util.Date()) + ".xlsx";
		String filePath = ccdMappingVO.getGlobalFile().replace(".xlsx", newname);
		// "C://Mapping//CPW//Global-Indo(CPW)" + (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"))+ ".xlsx";
		// FileOutputStream fileOut = null;

		InputStream inputStream = null;
		PreparedStatement statement = null;

		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "select Excel from inventory where id=1";
			statement = conn.prepareStatement(sql);
			// statement.setString(1, "1");
			// inputStream = new FileInputStream(new File(filePath));
			// statement.setBlob(1, inputStream);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				java.sql.Blob blob = resultSet.getBlob("Excel");
				inputStream = blob.getBinaryStream();
				XSSFWorkbook globalworkbook = new XSSFWorkbook(inputStream);
				FileOutputStream fileOut = new FileOutputStream(new File(filePath));
				globalworkbook.write(fileOut);
				fileOut.close();
			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("error in closing local input file", e);
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.error("error in closing local input file", e);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("error in closing local input file", e);
				}
			}
		}
	}
}
