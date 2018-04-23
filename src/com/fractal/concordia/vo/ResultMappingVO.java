package com.fractal.concordia.vo;

import java.util.ArrayList;
import java.util.List;

public class ResultMappingVO extends DataVO {

	private List<String> columnList;

	public List<String> getColumnList() {
		if (columnList == null) {
			columnList = new ArrayList<String>();
		}
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

}
