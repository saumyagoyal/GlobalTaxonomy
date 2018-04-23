package com.fractal.concordia.vo;

public class updateGlobalFileVO {

	private String globalFilePath;
	private String[] names;
	private String sheetName;

	public String getGlobalFilePath() {
		return globalFilePath;
	}

	public void setGlobalFilePath(String globalFilePath) {
		this.globalFilePath = globalFilePath;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

}
