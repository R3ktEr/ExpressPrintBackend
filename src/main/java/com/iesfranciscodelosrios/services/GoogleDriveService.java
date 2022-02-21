package com.iesfranciscodelosrios.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.iesfranciscodelosrios.ExpressprintApplication;

import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

public class GoogleDriveService {
	
	/** Application name. */
    private static final String APPLICATION_NAME = "Expressprint";
    /** Global instance of the JSON factory. */
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/com/iesfranciscodelosrios/tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ExpressprintApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public static void createFolder() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        fileMetadata.setName("Prueba");
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = service.files().create(fileMetadata)
            .setFields("id")
            .execute();
        System.out.println("Folder ID: " + file.getId());
    }
    
    public static void uploadFile() throws IOException, GeneralSecurityException {
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

    	File fileMetadata = new File();
    	fileMetadata.setName("mierdadesage.pdf");
    	
    	java.io.File filePath = new java.io.File("mierdadesage.pdf");
    	FileContent mediaContent = new FileContent("application/pdf", filePath);
    	File file = service.files().create(fileMetadata, mediaContent)
    	    .setFields("id, webViewLink")
    	    .execute();
    	System.out.println("File ID: " + file.getId());
    	System.out.println(file);
    }
    
    public static void readFiles() throws IOException, GeneralSecurityException {
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String pageToken = null;
        do {
          FileList result = service.files().list()
              .setQ("mimeType='image/jpeg'")
              .setSpaces("drive")
              .setFields("nextPageToken, files(id, name)")
              .setPageToken(pageToken)
              .execute();
          for (File file : result.getFiles()) {
            System.out.printf("Found file: %s (%s)\n",
                file.getName(), file.getId());
          }
          pageToken = result.getNextPageToken();
        } while (pageToken != null);
    }
    
    public static void setPermission() throws GeneralSecurityException, IOException {
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
    	String fileId = "1FOTJESBaA8xj2DK_x_WLCcZ5HQNBFy3n";
    	JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
    	  @Override
    	  public void onFailure(GoogleJsonError e,
    	                        HttpHeaders responseHeaders)
    	      throws IOException {
    	    // Handle error
    	    System.err.println(e.getMessage());
    	  }

    	  @Override
    	  public void onSuccess(Permission permission,
    	                        HttpHeaders responseHeaders)
    	      throws IOException {
    	    System.out.println("Permission ID: " + permission.getId());
    	  }
    	};
    	BatchRequest batch = service.batch();
    	Permission userPermission = new Permission()
    	    .setType("user")
    	    .setRole("reader")
    	    .setEmailAddress("alvaroperezab@gmail.com");
    	try {
			service.permissions().create(fileId, userPermission)
			    .setFields("id")
			    .queue(batch, callback);
			
			batch.execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }
}
