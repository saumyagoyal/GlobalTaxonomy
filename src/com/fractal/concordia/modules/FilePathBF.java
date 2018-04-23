package com.fractal.concordia.modules;

import com.fractal.concordia.common.BaseBF;
import com.fractal.concordia.vo.FilePathVO;

public class FilePathBF extends BaseBF {

	public FilePathVO fetchFilePaths() {
		String query = "ProductHierarchyVO.fetchFilePaths";
		return (FilePathVO) fetchObject(query);
	}

	public void saveFilePaths(FilePathVO filePathVO) throws Exception {
		String query = "";
		if (filePathVO.getId() == null) {
			query = "ProductHierarchyVO.insertFilePaths";
			insertObject(query, filePathVO);
		}
		else {
			query = "ProductHierarchyVO.updateFilePaths";
			updateObject(query, filePathVO);
		}

	}

}
