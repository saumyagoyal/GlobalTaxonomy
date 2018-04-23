package com.fractal.concordia.modules;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fractal.concordia.modules.common.ParamBean;
import com.fractal.concordia.utils.CCDConstants;
import com.fractal.concordia.utils.ExcelUtil;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.OutputVO;
import com.fractal.concordia.vo.updateGlobalFileVO;

@ManagedBean(name = "outputBean")
@ViewScoped
public class OutputBean {
	Logger log = Logger.getLogger(OutputBean.class);
	@ManagedProperty(value = "#{paramBean}")
	private ParamBean paramBean;
	private List<OutputVO> outputVOs;
	private String sheetName;
	private String newWords;
	private List<String> noDataList;

	private String wholeData;
	private Integer selectedColorTab;

	public String getWholeData() {
		if (wholeData == null) {
			try {
				wholeData = new ObjectMapper().writeValueAsString(getOutputVOs());
			} catch (JsonProcessingException e) {
				log.error("error in converting into json", e);
			}
		}
		return wholeData;
	}

	public void setWholeData(String wholeData) {
		this.wholeData = wholeData;
	}

	public void colorChange() {
		// System.out.println(getSelectedColorTab());
	}

	public void resetColorValue() {
		setSelectedColorTab(1);
	}

	public void fetchOutputData() {
		OutputBF outputBF = new OutputBF();
		outputVOs = outputBF.fetchOutputData();
		setSelectedColorTab(1);
	}

	public List<OutputVO> getOutputVOs() {
		if (outputVOs == null) {
			outputVOs = new ArrayList<OutputVO>();
		}
		return outputVOs;
	}

	public String download() {
		ExcelUtil.fileDownload(CCDConstants.ouptup_file_path, "OutputFile.xlsx");
		return null;
	}

	public void addSheetName() {
		System.out.println(sheetName);
	}

	public void updateGlobalSheet() {
		CCDMappingVO ccdMappingVO = new CCDMappingVO();
		ccdMappingVO.setId(Integer.parseInt(getParamBean().getParam1()));
		ccdMappingVO = new CCDMappingBF().fetchCCDById(ccdMappingVO);
		updateGlobalFileVO updateGlobalFileVO = new updateGlobalFileVO();
		updateGlobalFileVO.setGlobalFilePath(ccdMappingVO.getGlobalFile());

	}

	public void setOutputVOs(List<OutputVO> outputVOs) {
		this.outputVOs = outputVOs;
	}

	public ParamBean getParamBean() {
		if (paramBean == null) {
			paramBean = new ParamBean();
		}
		return paramBean;
	}

	public void setParamBean(ParamBean paramBean) {
		this.paramBean = paramBean;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getNewWords() {
		return newWords;
	}

	public void setNewWords(String newWords) {
		this.newWords = newWords;
	}

	public Integer getSelectedColorTab() {
		return selectedColorTab;
	}

	public void setSelectedColorTab(Integer selectedColorTab) {
		this.selectedColorTab = selectedColorTab;
	}

	public List<String> getNoDataList() {
		if (noDataList == null) {
			noDataList = new ArrayList<String>();
			noDataList.add("No Records Found");
		}
		return noDataList;
	}

	public void setNoDataList(List<String> noDataList) {
		this.noDataList = noDataList;
	}

}
