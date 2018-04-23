package com.fractal.concordia.vo;

import java.util.ArrayList;
import java.util.List;

public class Varient extends DataVO {

	private List<VarientRowVO> varientRow;

	public List<VarientRowVO> getVarientRow() {
		if (varientRow == null) {
			varientRow = new ArrayList<VarientRowVO>();
		}
		return varientRow;
	}

	public void setVarientRow(List<VarientRowVO> varientRow) {
		this.varientRow = varientRow;
	}




}
