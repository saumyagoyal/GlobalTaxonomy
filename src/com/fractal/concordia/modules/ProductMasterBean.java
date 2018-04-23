package com.fractal.concordia.modules;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.fractal.concordia.modules.common.ParamBean;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

@ManagedBean(name = "productMasterBean")
@ViewScoped
public class ProductMasterBean {
	Logger log = Logger.getLogger(ProductMasterBean.class);
	@ManagedProperty(value = "#{paramBean}")
	private ParamBean paramBean;
	private List<ProductHierarchyVO> productHierarchyList;
	private CCDMappingVO ccdMappingVO;

	public void fetchProductMasterData(){
		if (getParamBean().getParam1() != null) {
			CCDMappingBF ccdMappingBF = new CCDMappingBF();
			getCcdMappingVO().setId(Integer.parseInt(getParamBean().getParam1()));
			ccdMappingVO = ccdMappingBF.fetchCCDById(ccdMappingVO);
			ProductMasterBF productMasterBF = new ProductMasterBF();
			ccdMappingVO = new CCDMappingBF().fetchCCDById(ccdMappingVO);
			productHierarchyList = productMasterBF.readProductMastersFromGlobalFileTest(ccdMappingVO);
		}
	}

	public void saveProductMaster() {
		ProductHierarchyBF hierarchyBF = new ProductHierarchyBF();
		ccdMappingVO.setProductHierarchyVOs(productHierarchyList);
		try {
			hierarchyBF.saveCategories(ccdMappingVO);
		} catch (Exception e) {
			log.error("Error in saving categories", e);
		}
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

	public List<ProductHierarchyVO> getProductHierarchyList() {
		if (productHierarchyList == null) {
			productHierarchyList = new ArrayList<ProductHierarchyVO>();
		}
		return productHierarchyList;
	}

	public void setProductHierarchyList(List<ProductHierarchyVO> productHierarchyList) {
		this.productHierarchyList = productHierarchyList;
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

}
