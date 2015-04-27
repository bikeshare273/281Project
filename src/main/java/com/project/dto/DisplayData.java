package com.project.dto;

import java.util.ArrayList;
import java.util.List;

public class DisplayData {
	
	String tableName;
	List<String> col_name;
	List<List<String>> row;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<String> getCol_name() {
		return col_name;
	}
	public void setCol_name(List<String> col_name) {
		this.col_name = col_name;
	}
	public List<List<String>> getRow() {
		return row;
	}
	public void setRow(List<List<String>> row) {
		this.row = row;
	}
}

