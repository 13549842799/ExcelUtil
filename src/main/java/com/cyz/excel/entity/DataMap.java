package com.cyz.excel.entity;

import java.util.LinkedHashMap;
import java.util.Set;

public class DataMap extends LinkedHashMap<String, String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2979530544559837133L;
	
	public String[] getTitle() {
		Set<String> rs = this.keySet();
		int count = 0;
		String[] titles = new String[rs.size()];
		for (String str : rs) {
			titles[count++] = str;
		}
		return titles;
	}

	public DataMap() {
		super();
	}

	public DataMap(int initialCapacity) {
		super(initialCapacity);
	}

}
