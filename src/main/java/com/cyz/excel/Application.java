package com.cyz.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cyz.excel.entity.ResultExcel;
import com.cyz.excel.read.Conversion;
import com.cyz.excel.read.ReadExcel;

public class Application {
	
	public static void main(String[] args) {
	    ReadExcel excel = ReadExcel.getInstance();
	    ResultExcel result = excel.readXlsx("D:"+File.separator+"�ĵ�"+File.separator+"�㶫ʡͶ��ƽ̨"+File.separator+"�����嵥"+File.separator+"A.�г�_���ݿ�ӳ��.xlsx", 0, 0, 6);
	    result.getResult().forEach(o->System.out.println(o.keySet()));
	    List<AA> aas = new ArrayList<Application.AA>();
	    Conversion con = new Conversion("projectNum", "forb", "forbDes", "subProjectNum", "id", "sort");
	    result.getResult().forEach(o-> aas.add(con.mapperObj(o, AA.class)));
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
