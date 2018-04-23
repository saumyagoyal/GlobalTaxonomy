/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractal.concordia.mappings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.fractal.concordia.utils.CCDConstants;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.HeaderVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

/**
 *
 * @author saumya.goyal
 */
public class SagoAlgo {

	public static List<ProductHierarchyVO> execute(CCDMappingVO ccdMappingVO) {

		// Start time
		String time1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		SimpleDateFormat timeStamp1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		System.out.println(time1);

		// Get input, output, global file paths
		List<ProductHierarchyVO> categoryVOs = ccdMappingVO.getProductHierarchyVOs();
		String level = "";
		int[] filtercolumn;
		String globalpath = ccdMappingVO.getGlobalFile();
		String main = ccdMappingVO.getLocalFile();
		String outputfile = CCDConstants.ouptup_file_path;

		// Code for time stamped Output file
		/*File from = new File(outputfile);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		String newest = outputfile.replace(".xlsx", timeStamp + ".xlsx");
		File to = new File(newest);
		if (from.renameTo(to)) {
			System.out.println("Renamed");
		} else {
			System.out.println("Error");
		}
		outputfile = newest;
		System.out.println(outputfile);*/

		// Getting all columns required for each and every level of the hierarchy
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < categoryVOs.size(); i++) {

			ProductHierarchyVO mycategoryVO = categoryVOs.get(i);
			List<HeaderVO> myheaderVOs = mycategoryVO.getHeaders();
			for (int j = 0; j < myheaderVOs.size(); j++) {
				HeaderVO myheaderVO = myheaderVOs.get(j);
				set.add(myheaderVO.getNumber());
			}
		}
		// The output file will have all the unique columns selected for each and every level of the hierarchy
		// (Sorted as per input file)
		Iterator<Integer> iterator = set.iterator();
		filtercolumn = new int[set.size()];
		int globalcolumn = 0;
		while (iterator.hasNext()) {
			filtercolumn[globalcolumn++] = iterator.next();
		}
		Arrays.sort(filtercolumn);


		LocalConcatFile.filterFile(main, filtercolumn, outputfile);

		// For every hierarachy
		List<ProductHierarchyVO> categoryVOList = new ArrayList<ProductHierarchyVO>();

		for (int i = 0; i < categoryVOs.size(); i++) {

			ProductHierarchyVO mycategoryVO = categoryVOs.get(i);
			level = mycategoryVO.getName();
			List<HeaderVO> myheaderVOs = mycategoryVO.getHeaders();
			int[] mycolumn = new int[myheaderVOs.size()];
			for (int j = 0; j < myheaderVOs.size(); j++) {
				HeaderVO myheaderVO = myheaderVOs.get(j);
				mycolumn[j] = myheaderVO.getNumber();

			}
			ProductHierarchyVO categoryVO = Calling.taxonomyCalling(level, mycolumn, main, globalpath, outputfile);
			categoryVOList.add(categoryVO);
		}

		System.out.println("Done");

		time1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		SimpleDateFormat timeStamp2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		System.out.println(time1);

		return categoryVOList;
	}

}
