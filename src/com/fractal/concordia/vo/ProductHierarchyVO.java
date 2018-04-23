package com.fractal.concordia.vo;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ProductHierarchyVO extends DataVO {

	private String name;
	// private List<String> varients;
	private List<Varient> varients;
	private List<HeaderVO> headers;
	private String headerString;
	private Integer perfectMatchCount;
	private Integer patternMatchCount;
	private Integer noMatchCount;
	private Integer totalCount;
	private Integer maxVariants;
	private List<Integer> maxList;
	private byte[] excelData;
	private String comments;
	private String version;

	private Blob excelTest;

	public Blob getExcelTest() {
		return excelTest;
	}

	public void setExcelTest(Blob excelTest) {
		this.excelTest = excelTest;
	}

	private Boolean isSelectedForExecution;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public List<String> getVarients() {
	// return varients;
	// }
	//
	// public void setVarients(List<String> varients) {
	// this.varients = varients;
	// }
	public List<HeaderVO> getHeaders() {
		if (headers == null) {
			headers = new ArrayList<HeaderVO>();
		}
		return headers;
	}

	public void setHeaders(List<HeaderVO> headers) {
		this.headers = headers;
	}

	public List<Varient> getVarients() {
		if (varients == null) {
			varients = new ArrayList<Varient>();
		}
		return varients;
	}

	public void setVarients(List<Varient> varients) {
		this.varients = varients;
	}

	public Boolean getIsSelectedForExecution() {
		if (isSelectedForExecution == null) {
			isSelectedForExecution = new Boolean(false);
		}
		return isSelectedForExecution;
	}

	public void setIsSelectedForExecution(Boolean isSelectedForExecution) {
		this.isSelectedForExecution = isSelectedForExecution;
	}

	public String getHeaderString() {

		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	public Integer getPerfectMatchCount() {

		return perfectMatchCount;
	}

	public void setPerfectMatchCount(Integer perfectMatchCount) {
		this.perfectMatchCount = perfectMatchCount;
	}

	public Integer getPatternMatchCount() {

		return patternMatchCount;
	}

	public void setPatternMatchCount(Integer patternMatchCount) {
		this.patternMatchCount = patternMatchCount;
	}

	public Integer getNoMatchCount() {

		return noMatchCount;
	}

	public void setNoMatchCount(Integer noMatchCount) {
		this.noMatchCount = noMatchCount;
	}

	public Integer getTotalCount() {

		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getMaxVariants() {
		if (maxVariants == null) {
			maxVariants = 0;
		}
		return maxVariants;
	}

	public void setMaxVariants(Integer maxVariants) {
		this.maxVariants = maxVariants;
	}

	public List<Integer> getMaxList() {
		if (maxList == null) {
			maxList = new ArrayList<Integer>();
		}
		return maxList;
	}

	public void setMaxList(List<Integer> maxList) {
		this.maxList = maxList;
	}

	public byte[] getExcelData() {

		return excelData;
	}

	public void setExcelData(byte[] excelData) {
		this.excelData = excelData;
	}

	public String getComments() {

		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
