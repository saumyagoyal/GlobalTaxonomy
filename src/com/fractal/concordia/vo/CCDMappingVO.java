package com.fractal.concordia.vo;

import java.util.ArrayList;
import java.util.List;

public class CCDMappingVO extends DataVO {

	private CountryMasterVO countryMasterVO;
	private CategoryMasterVO categoryMasterVO;
	private DatasourceMasterVO datasourceMasterVO;
	private String localFileName;
	private String globalFileName;
	private String localPath;
	private String globalPath;
	private String localFile;
	private String globalFile;

	private List<ProductHierarchyVO> productHierarchyVOs;

	public CountryMasterVO getCountryMasterVO() {
		if (countryMasterVO == null) {
			countryMasterVO = new CountryMasterVO();
		}
		return countryMasterVO;
	}

	public void setCountryMasterVO(CountryMasterVO countryMasterVO) {
		this.countryMasterVO = countryMasterVO;
	}

	public CategoryMasterVO getCategoryMasterVO() {
		if (categoryMasterVO == null) {
			categoryMasterVO = new CategoryMasterVO();
		}
		return categoryMasterVO;
	}

	public void setCategoryMasterVO(CategoryMasterVO categoryMasterVO) {
		this.categoryMasterVO = categoryMasterVO;
	}

	public DatasourceMasterVO getDatasourceMasterVO() {
		if (datasourceMasterVO == null) {
			datasourceMasterVO = new DatasourceMasterVO();
		}
		return datasourceMasterVO;
	}

	public void setDatasourceMasterVO(DatasourceMasterVO datasourceMasterVO) {
		this.datasourceMasterVO = datasourceMasterVO;
	}

	public String getLocalFileName() {

		return localFileName;
	}

	public void setLocalFileName(String localFileName) {
		this.localFileName = localFileName;
	}

	public String getGlobalFileName() {
		return globalFileName;
	}

	public void setGlobalFileName(String globalFileName) {
		this.globalFileName = globalFileName;
	}

	public List<ProductHierarchyVO> getProductHierarchyVOs() {
		if (productHierarchyVOs == null) {
			productHierarchyVOs = new ArrayList<ProductHierarchyVO>();
		}
		return productHierarchyVOs;
	}

	public void setProductHierarchyVOs(List<ProductHierarchyVO> productHierarchyVOs) {
		this.productHierarchyVOs = productHierarchyVOs;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getGlobalPath() {
		return globalPath;
	}

	public void setGlobalPath(String globalPath) {
		this.globalPath = globalPath;
	}

	public String getLocalFile() {
		return getLocalPath() + getLocalFileName();
	}

	public void setLocalFile(String localFile) {
		this.localFile = localFile;
	}

	public String getGlobalFile() {
		return getGlobalPath() + getGlobalFileName();
	}

	public void setGlobalFile(String globalFile) {
		this.globalFile = globalFile;
	}

}
