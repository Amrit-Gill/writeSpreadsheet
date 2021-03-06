package com.amrit.spreadsheet.writeSpreadsheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Data;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Get;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class spreadSheetWrite {
	
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart.json
     */
    
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS);

        static {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
                DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(1);
            }
        }

        /**
         * Creates an authorized Credential object.
         * @return an authorized Credential object.
         * @throws IOException
         */
        public static Credential authorize() throws IOException {
            // Load client secrets.
            InputStream in =
                SheetsQuickstart.class.getResourceAsStream("/client_secret.json");
            GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(DATA_STORE_FACTORY)
                    .setAccessType("offline")
                    .build();
            Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
            System.out.println(
                    "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
            return credential;
        }

        /**
         * Build and return an authorized Sheets API client service.
         * @return an authorized Sheets API client service
         * @throws IOException
         */
        public static Sheets getSheetsService() throws IOException {
            Credential credential = authorize();
            return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
        
        public static void writeSomething(String id, String writeRange) {

            try {
            	Sheets service = getSheetsService();
            	getDataFromMat dataFromMat = new getDataFromMat();
                List<List<Object>> writeData = dataFromMat.getDataFromMatlab();

                ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
                service.spreadsheets().values()
                        .update(id, writeRange, vr)
                        .setValueInputOption("RAW")
                        .execute();
            } catch (Exception e) {
                System.out.println("Exception");
            }
        }

//        public static List<List<Object>> getData ()  {
//
//        	  List<Object> data1 = new ArrayList<Object>();
//        	  data1.add ("5");
//        	  data1.add ("5");
//        	  data1.add ("5");
//        	  data1.add ("5");
//        	  data1.add ("5");
//        	  data1.add ("5");
//        	  data1.add ("5");
//
//        	  List<List<Object>> data = new ArrayList<List<Object>>();
//        	  data.add (data1);
//
//        	  return data;
//        	}
        
        public static void main(String[] args) throws IOException {
            // Build a new authorized API client service.
            Sheets service = getSheetsService();

            // Prints the names and majors of students in a sample spreadsheet:
            // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
            String spreadsheetId = "1-vFCRHzoH4QoFhqhwJTXQ8ksU_KR7EvDjt-AuvUfZOM";
            String range = "A1";
            
            Spreadsheet sheet = service.spreadsheets().get(spreadsheetId).execute();
            List<Sheet> sheet1 = sheet.getSheets();
            Sheet sheet2 = sheet1.get(0);
            SheetProperties a = sheet2.getProperties();
            int b = a.getSheetId();
            
            DeleteSheetRequest del = new DeleteSheetRequest();
            del.setSheetId(b);
            Request request = new Request();
            request.setDeleteSheet(del);
            BatchUpdateSpreadsheetRequest oRequest = new BatchUpdateSpreadsheetRequest();
            oRequest.setRequests(Arrays.asList(request));
            BatchUpdateSpreadsheetResponse sheet3 = service.spreadsheets().batchUpdate(spreadsheetId, oRequest).execute();
            
            writeSomething(spreadsheetId, range);
            
        }


}
