package com.fractal.concordia.modules;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.fractal.concordia.vo.CCDMappingVO;

@ManagedBean(name = "ccdLandingBean")
@ViewScoped
public class CCDLandingBean {

	Logger log = Logger.getLogger(CCDLandingBean.class);
	private List<CCDMappingVO> ccdMappingVOs;

	public void fetchccdList() {
		ccdMappingVOs = new CCDMappingBF().fetchCCDList();
	}

	public List<CCDMappingVO> getCcdMappingVOs() {
		if (ccdMappingVOs == null) {
			ccdMappingVOs = new ArrayList<CCDMappingVO>();
		}
		return ccdMappingVOs;
	}

	public void setCcdMappingVOs(List<CCDMappingVO> ccdMappingVOs) {
		this.ccdMappingVOs = ccdMappingVOs;
	}



}
