package com.example.googlesheetsapi.dto;

import java.util.List;

public class GoogleSheetDTO {
	private String sheetName;
	
	private List<List<Object>> dataToBeUpdated;
	
	private List<String> emails;
	
	public List<String> getEmails(){
		return emails;
	}
	public void sertEmails(List<String> emails) {
		this.emails = emails;
	}
	
	public List<List<Object>> getDataToBeUpdated(){
		return dataToBeUpdated;
	}
	public void setDataToBeUdpdate(List<List<Object>> dataToBeUpdated) {
		this.dataToBeUpdated = dataToBeUpdated;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
