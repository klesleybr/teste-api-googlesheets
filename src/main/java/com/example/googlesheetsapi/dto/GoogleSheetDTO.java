package com.example.googlesheetsapi.dto;

import java.util.List;

public class GoogleSheetDTO {
	private String sheetName;
	
	private List<List<Object>> dataToBeUpdate;
	
	public List<List<Object>> getDataToBeUpdate(){
		return dataToBeUpdate;
	}
	public void setDataToBeUdpdate(List<List<Object>> dataToBeUpdate) {
		this.dataToBeUpdate = dataToBeUpdate;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
