package com.fractal.concordia.modules;

import java.util.List;

import com.fractal.concordia.common.BaseBF;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

public class ProductHierarchyBF extends BaseBF {

	public List<ProductHierarchyVO> fetchCategories(CCDMappingVO ccdMappingVO) {
		String query = "ProductHierarchyVO.fetchCategories";
		return (List<ProductHierarchyVO>) fetchList(query, ccdMappingVO);
	}

	public void saveCategories(CCDMappingVO ccdMappingVO) throws Exception {
		List<ProductHierarchyVO> newPHList = ccdMappingVO.getProductHierarchyVOs();
		// fetch product hierarchy
		List<ProductHierarchyVO> oldPHList = fetchCategories(ccdMappingVO);
		Boolean hasSamePH = false;
		// compare no of tabs & tab names
		if (oldPHList.size() == newPHList.size()) {
			for (ProductHierarchyVO newPH : newPHList) {
				hasSamePH = false;
				for (ProductHierarchyVO oldPH : oldPHList) {
					if (newPH.getName().equalsIgnoreCase(oldPH.getName())) {
						hasSamePH = true;
					}
				}
				if (hasSamePH == false) {
					break;
				}
			}
		} else {
			hasSamePH = false;
		}
		if (hasSamePH == false) {
			String query = "ProductHierarchyVO.insertCategories";
			fetchList(query, ccdMappingVO);
		}

	}

	public void saveCategoryHeaders(ProductHierarchyVO productHierarchyVO) throws Exception {
		String query = "ProductHierarchyVO.insertCategoryHeaders";
		insertObject(query, productHierarchyVO);
	}

}
