package com.iesfranciscodelosrios.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import com.google.api.services.drive.model.Permission;
import com.iesfranciscodelosrios.ExpressprintApplication;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private Drive service;
    
    public GoogleDriveService() {
    	NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME)
					.build();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
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
    
    /**
     * Crea en Google Drive una carpeta con el nombre del usuario y la fecha del pedido. Dicha carpeta contiene
     * cada uno de los documentos asociados al pedido
     * 
     * @param orderName: Nombre del pedido
     * @param fileList: Lista de documentos del pedido
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public void createOrderFolder(String orderName, List<java.io.File> fileList) throws IOException, GeneralSecurityException {
		String folderId = createFolder(orderName);
		populateFolder(fileList, folderId);
		
    }

    /**
     * Crea una carpeta en google Drive dado un nombre.
     * 
     * @param name: Nombre de la carpeta
     * @return
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public String createFolder(String name) throws IOException, GeneralSecurityException {
    	String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    	File fileMetadata = new File();
        fileMetadata.setName(name+" "+dateTime);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        

        File file = service.files().create(fileMetadata)
            .setFields("id")
            .execute();
        System.out.println("Folder ID: " + file.getId());
        
        return file.getId();
    }
    
    /**
     * Rellena una carpeta con una lista de documentos
     * 
     * @param fileList: La lista de documentos
     * @param folderId: Id de la carpeta a rellenar
     * @return webLinks: Lista en formato clave valor con el nombre del documento y la url de drive asociado a el
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public LinkedHashMap<String, String> populateFolder(List<java.io.File> fileList, String folderId) throws IOException, GeneralSecurityException {
    	LinkedHashMap<String, String> webLinks=new LinkedHashMap<String, String>();
    	
    	for (java.io.File file : fileList) {
    		File fileMetadata = new File();
    		
    		fileMetadata.setName(file.toPath().getFileName().toString());
    		fileMetadata.setParents(Collections.singletonList(folderId));
    		
    		String mimeType=Files.probeContentType(file.toPath());
    		
    		FileContent mediaContent = new FileContent(mimeType, file);
    		File gfile = service.files().create(fileMetadata, mediaContent)
    				.setFields("id, name, webViewLink")
    				.execute();
    		
    		/*
    		 * Cuidado! Asegurarse en la vista de que no se le pasa al backend dos documentos con un misma url
    		 * ya que el hashmap usa como clave el nombre y machacaría al anterior insertado con el mismo nombre
    		 */
    		webLinks.put(gfile.getName(), gfile.getWebViewLink());
    		
    		System.out.println("File ID: " + gfile.getId());
    		System.out.println(file);
    	}    	
    	
    	System.out.println("\n"+webLinks.toString());
    	
    	return webLinks;
    }
    
    /**
     * Sube un archivo al directorio raiz del usuario de Google Drive.
     * 
     * @param file: El archivo a subir
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public void uploadFile(java.io.File file) throws IOException, GeneralSecurityException {
    	File fileMetadata = new File();
    	fileMetadata.setName(file.toPath().getFileName().toString());
    	
    	String mimeType=Files.probeContentType(file.toPath());
		
		FileContent mediaContent = new FileContent(mimeType, file);
		File gfile = service.files().create(fileMetadata, mediaContent)
				.setFields("id, name, webViewLink")
				.execute();
		
    	System.out.println("File ID: " + gfile.getId());
    	System.out.println(file);
    }
    
    /**
     * Comparte y se le da permisos de lectura a un archivo asociado a un usuario distinto al propietario de la cuenta 
     * de Google Drive.
     * 
     * @param fileId: El archivo que se va a compartir y al que se le va a dar permisos de lectura
     * @param email: El correo del usuario al que se le van a conceder los permisos
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     */
    public void setPermission(String fileId, String email) throws GeneralSecurityException, IOException {
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
    	    .setEmailAddress(email);
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
