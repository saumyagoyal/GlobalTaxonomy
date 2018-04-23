package com.fractal.concordia.modules;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.fractal.concordia.vo.FilePathVO;

@ManagedBean(name = "filePathBean")
@ViewScoped
public class FilePathBean {

	Logger log = Logger.getLogger(FilePathBean.class);
	private FilePathVO filePathVO;
	private Boolean isValid;

	public void fetchFilePath() {
		filePathVO = new FilePathBF().fetchFilePaths();
	}

	public void saveFilePath() {

		isValid = true;
		if ((filePathVO.getGlobal() == null) || "".equalsIgnoreCase(filePathVO.getGlobal().trim())) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Global file path is mandatory", null));
		}
		else if ((filePathVO.getGlobal().endsWith("/") == false) && (filePathVO.getGlobal().endsWith("\\") == false)) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Global file path should end with / or \\\\ ", null));
		}
		if ((filePathVO.getLocal() == null) || "".equalsIgnoreCase(filePathVO.getLocal().trim())) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Local file path is mandatory", null));
		}
		else if ((filePathVO.getLocal().endsWith("/") == false) && (filePathVO.getLocal().endsWith("\\") == false)) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Local file path should end with / or \\\\", null));
		}
		if (isValid) {
			try {
				new FilePathBF().saveFilePaths(filePathVO);
			} catch (Exception e) {
				log.error("Error in saving file paths", e);
			}
		}
	}

	public FilePathVO getFilePathVO() {
		if (filePathVO == null) {
			filePathVO = new FilePathVO();
		}
		return filePathVO;
	}

	public void setFilePathVO(FilePathVO filePathVO) {
		this.filePathVO = filePathVO;
	}

	public Boolean getIsValid() {
		if (isValid == null) {
			isValid = Boolean.valueOf(false);
		}
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}
