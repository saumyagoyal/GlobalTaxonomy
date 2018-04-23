package com.fractal.concordia.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutputVO extends DataVO {

	Logger log = Logger.getLogger(OutputVO.class);
	private String produchHierarchyName;
	private List<String> headers;
	private List<ResultMappingVO> greenMappingVOs;
	private List<ResultMappingVO> orangeMappingVOs;
	private List<ResultMappingVO> blueMappingVOs;
	private List<ResultMappingVO> greyMappingVOs;
	private String greenMappingVOJson;
	private String orangeMappingVOJson;
	private String blueMappingVOJson;
	private String greyMappingVOJson;

	public String getProduchHierarchyName() {

		return produchHierarchyName;
	}

	public void setProduchHierarchyName(String produchHierarchyName) {
		this.produchHierarchyName = produchHierarchyName;
	}

	public List<ResultMappingVO> getGreenMappingVOs() {
		if (greenMappingVOs == null) {
			greenMappingVOs = new ArrayList<ResultMappingVO>();
		}
		return greenMappingVOs;
	}

	public void setGreenMappingVOs(List<ResultMappingVO> greenMappingVOs) {
		this.greenMappingVOs = greenMappingVOs;
	}

	public List<ResultMappingVO> getOrangeMappingVOs() {
		if (orangeMappingVOs == null) {
			orangeMappingVOs = new ArrayList<ResultMappingVO>();
		}
		return orangeMappingVOs;
	}

	public void setOrangeMappingVOs(List<ResultMappingVO> orangeMappingVOs) {
		this.orangeMappingVOs = orangeMappingVOs;
	}

	public List<ResultMappingVO> getBlueMappingVOs() {
		if (blueMappingVOs == null) {
			blueMappingVOs = new ArrayList<ResultMappingVO>();
		}
		return blueMappingVOs;
	}

	public void setBlueMappingVOs(List<ResultMappingVO> blueMappingVOs) {
		this.blueMappingVOs = blueMappingVOs;
	}

	public List<ResultMappingVO> getGreyMappingVOs() {
		if (greyMappingVOs == null) {
			greyMappingVOs = new ArrayList<ResultMappingVO>();
		}
		return greyMappingVOs;
	}

	public void setGreyMappingVOs(List<ResultMappingVO> greyMappingVOs) {
		this.greyMappingVOs = greyMappingVOs;
	}

	public String getBlueMappingVOJson() {
		if (blueMappingVOJson == null) {
			try {
				blueMappingVOJson = new ObjectMapper().writeValueAsString(getBlueMappingVOs());
			} catch (JsonProcessingException e) {
				log.error("error in converting into json", e);
			}
		}
		return blueMappingVOJson;
	}

	public String getOrangeMappingVOJson() {
		if (orangeMappingVOJson == null) {
			try {
				orangeMappingVOJson = new ObjectMapper().writeValueAsString(getOrangeMappingVOs());
			} catch (JsonProcessingException e) {
				log.error("error in converting into json", e);
			}
		}
		return orangeMappingVOJson;
	}

	public String getGreyMappingVOJson() {
		if (greyMappingVOJson == null) {
			try {
				greyMappingVOJson = new ObjectMapper().writeValueAsString(getGreyMappingVOs());
			} catch (JsonProcessingException e) {
				log.error("error in converting into json", e);
			}
		}
		return greyMappingVOJson;
	}

	public String getGreenMappingVOJson() {
		if (greenMappingVOJson == null) {
			try {
				greenMappingVOJson = new ObjectMapper().writeValueAsString(getGreenMappingVOs());
			} catch (JsonProcessingException e) {
				log.error("error in converting into json", e);
			}
		}
		return greenMappingVOJson;
	}

	public void setGreenMappingVOJson(String greenMappingVOJson) {
		this.greenMappingVOJson = greenMappingVOJson;
	}

	public void setOrangeMappingVOJson(String orangeMappingVOJson) {
		this.orangeMappingVOJson = orangeMappingVOJson;
	}

	public void setBlueMappingVOJson(String blueMappingVOJson) {
		this.blueMappingVOJson = blueMappingVOJson;
	}

	public void setGreyMappingVOJson(String greyMappingVOJson) {
		this.greyMappingVOJson = greyMappingVOJson;
	}

	public List<String> getHeaders() {
		if (headers == null) {
			headers = new ArrayList<String>();
		}
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

}
