package com.fractal.concordia.utils;


import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.fractal.concordia.vo.HeaderVO;

public class HeaderConverter implements Converter {
	private Map choiceMap;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String label) {
		return choiceMap.get(label);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		HeaderVO headerVO = (HeaderVO) obj;
		String label = headerVO.getName() + " " + headerVO.getNumber();
		return label;
	}

	/**
	 * @param choiceMap
	 *            the choiceMap to set
	 */
	public void setChoiceMap(Map choiceMap) {
		this.choiceMap = choiceMap;
	}

}