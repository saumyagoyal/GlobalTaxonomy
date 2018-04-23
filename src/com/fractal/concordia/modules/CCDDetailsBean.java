package com.fractal.concordia.modules;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fractal.concordia.modules.common.ParamBean;
import com.fractal.concordia.utils.CCDConstants;
import com.fractal.concordia.vo.CCDMappingVO;
import com.fractal.concordia.vo.CategoryMasterVO;
import com.fractal.concordia.vo.CountryMasterVO;
import com.fractal.concordia.vo.DatasourceMasterVO;
import com.fractal.concordia.vo.ProductHierarchyVO;

@ManagedBean(name = "ccdDetailsBean")
@ViewScoped
public class CCDDetailsBean {
	Logger log = Logger.getLogger(CCDDetailsBean.class);
	@ManagedProperty(value = "#{paramBean}")
	private ParamBean paramBean;
	private CCDMappingVO ccdMappingVO;
	private List<CountryMasterVO> countryMasterList;
	private List<CategoryMasterVO> categoryMasterList;
	private List<DatasourceMasterVO> datasourceMasterList;
	private Boolean isValid;

	public void fetchccdDetails() {
		fetchMasterData();
		if (getParamBean().getParam1() != null) {
			// .setId(Integer.parseInt(EncoderAndDecoderUtil.decode(paramBean.getParam1(), "PBEWithMD5AndDES")));
			getCcdMappingVO().setId(Integer.parseInt(paramBean.getParam1()));
			ccdMappingVO = new CCDMappingBF().fetchCCDById(ccdMappingVO);
		}
	}

	public void fetchMasterData() {
		Map<Integer, Object> parent = new CCDMappingBF().fetchMasterData();
		countryMasterList = (ArrayList<CountryMasterVO>) parent.get(CCDConstants.COUNTRY_MASTER);
		categoryMasterList = (ArrayList<CategoryMasterVO>) parent.get(CCDConstants.CATEGORY_MASTER);
		datasourceMasterList = (ArrayList<DatasourceMasterVO>) parent.get(CCDConstants.DATASOURCE_MASTER);
	}

	public void saveccdDetails() throws Exception {

		isValid = true;
		// validations
		if ((ccdMappingVO.getGlobalFileName() == null)
				|| "".equalsIgnoreCase(ccdMappingVO.getGlobalFileName().trim())) {
			// error
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Global file name is mandatory", null));
		}
		if ((ccdMappingVO.getLocalFileName() == null) || "".equalsIgnoreCase(ccdMappingVO.getLocalFileName().trim())) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Local file name is mandatory", null));
		}
		if ((ccdMappingVO.getCountryMasterVO() == null) || (ccdMappingVO.getCountryMasterVO().getId() == null)
				|| (ccdMappingVO.getCountryMasterVO().getId() == 0)) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Country is mandatory", null));
		}
		if ((ccdMappingVO.getCategoryMasterVO() == null) || (ccdMappingVO.getCategoryMasterVO().getId() == null)
				|| (ccdMappingVO.getCategoryMasterVO().getId() == 0)) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Category is mandatory", null));
		}
		if ((ccdMappingVO.getDatasourceMasterVO() == null) || (ccdMappingVO.getDatasourceMasterVO().getId() == null)
				|| (ccdMappingVO.getDatasourceMasterVO().getId() == 0)) {
			isValid = false;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datasource is mandatory", null));
		}

		if (isValid) {
			try {
				new CCDMappingBF().saveCCD(ccdMappingVO);
			} catch (Exception e) {
				log.error("Error in saving country category datasouce", e);
			}
		}


		saveFile(ccdMappingVO);
		readFile(ccdMappingVO);
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

	public List<CountryMasterVO> getCountryMasterList() {
		if (countryMasterList == null) {
			countryMasterList = new ArrayList<CountryMasterVO>();
		}
		return countryMasterList;
	}

	public void setCountryMasterList(List<CountryMasterVO> countryMasterList) {
		this.countryMasterList = countryMasterList;
	}

	public List<CategoryMasterVO> getCategoryMasterList() {
		if (categoryMasterList == null) {
			categoryMasterList = new ArrayList<CategoryMasterVO>();
		}
		return categoryMasterList;
	}

	public void setCategoryMasterList(List<CategoryMasterVO> categoryMasterList) {
		this.categoryMasterList = categoryMasterList;
	}

	public List<DatasourceMasterVO> getDatasourceMasterList() {
		if (datasourceMasterList == null) {
			datasourceMasterList = new ArrayList<DatasourceMasterVO>();
		}
		return datasourceMasterList;
	}

	public void setDatasourceMasterList(List<DatasourceMasterVO> datasourceMasterList) {
		this.datasourceMasterList = datasourceMasterList;
	}

	public ParamBean getParamBean() {
		return paramBean;
	}

	public void setParamBean(ParamBean paramBean) {
		this.paramBean = paramBean;
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

	public void saveFile(CCDMappingVO ccdMappingVO) {

		// String url =
		// "jdbc:mysql://localhost:3306/concordiaui?zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true&amp;autoCommit=false";
		// "jdbc:mysql://localhost:3306/contactdb";
		// String user = "root";
		// String password = "1qaz2wsx";

		String filePath = ccdMappingVO.getGlobalFile(); // "C://Mapping//CPW//Global-Indo(CPW).xlsx";
		InputStream inputStream = null;

		try {
			// Connection conn = DriverManager.getConnection(url, user, password);
			inputStream = new FileInputStream(new File(filePath));
			byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(inputStream);
			// Blob b = Blob.class.cast(new File(filePath));

			// Blob b1 = null;
			// b1 = new SerialBlob(IOUtils.toByteArray(inputStream));

			CCDMappingBF ccdMappingBF = new CCDMappingBF();

			ProductHierarchyVO hierarchyVO = new ProductHierarchyVO();
			// inputStream.read(b);
			hierarchyVO.setExcelData(bytes);
			hierarchyVO.setComments("Test");
			hierarchyVO.setVersion("v1.0");
			// hierarchyVO.setExcelTest(b1);
			ccdMappingBF.saveExcel(hierarchyVO);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("error in closing local input file", e);
				}
			}

		}

	}

	public void readFile(CCDMappingVO ccdMappingVO) throws Exception {

		// String url =
		// "jdbc:mysql://localhost:3306/concordiaui?zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true&amp;autoCommit=false";
		// "jdbc:mysql://localhost:3306/contactdb";
		// String user = "root";
		// String password = "1qaz2wsx";
		// ResultSet resultSet = null;

		String newname = "_" + (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")).format(new java.util.Date()) + ".xlsx";
		String filePath = ccdMappingVO.getGlobalFile().replace(".xlsx", newname);
		// "C://Mapping//CPW//Global-Indo(CPW)" + (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"))+ ".xlsx";
		// FileOutputStream fileOut = null;

		InputStream inputStream = null;
		// PreparedStatement statement = null;

		try {
			// Connection conn = DriverManager.getConnection(url, user, password);

			// String sql = "select Excel from inventory where id=1";
			// statement = conn.prepareStatement(sql);
			// statement.setString(1, "1");
			// inputStream = new FileInputStream(new File(filePath));
			// statement.setBlob(1, inputStream);

			// resultSet = statement.executeQuery();

			CCDMappingBF ccdMappingBF = new CCDMappingBF();
			ProductHierarchyVO hierarchyVO = new ProductHierarchyVO();


			hierarchyVO = ccdMappingBF.getExcel(hierarchyVO);

			System.out.println(hierarchyVO.getExcelData());

			// while (resultSet.next()) {
			byte[] bytes = hierarchyVO.getExcelData();
			// resultSet.getBlob("Excel");
			inputStream = new ByteArrayInputStream(bytes);
			// blob.getBinaryStream();
			XSSFWorkbook globalworkbook = new XSSFWorkbook(inputStream);
			FileOutputStream fileOut = new FileOutputStream(new File(filePath));
			globalworkbook.write(fileOut);
			fileOut.close();
			// }

			// conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} /*
		 * finally { if (inputStream != null) { try { inputStream.close(); } catch (IOException e) {
		 * log.error("error in closing local input file", e); } }
		 *
		 * }
		 */
	}
}
