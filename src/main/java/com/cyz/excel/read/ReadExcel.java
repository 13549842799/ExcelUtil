package com.cyz.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 
 * @author cyz
 *
 */
public final class ReadExcel {
	
	public static volatile ReadExcel readExcel;
	
	private ReadExcel() {}
	
	public static ReadExcel getInstance() {
		if (readExcel == null) {
			synchronized (ReadExcel.class) {
				if (readExcel == null) {
					readExcel = new ReadExcel();
				}
			}			
		}
		return readExcel;
	}
	
	public boolean isXlsx(String path) {
		return this.checkType(path, "xlsx");
	}
	
	private boolean checkType(String path, String type) {
		int lastIndex = path.lastIndexOf(".");
		return lastIndex > -1 && path.substring(lastIndex + 1).equals(type);
	}
	
	public List<Map<String, String>> readToList(String path, int shell, int titleRow, int titleLength) {
		 if (this.isXlsx(path)) {
			 return readXlsx(path, shell, titleRow, titleLength);
		 }
		 return readXls(path, shell, titleRow, titleLength);
	}
	
	/**
	 * the method do for which file's file-extensions is xlsx
	 * @param path
	 * @param shell
	 * @param titleRow
	 * @param titleLength
	 * @return
	 */
	public List<Map<String, String>> readXlsx(String path, int shell, int titleRow, int titleLength) {
		
		File xlsFile = new File(path);

        // 获得工作簿
		try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile))){
			// 获得工作表
	        XSSFSheet sheet = workbook.getSheetAt(shell);
	        return createList(titleRow, titleLength, sheet);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}//xlsx
	}
	
	/**
	 * the method do for which file's file-extensions is xls
	 * @param path xls path
	 * @param shell the shell index
	 * @param titleRow
	 * @param titleLength
	 * @return
	 */
	public List<Map<String, String>> readXls(String path, int shell, int titleRow, int titleLength) {
		File xlsFile = new File(path);

        // get workbook
		try (HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsFile))){
			// get sheet
	        HSSFSheet sheet = workbook.getSheetAt(shell);
	        return createList(titleRow, titleLength, sheet);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}//xls
	}
	/**
	 * first, get the titlerow by the titleRow param,execute the createTile method to accept title array, 
	 * get the rows which include data from the sheet， and circulate then， put all data with title to map.
	 * @param titleRow  the title which title in， the index is starting from zero
	 * @param titleLength  the length of title， it mean how many title in a row
	 * @param sheet
	 * @return
	 */
	public List<Map<String, String>> createList(int titleRow, int titleLength, Sheet sheet) {
		int rows = sheet.getPhysicalNumberOfRows();
		String[] titleStr = createTitle(sheet.getRow(titleRow), titleLength);
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = titleRow + 1; i < rows; i++) {
		    //  get data from the row whick index is i
		    list.add(readTextToMap(sheet.getRow(i), titleStr));
		}
		return list;
	}
	
	/**
	 * we accept the row param and titleLength, circle from zero to titleLength,
	 * when the cell is not null,put the value of cell to title array by toString method.
	 * you should pay attention to the value, it check the null and trim before pay to title array
	 * @param row
	 * @param titleLength
	 * @return
	 */
	public String[] createTitle(Row row, int titleLength) {
		String[] titleStr = new String[titleLength];
        for(int i = 0; i<titleLength;i++) {
        	if (row.getCell(i) == null) {
        		continue;
        	}
        	String val = row.getCell(i).toString();
        	titleStr[i] = val == null || val.trim().length() == 0 ? String.valueOf(i) : val; 
        }
        return titleStr;
	}
	
	public Map<String, String> readTextToMap(Row row, String[] titleStr) {
        Map<String, String> map = new HashMap<>();
        for (int j = 0;j < titleStr.length; j++) {
        	Cell cell = row.getCell(j);
        	if (cell == null) {
        		continue;
        	}
        	map.put(titleStr[j], cell.toString());	     
        }
        return map;
	}
}
