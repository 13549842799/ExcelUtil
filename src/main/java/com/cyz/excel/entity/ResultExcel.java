package com.cyz.excel.entity;

import java.util.List;
/**
 * 
 * @author cyz
 *
 */
public class ResultExcel {
	
	private String[] titles;
	
	private List<DataMap> result;
	
	public ResultExcel(List<DataMap> result, String[] titles) {
		this.result = result;
		this.titles = titles;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public List<DataMap> getResult() {
		return result;
	}

	public void setResult(List<DataMap> result) {
		this.result = result;
	}
	
	

}
