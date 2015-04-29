package com.project.dto;

import java.util.HashMap;

public class GraphData {
	
	private String xName;
	private String yName;
	private HashMap<String, String> xData;
	private HashMap<String, String> yData;
	
	public String getxName() {
		return xName;
	}
	public void setxName(String xName) {
		this.xName = xName;
	}
	public String getyName() {
		return yName;
	}
	public void setyName(String yName) {
		this.yName = yName;
	}
	public HashMap<String, String> getxData() {
		return xData;
	}
	public void setxData(HashMap<String, String> xData) {
		this.xData = xData;
	}
	public HashMap<String, String> getyData() {
		return yData;
	}
	public void setyData(HashMap<String, String> yData) {
		this.yData = yData;
	}

}
