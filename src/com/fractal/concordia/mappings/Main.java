/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractal.concordia.mappings;

import java.util.ArrayList;
import java.util.List;

import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.HeaderVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

/**
 *
 * @author saumya.goyal
 */

/**
 * This class provides an interface to various encoders for producing phonetic or reduced forms from words.
 */
public class Main {

	public static void main(String[] args) {


		// Running the entire Algorithm via Java application, setting up all the variables needed.

		String main = "C:\\Mapping\\CP\\LBook1.xlsx";
		String globalpath = "C:\\Mapping\\CP\\GBook1.xlsx";

		CCDMappingVO ccdMappingVO = new CCDMappingVO();
		ccdMappingVO.setGlobalFileName(globalpath);
		ccdMappingVO.setLocalFileName(main);
		ccdMappingVO.setGlobalPath("");
		ccdMappingVO.setLocalPath("");

		List<ProductHierarchyVO> productHierarchyVOList = new ArrayList<ProductHierarchyVO>();

		ProductHierarchyVO categoryVO1 = new ProductHierarchyVO();
		List<HeaderVO> headerVOs1 = new ArrayList<HeaderVO>();
		categoryVO1.setName("Manufacturer");
		int[] column1 = new int[] { 1, 2 };
		for (int j = 0; j < column1.length; j++) {
			HeaderVO headerVO = new HeaderVO();
			headerVO.setNumber(column1[j]);
			headerVOs1.add(headerVO);
		}
		categoryVO1.setHeaders(headerVOs1);
		productHierarchyVOList.add(categoryVO1);

		ProductHierarchyVO categoryVO2 = new ProductHierarchyVO();
		List<HeaderVO> headerVOs2 = new ArrayList<HeaderVO>();
		int[] column2 = new int[] { 1, 3 };
		categoryVO2 = new ProductHierarchyVO();
		categoryVO2.setName("Brand");
		for (int j = 0; j < column2.length; j++) {
			HeaderVO headerVO = new HeaderVO();
			headerVO.setNumber(column2[j]);
			headerVOs2.add(headerVO);
		}
		categoryVO2.setHeaders(headerVOs2);
		productHierarchyVOList.add(categoryVO2);

		ProductHierarchyVO categoryVO3 = new ProductHierarchyVO();
		List<HeaderVO> headerVOs3 = new ArrayList<HeaderVO>();
		int[] column3 = new int[] { 1, 4 };
		categoryVO3 = new ProductHierarchyVO();
		categoryVO3.setName("Subbrand");
		for (int j = 0; j < column3.length; j++) {
			HeaderVO headerVO = new HeaderVO();
			headerVO.setNumber(column3[j]);
			headerVOs3.add(headerVO);
		}
		categoryVO3.setHeaders(headerVOs3);
		productHierarchyVOList.add(categoryVO3);

		ProductHierarchyVO categoryVO4 = new ProductHierarchyVO();
		List<HeaderVO> headerVOs4 = new ArrayList<HeaderVO>();
		int[] column4 = new int[] { 1, 5 };
		categoryVO4 = new ProductHierarchyVO();
		categoryVO4.setName("Size");
		for (int j = 0; j < column4.length; j++) {
			HeaderVO headerVO = new HeaderVO();
			headerVO.setNumber(column4[j]);
			headerVOs4.add(headerVO);
		}
		categoryVO4.setHeaders(headerVOs4);
		productHierarchyVOList.add(categoryVO4);


		ccdMappingVO.setProductHierarchyVOs(productHierarchyVOList);

		SagoAlgo.execute(ccdMappingVO);

	}
}
