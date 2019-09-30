package com.cyz.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyz.excel.read.Conversion;
import com.cyz.excel.read.ReadExcel;

public class Application {
	
	public static void main(String[] args) {
	    ReadExcel excel = ReadExcel.getInstance();
	    List<Map<String, String>> list = excel.readXlsx("D:"+File.separator+"文档"+File.separator+"广东省投资平台"+File.separator+"负面清单"+File.separator+"A.市场_数据库映射.xlsx", 0, 0, 6);
	    /*for (int i = 0; i <list.size(); i++) {
			System.out.println(list.get(i));
		}*/
	    List<AA> aas = new ArrayList<Application.AA>();
	    Map<String, String> mapper = new HashMap<String, String>();
	    mapper.put("id", "id");
	    mapper.put("projectNum", "项目号");
	    mapper.put("forb", "禁止事项");
	    mapper.put("forbDes", "禁止准入措施描述");
	    mapper.put("subProjectNum", "子项目号");
	    mapper.put("sort", "sort");
	    Conversion con = new Conversion(mapper);
	    list.forEach(o-> aas.add(con.mapperObj(o, AA.class)));
        aas.forEach(o->System.out.println(o));
	}
	
	public static class AA {
		private String id;
		private Integer projectNum;
		private String forb;
		private String forbDes;
		private Integer subProjectNum;
		private Integer sort;
		
		
		public AA() {
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Integer getProjectNum() {
			return projectNum;
		}
		public void setProjectNum(Integer projectNum) {
			this.projectNum = projectNum;
		}
		public String getForb() {
			return forb;
		}
		public void setForb(String forb) {
			this.forb = forb;
		}
		public String getForbDes() {
			return forbDes;
		}
		public void setForbDes(String forbDes) {
			this.forbDes = forbDes;
		}
		public Integer getSubProjectNum() {
			return subProjectNum;
		}
		public void setSubProjectNum(Integer subProjectNum) {
			this.subProjectNum = subProjectNum;
		}
		public Integer getSort() {
			return sort;
		}
		public void setSort(Integer sort) {
			this.sort = sort;
		}
		@Override
		public String toString() {
			return "AA [id=" + id + ", projectNum=" + projectNum + ", forb=" + forb + ", forbDes=" + forbDes
					+ ", subProjectNum=" + subProjectNum + ", sort=" + sort + "]";
		}
		
		
	}

}
