package com.cyz.excel.read;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.cyz.excel.entity.DataMap;

/**
 * the class do for creating obj by the list which generic paradigm is <Map<String, String>>
 * @author cyz
 *
 */
public class Conversion {
	
    /**
     * you can set the bean'propertieNames same as excel’titles,that adapter is true,
     * so you don't need to precent the properties and titles
     */
	private boolean adapter = false;
	
	/**
	 * the properties is mapper to bean properties
	 */
	private String[] properties;
	
	/**
	 * the titles is mapper to title in excel
	 */
	private String[] titles;
	
	public Conversion(boolean adapter) {
		this.adapter = adapter;
	}
	
	/**
	 * you can create the mapper for bean'properties and excel'titles
	 * @param mapper
	 */
	public Conversion(Map<String, String> mapper) {
		properties = new String[mapper.size()];
		titles = new String[mapper.size()];
		int count = 0;
		for (String key : mapper.keySet()) {
			properties[count] = key;
			titles[count++] = mapper.get(key);
		}
	}
	
	/**
	 * if you only provide the properties， you should ensure the sequence for properties param is same as excel titels
	 * @param propertys
	 */
	public Conversion(String ...properties) {
		this.properties = properties;
	}
	
	/**
	 * the method is on the basis of reflect.
	 * it will read the data value and instance a object as cls
	 * and put the value to it
	 * @param data
	 * @param cls
	 * @return
	 */
	public <T> T mapperObj(DataMap data, Class<T> cls) {
		T t = null;
		Method setMethod = null;
		Class<?> types = null;
		String[] titles = null, properties = null;
		try {
		    t =	cls.newInstance();
		} catch (InstantiationException |IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		titles = this.titles == null ? data.getTitle() : this.titles;
		properties = this.properties == null && this.adapter ? titles : this.properties;
		for (int i = 0; i < properties.length; i++) {
			try {
				setMethod = cls.getMethod(setName(properties[i]), (types = cls.getMethod(getName(properties[i])).getReturnType()));
			} catch (NoSuchMethodException | SecurityException e) {
				System.out.println("找不到属性：" + properties[i]);
				continue;
			}
			
            if (!data.containsKey(titles[i])) {
            	System.out.println("找不到标题：" + titles[i]);
            	continue;
            }           
			Object valObj = adapterType(data.get(titles[i]), types);
			
		    try {
		    	setMethod.invoke(t, valObj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return t;
	}
	
	private Object adapterType(String val, Class<?> type) {
		if (val != null) {
			switch (type.getSimpleName()) {
			case "int":
			case "Integer":
				return (int)Double.parseDouble(val);
			case "String":
				return val;
			case "double":
			case "Double":
				return Double.parseDouble(val);
			}
		}
		return null;
	}
	
	/**
	 * the method do for create the name of set-method
	 * @param propertyName
	 * @return
	 */
	private String setName(String propertyName) {
		return nameMethod(propertyName, "set");
	}
    
	private String getName(String propertyName) {
		return nameMethod(propertyName, "get");
	}
	
	private String nameMethod(String propertyName, String type) {
		if (propertyName == null || propertyName.trim() == "") {
			return "";
		}
		return type + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}
}
