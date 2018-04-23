package com.fractal.concordia.modules;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fractal.concordia.mappings.SagoAlgo;
import com.fractal.concordia.modules.common.ParamBean;
import com.fractal.concordia.utils.HeaderConverter;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.HeaderVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

@ManagedBean(name = "setupBean")
@ViewScoped
public class SetupBean {
	Logger log = Logger.getLogger(SetupBean.class);
	@ManagedProperty(value = "#{paramBean}")
	private ParamBean paramBean;
	private List<ProductHierarchyVO> productHierarchyList;
	private List<HeaderVO> headerList;
	private String headerString;
	private ProductHierarchyVO productHierarchyVO;
	private HeaderConverter headerConverter;
	private CCDMappingVO ccdMappingVO;
	private Boolean checkBoxParentValue;
	private Boolean isValid;

	public void fetchSetupData() {

		if (getParamBean().getParam1() != null) {
			// check if exists -- db call
			ProductHierarchyBF productHierarchyBF = new ProductHierarchyBF();
			getCcdMappingVO().setId(Integer.parseInt(getParamBean().getParam1()));
			CCDMappingBF ccdMappingBF = new CCDMappingBF();
			ccdMappingVO = ccdMappingBF.fetchCCDById(ccdMappingVO);
			productHierarchyList = productHierarchyBF.fetchCategories(ccdMappingVO);
			SetupBF setupBF = new SetupBF();
			if ((productHierarchyList == null) || (productHierarchyList.isEmpty() == true)) {
				productHierarchyList = setupBF.readCategories(ccdMappingVO);
			}
			headerList = setupBF.readHeaders(ccdMappingVO);
			try {
				headerString = new ObjectMapper().writeValueAsString(headerList);
			} catch (JsonProcessingException e) {
				log.error("error in converting object to string", e);
			}
		}
	}

	public void selectProductHierarchy(ProductHierarchyVO selectedCategory) {
		productHierarchyVO = selectedCategory;
	}

	public void saveHeadersForProductHierarchy() {

		isValid = true;
		if ((headerString == null) || (headerString.length() < 1)) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select at least one header", null));
		}
		if (isValid) {
			List<HeaderVO> selectedHeaders = new ArrayList<HeaderVO>();
			try {
				String[] headNumbers = headerString.split(",");
				for (String string : headNumbers) {
					for (HeaderVO headerVO : headerList) {
						if (headerVO.getNumber() == Integer.parseInt(string)) {
							selectedHeaders.add(headerVO);
						}
					}
				}
				productHierarchyVO.setHeaders(selectedHeaders);
				ProductHierarchyBF productHierarchyBF = new ProductHierarchyBF();
				productHierarchyBF.saveCategoryHeaders(productHierarchyVO);
				productHierarchyList = productHierarchyBF.fetchCategories(ccdMappingVO);
			} catch (Exception e) {
				log.error("Error in saving headers for category: " + productHierarchyVO.getName(), e);
			}
		}
	}

	public void execute() {
		isValid = false;
		List<ProductHierarchyVO> finalHierarchyVOs = new ArrayList<ProductHierarchyVO>();
		for (ProductHierarchyVO productHierarchyVO : productHierarchyList) {
			if (productHierarchyVO.getIsSelectedForExecution()) {
				isValid = true;
				if((productHierarchyVO.getHeaders() != null)
						&& (productHierarchyVO.getHeaders().isEmpty() == false)){
					finalHierarchyVOs.add(productHierarchyVO);
				}
			}
		}
		if (isValid == false) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Select at least one product hierarchy to be executed", null));
		} else {
			if (finalHierarchyVOs.isEmpty() == false) {
				isValid = true;
				ccdMappingVO.setProductHierarchyVOs(finalHierarchyVOs);
				// try {
				List<ProductHierarchyVO> result = SagoAlgo.execute(ccdMappingVO);
				for (ProductHierarchyVO productHierarchyVO : result) {
					System.out.println(productHierarchyVO.getName());
					System.out.println(productHierarchyVO.getPerfectMatchCount());
					System.out.println(productHierarchyVO.getPatternMatchCount());
					System.out.println(productHierarchyVO.getNoMatchCount());
					System.out.println(productHierarchyVO.getTotalCount());

				}
				// } catch (IOException | EncoderException e) {
				// log.error("Error in execute", e);
				// }
			}
			else {
				isValid = false;
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Select headers for selected product hierarchies", null));
			}
		}

	}


	public List<HeaderVO> getHeaderList() {
		if (headerList == null) {
			headerList = new ArrayList<HeaderVO>();
		}
		return headerList;
	}

	public void setHeaderList(List<HeaderVO> headerList) {
		this.headerList = headerList;
	}



	public HeaderConverter getHeaderConverter() {
		if (headerConverter == null) {
			headerConverter = new HeaderConverter();
		}
		return headerConverter;
	}

	public void setHeaderConverter(HeaderConverter headerConverter) {
		this.headerConverter = headerConverter;
	}

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
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

	public CCDMappingVO getCcdMappingVO() {
		if (ccdMappingVO == null) {
			ccdMappingVO = new CCDMappingVO();
		}
		return ccdMappingVO;
	}

	public void setCcdMappingVO(CCDMappingVO ccdMappingVO) {
		this.ccdMappingVO = ccdMappingVO;
	}

	public List<ProductHierarchyVO> getProductHierarchyList() {
		if (productHierarchyList == null) {
			productHierarchyList = new ArrayList<ProductHierarchyVO>();
		}
		return productHierarchyList;
	}

	public void setProductHierarchyList(List<ProductHierarchyVO> productHierarchyList) {
		this.productHierarchyList = productHierarchyList;
	}

	public ProductHierarchyVO getProductHierarchyVO() {
		if (productHierarchyVO == null) {
			productHierarchyVO = new ProductHierarchyVO();
		}
		return productHierarchyVO;
	}

	public void setProductHierarchyVO(ProductHierarchyVO productHierarchyVO) {
		this.productHierarchyVO = productHierarchyVO;
	}

	public Boolean getCheckBoxParentValue() {
		if (checkBoxParentValue == null) {
			checkBoxParentValue = Boolean.valueOf(false);
		}
		return checkBoxParentValue;
	}

	public void setCheckBoxParentValue(Boolean checkBoxParentValue) {
		this.checkBoxParentValue = checkBoxParentValue;
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
