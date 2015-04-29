package com.project.dto;

import java.util.List;

public class GraphData {
	
	private String chartType;
	private String xName;
	private String yName;
	/*private HashMap<String, String> xData;
	private HashMap<String, String> yData;*/
	private List<GraphPlotValueDTO> xData;
	
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
	public List<GraphPlotValueDTO> getxData() {
		return xData;
	}
	public void setxData(List<GraphPlotValueDTO> xData) {
		this.xData = xData;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	

}
