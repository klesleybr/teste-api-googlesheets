package com.example.googlesheetsapi.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.googlesheetsapi.dto.GoogleSheetDTO;
import com.example.googlesheetsapi.util.GoogleSheetsApiUtil;

@Service
public class GoogleSheetsApiService {
	@Autowired(required = true)
	GoogleSheetsApiUtil googleSheetsApiUtil;
	
	public Map<Object,Object> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
		return googleSheetsApiUtil.getDataFromGoogleSheet();
	}
	
	public String createSheet(GoogleSheetDTO request) throws GeneralSecurityException, IOException {
		return googleSheetsApiUtil.createGoogleSheets(request);
	}

}
