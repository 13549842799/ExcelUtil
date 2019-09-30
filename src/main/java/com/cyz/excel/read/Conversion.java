package com.cyz.excel.read;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * the class do for creating obj by the list which generic paradigm is <Map<String, String>>
 * @author cyz
 *
 */
public class Conversion {
	
	/**
	 * the mapper contain the key which mapper to bean and the value which mapper to title in excel
	 */
	private Map<String, String> mapper;
	
	public Conversion(Map<String, String> mapper) {
		this.mapper = mapper;
	}
	
	/**
	 * 
	 * @param data source data
	 * @param cls bean
	 * @return
	 */
	public <T> T mapperObj(Map<String, String> data, Class<T> cls) {
		T t = null;
		try {
		    t =	cls.newInstance();
		} catch (InstantiationException |IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		List<String> noMapperTitle = new LinkedList<>();
		List<String> noHaveTitle = new LinkedList<>();
		Method setMethod = null;
		Method getMethod = null;
		Class<?> types = null;
		Set<String> keys = mapper.keySet();
		for (String property : keys) {			
			try {
				getMethod = cls.getMethod(getName(property));
				types = getMethod.getReturnType();
				String setName = setName(property);
			    if ((setMethod = cls.getMethod(setName, types)) == null) {
			    	noMapperTitle.add(property);
			    	continue;
			    }
			} catch (NoSuchMethodException | SecurityException e) {
				noMapperTitle.add(property);
				e.printStackTrace();
				continue;
			}
			
            String title = mapper.get(property);
            if (!data.containsKey(title)) {
            	noHaveTitle.add(title);
            	continue;
            }           
			String val = data.get(title);
			Object valObj = null;
			if (val != null) {
				switch (types.getSimpleName()) {
				case "int":
				case "Integer":
					valObj = (int)Double.parseDouble(val);
					break;
				case "String":
					valObj = val;
					break;
				case "double":
				case "Double":
					valObj = Double.parseDouble(val);
					break;
				}
			}
		    try {
		    	setMethod.invoke(t, valObj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		System.out.println("noMapper:" + noMapperTitle);
		System.out.println("noHave:" + noHaveTitle);
		return t;
	}
	
	public String setName(String propertyName) {
		return nameMethod(propertyName, "set");
	}
    
	public String getName(String propertyName) {
		return nameMethod(propertyName, "get");
	}
	
	public String nameMethod(String propertyName, String type) {
		if (propertyName == null || propertyName.trim() == "") {
			return "";
		}
		return type + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}
}
