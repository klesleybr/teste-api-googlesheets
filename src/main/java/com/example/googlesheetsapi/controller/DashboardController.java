package com.example.googlesheetsapi.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.googlesheetsapi.dto.GoogleSheetDTO;
import com.example.googlesheetsapi.service.GoogleSheetsApiService;

@RestController
// @RequestMapping
public class DashboardController {
	@Autowired
	private GoogleSheetsApiService googleSheetsApiService;
	
	@GetMapping("/check") // Serve para indicar onde essa função será mostrada.
	// É só abrir o https://localhost:8080/check
	public String check() {
		return "Testando a API";
	}
	
	@GetMapping("/getData")
	public Map<Object,Object> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
		return googleSheetsApiService.readDataFromGoogleSheet();
	}
	
	@PostMapping("/createSheet")
	public String createGoogleSheets(@RequestBody GoogleSheetDTO request) throws GeneralSecurityException, IOException {
		String urlSheet = googleSheetsApiService.createSheet(request);
		System.out.println("Link da Planilha: " + urlSheet);
		return urlSheet;
	}
	
	
}
