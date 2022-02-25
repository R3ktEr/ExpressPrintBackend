package com.iesfranciscodelosrios.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStore;
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

@Service
public class GoogleDriveService {
	
	/** Application name. */
    private static final String APPLICATION_NAME = "Expressprint";
    /** Global instance of the JSON factory. */
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/com/iesfranciscodelosrios/tokens";
    
    private static final String REFRESH_TOKEN="1//04PBrfp9CnhiuCgYIARAAGAQSNwF-L9IrtcJSZYNmpLFVvc5LCXZSiFBXDv2k6AwjY6Nll6Yf_ctdOgUqmWZCkwtTZOxdqMiwpZ8";

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
     * @throws GeneralSecurityException 
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, GeneralSecurityException {
    	 // Load client secrets.
        InputStream in = ExpressprintApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore("StoredCredential");
        
        datastore.values().forEach(x -> x.setRefreshToken(REFRESH_TOKEN));
        datastore.values().forEach(x -> x.setExpirationTimeMilliseconds(null));
        
        datastore.values().forEach(x -> System.out.println("Token Expiration time: "+x.getExpirationTimeMilliseconds()));
        
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        		.setTokenServerUrl(new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setCredentialDataStore(datastore)
                .setAccessType("offline")
                .setApprovalPrompt(null)
                .build();
        
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
        		.setRefreshToken(REFRESH_TOKEN);
        
        StoredCredential storedCredentials=new StoredCredential(credential);
        storedCredentials.setRefreshToken(REFRESH_TOKEN);
        storedCredentials.setExpirationTimeMilliseconds(null); 
        
        //returns an authorized Credential object.
   
        return credential;
    }
    
    /*
    private static Credential createCredentialWithRefreshToken(
    	    HttpTransport transport,
    	    JsonFactory jsonFactory,
    	    TokenResponse tokenResponse) {
    	  String clientId = "921786245072-15raqampr57qi34d282ts8oevih9okcr.apps.googleusercontent.com";
    	  String clientSecret = "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";
    	  return new Credential.Builder(BearerToken.authorizationHeaderAccessMethod()).setTransport(
    	      transport)
    	      .setJsonFactory(jsonFactory)
    	      .setTokenServerUrl(
    	          new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL))
    	      .setClientAuthentication(new BasicAuthentication(
    	          clientId,
    	          clientSecret))
    	      .build()
    	      .setFromTokenResponse(tokenResponse);
    	  //tokenResponse.setRefreshToken(refreshToken)
    	}*/
    
    /**
     * Crea en Google Drive una carpeta con el nombre del usuario y la fecha del pedido. Dicha carpeta contiene
     * cada uno de los documentos asociados al pedido
     * 
     * @param orderName: Nombre del pedido
     * @param fileList: Lista de documentos del pedido
     * @return webLinks: HashMap con el id de la carpeta del pedido y la lista de enlaces de cada uno de los documentos
     * que contiene
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public LinkedHashMap<String, List<String>> createOrderFolder(String orderName, List<java.io.File> fileList) throws IOException, GeneralSecurityException {
		String folderId = createFolder(orderName);
		LinkedHashMap<String, List<String>> webLinks=populateFolder(fileList, folderId);
		
		return webLinks;
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
        
        //System.out.println("Folder ID: " + file.getId());
        
        return file.getId();
    }
    
    /**
     * Borra una carpeta por id
     * 
     * @param folderId: Id de la carpeta
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     */
    public void deleteFolder(String folderId) throws IOException {
    	service.files().delete(folderId);
    }
    
    /**
     * Rellena una carpeta con una lista de documentos
     * 
     * @param fileList: La lista de documentos
     * @param folderId: Id de la carpeta a rellenar
     * @return webLinks: HashMap con el id de la carpeta del pedido y la lista de enlaces de cada uno de los documentos
     * que contiene
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public LinkedHashMap<String, List<String>> populateFolder(List<java.io.File> fileList, String folderId) throws IOException, GeneralSecurityException {
    	LinkedHashMap<String, List<String>> webLinks=new LinkedHashMap<String, List<String>>();
    	List<String> linksList= new ArrayList<String>();
    	
    	for (java.io.File file : fileList) {
    		File fileMetadata = new File();
    		
    		fileMetadata.setName(file.toPath().getFileName().toString());
    		fileMetadata.setParents(Collections.singletonList(folderId));
    		
    		String mimeType=Files.probeContentType(file.toPath());
    		
    		FileContent mediaContent = new FileContent(mimeType, file);
    		File gfile = service.files().create(fileMetadata, mediaContent)
    				.setFields("id, name, webViewLink")
    				.execute();
    		
    		linksList.add(gfile.getWebViewLink());
    		
    		//System.out.println("File ID: " + gfile.getId());
    		//System.out.println(file);
    	}
    	
    	webLinks.put(folderId, linksList);
    	
    	//System.out.println("\n"+webLinks.toString());
    	
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
		@SuppressWarnings("unused")
		File gfile = service.files().create(fileMetadata, mediaContent)
				.setFields("id, name, webViewLink")
				.execute();
		
    	//System.out.println("File ID: " + gfile.getId());
    	//System.out.println(file);
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
    	    //System.err.println(e.getMessage());
    		  throw new IOException(e.getMessage());
    	  }

    	  @Override
    	  public void onSuccess(Permission permission,
    	                        HttpHeaders responseHeaders)
    	      throws IOException {
    	    //System.out.println("Permission ID: " + permission.getId());
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
			throw new IOException(e1.getMessage());
		}
    }
}
