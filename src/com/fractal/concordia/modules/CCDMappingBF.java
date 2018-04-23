package com.fractal.concordia.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fractal.concordia.common.BaseBF;
import com.fractal.concordia.utils.CCDConstants;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.CategoryMasterVO;
import com.fractal.concordia.vo.CountryMasterVO;
import com.fractal.concordia.vo.DatasourceMasterVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

public class CCDMappingBF extends BaseBF {

	public Map<Integer, Object> fetchMasterData() {
		String query = "ProductHierarchyVO.fetchCCDMasterData";
		List<Object> parentObjects = (List<Object>) fetchList(query);
		List<CountryMasterVO> countryMasterVOs = null;
		List<CategoryMasterVO> categoryMasterVOs = null;
		List<DatasourceMasterVO> datasourceMasterVOs = null;
		if ((parentObjects != null) && !parentObjects.isEmpty()) {

			if ((parentObjects.get(0) != null) && (((List<Object>) parentObjects.get(0)).isEmpty() == false)) {
				countryMasterVOs = (List<CountryMasterVO>) parentObjects.get(0);
			}
			if ((parentObjects.size() > 1) && (parentObjects.get(1) != null)) {
				categoryMasterVOs = (List<CategoryMasterVO>) parentObjects.get(1);
			}
			if ((parentObjects.size() > 2) && (parentObjects.get(2) != null)) {
				datasourceMasterVOs = (List<DatasourceMasterVO>) parentObjects.get(2);
			}
		}

		Map<Integer, Object> result = new HashMap<Integer, Object>();
		result.put(CCDConstants.COUNTRY_MASTER, countryMasterVOs);
		result.put(CCDConstants.CATEGORY_MASTER, categoryMasterVOs);
		result.put(CCDConstants.DATASOURCE_MASTER, datasourceMasterVOs);
		return result;
	}

	public List<CCDMappingVO> fetchCCDList() {
		String query = "ProductHierarchyVO.fetchCCDList";
		return (List<CCDMappingVO>) fetchList(query);
	}

	public CCDMappingVO fetchCCDById(CCDMappingVO ccdMappingVO) {
		String query = "ProductHierarchyVO.fetchCCDById";
		return (CCDMappingVO) fetchObject(query, ccdMappingVO);
	}

	public void saveCCD(CCDMappingVO ccdMappingVO) throws Exception {
		String query = "";
		if (ccdMappingVO.getId() == null) {
			query = "ProductHierarchyVO.insertCCD";
			insertObject(query, ccdMappingVO);
		} else {
			query = "ProductHierarchyVO.updateCCD";
			updateObject(query, ccdMappingVO);
		}
	}

	public void saveExcel(ProductHierarchyVO hierarchyVO) throws Exception {
		String query = "ProductHierarchyVO.saveExcel";
		insertObject(query, hierarchyVO);
	}

	public ProductHierarchyVO getExcel(ProductHierarchyVO hierarchyVO) throws Exception {
		String query = "ProductHierarchyVO.getExcel";
		return (ProductHierarchyVO) fetchObject(query, hierarchyVO);
	}

}
