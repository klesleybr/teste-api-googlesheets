package com.example.googlesheetsapi.util;

// Adicionar o arquivo credentials.json em src/main/resources
// Vídeo: https://youtu.be/4mP3Fsi4hio

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.googlesheetsapi.dto.GoogleSheetDTO;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;

@Component
public class GoogleSheetsApiUtil {
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	
	private static final List<String> SCOPES =
			Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) 
			throws IOException{
		InputStream in = GoogleSheetsApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if(in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		
		GoogleClientSecrets clientSecrets = 
				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(System.getProperty("user.home"), TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline").build();
		
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
	
	/**
	   * Prints the names and majors of students in a sample spreadsheet:
	   * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	*/
	
	public Map<Object,Object> getDataFromGoogleSheet() throws GeneralSecurityException, IOException {		
		final String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
		final String range = "Class Data!A2:E";
		Sheets service = getSheetService();
		ValueRange response = service.spreadsheets().values()
				.get(spreadsheetId, range)
				.execute();
		List<List<Object>> values = response.getValues();
		
		Map<Object,Object> storedDataFromGoogleSheets = new HashMap<>();
		
		if(values == null || values.isEmpty()) {
			System.out.println("No data found.");
		} else {
			System.out.println("Name, Major");
			for (List row : values) {
				storedDataFromGoogleSheets.put(row.get(0), row.get(4));
				
				// Print columns A and E, which correspond to indices 0 and 4.
				System.out.printf("%s, %s\n", row.get(0), row.get(4));
			}			
		}
		return storedDataFromGoogleSheets;
	}
	
	// Como esta ação será usada por dois métodos, o melhor é abstraí-lo em um método private
	// que pode ser usado em qual outra função desta classe.
	private Sheets getSheetService() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Sheets service = 
				new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME)
				.build();
		return service;
	}

	// Criação de uma planilha....
	public String createGoogleSheets(GoogleSheetDTO request) throws GeneralSecurityException, IOException {
		Sheets service = getSheetService();
		
		// Planilha
		SpreadsheetProperties spreadsheetProperties = new SpreadsheetProperties();
		spreadsheetProperties.setTitle(request.getSheetName());
		
		// Folha
		SheetProperties sheetProperties = new SheetProperties();
		sheetProperties.setTitle(request.getSheetName());
		
		Sheet sheet = new Sheet().setProperties(sheetProperties);
		
		Spreadsheet spreadsheet = new Spreadsheet().setProperties(spreadsheetProperties).setSheets(Collections.singletonList(sheet));
		
		// ValueRange valueRange = new ValueRange().setValues(request.getDataToBeUpdate());
		// service.spreadsheets().values().update(spreadsheet.getSpreadsheetId(), "A1", valueRange);
		// update(spreadsheetId, range, content);
		
		return service.spreadsheets().create(spreadsheet).execute().getSpreadsheetUrl();	
	}
	
	/*public static void main(String[] args) throws GeneralSecurityException, IOException {
		
	}*/
}

/*Caused by: java.lang.IllegalStateException: You are currently running with version 2.7.0 
 * of google-api-client. You need at least version 1.15 of google-api-client to run 
 * version 1.18.0-rc of the Google Sheets API library.
 * 
 * */
