package com.iesfranciscodelosrios.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.iesfranciscodelosrios.ExpressprintApplication;
import com.iesfranciscodelosrios.tools.Credentials;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GoogleDriveService {

    /**
     * Global instance of the JSON factory.
     */
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private Drive service;

    public GoogleDriveService() {

        try {
            service = new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                    .setApplicationName("Expressprint")
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
     *
     * @return An authorized Credential object.
     * @throws IOException              If the file cannot be found.
     * @throws GeneralSecurityException
     */
    private static Credential getCredentials() throws IOException, GeneralSecurityException {
        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(ExpressprintApplication.class.getResourceAsStream("credentials.json")));
        GoogleCredential credential = GoogleCredential.fromStream(ExpressprintApplication.class.getResourceAsStream("expressprint.json"));
        credential.createScoped(Collections.singletonList(DriveScopes.DRIVE))
                .toBuilder()
                .setServiceAccountUser(Credentials.USERMAIL)
                .setClientSecrets(secrets)
                .build().setAccessToken(Credentials.ACCESSTOKEN)
                .setRefreshToken(Credentials.REFRESTOKEN);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    TokenResponse tokenResponse = new GoogleRefreshTokenRequest
                            (new NetHttpTransport(), JSON_FACTORY, Credentials.REFRESTOKEN
                                    , secrets.getDetails().getClientId()
                                    , secrets.getDetails().getClientSecret())
                            .setScopes(Collections.singletonList(DriveScopes.DRIVE)).setGrantType("refresh_token").execute();
                    credential.setAccessToken(tokenResponse.getAccessToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1800000);
        return credential;
    }

    /**
     * Crea en Google Drive una carpeta con el nombre del usuario y la fecha del pedido. Dicha carpeta contiene
     * cada uno de los documentos asociados al pedido
     *
     * @param orderName: Nombre del pedido
     * @param fileList:  Lista de documentos del pedido
     * @return webLinks: HashMap con el id de la carpeta del pedido y la lista de enlaces de cada uno de los documentos
     * que contiene
     * @throws IOException:              Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public LinkedHashMap<String, List<String>> createOrderFolder(String orderName, List<java.io.File> fileList) throws IOException, GeneralSecurityException {
        String folderId = createFolder(orderName);
        LinkedHashMap<String, List<String>> webLinks = populateFolder(fileList, folderId);

        return webLinks;
    }

    /**
     * Crea una carpeta en google Drive dado un nombre.
     *
     * @param name: Nombre de la carpeta
     * @return
     * @throws IOException:              Interrupcion de la comunicacion durante la operacion
     */
    public String createFolder(String name) throws IOException {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        File fileMetadata = new File();
        fileMetadata.setName(name + " " + dateTime);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");


        File file = service.files().create(fileMetadata)
                .setFields("id")
                .execute();


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
     * @throws IOException:              Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public LinkedHashMap<String, List<String>> populateFolder(List<java.io.File> fileList, String folderId) throws IOException, GeneralSecurityException {
        LinkedHashMap<String, List<String>> webLinks = new LinkedHashMap<String, List<String>>();
        List<String> linksList = new ArrayList<String>();

        for (java.io.File file : fileList) {
            File fileMetadata = new File();

            fileMetadata.setName(file.toPath().getFileName().toString());
            fileMetadata.setParents(Collections.singletonList(folderId));

            String mimeType = Files.probeContentType(file.toPath());

            FileContent mediaContent = new FileContent(mimeType, file);
            File gfile = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, name, webViewLink")
                    .execute();

            linksList.add(gfile.getWebViewLink());

        }

        webLinks.put(folderId, linksList);

        return webLinks;
    }

    /**
     * Sube un archivo al directorio raiz del usuario de Google Drive.
     *
     * @param file: El archivo a subir
     * @throws IOException:              Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public void uploadFile(java.io.File file) throws IOException, GeneralSecurityException {
        File fileMetadata = new File();
        fileMetadata.setName(file.toPath().getFileName().toString());

        String mimeType = Files.probeContentType(file.toPath());

        FileContent mediaContent = new FileContent(mimeType, file);
        @SuppressWarnings("unused")
        File gfile = service.files().create(fileMetadata, mediaContent)
                .setFields("id, name, webViewLink")
                .execute();
    }

    /**
     * Comparte y se le da permisos de lectura a un archivo asociado a un usuario distinto al propietario de la cuenta
     * de Google Drive.
     *
     * @param fileId: El archivo que se va a compartir y al que se le va a dar permisos de lectura
     * @param email:  El correo del usuario al que se le van a conceder los permisos
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     * @throws IOException:              Interrupcion de la comunicacion durante la operacion
     */
    public void setPermission(String fileId, String email) throws GeneralSecurityException, IOException {
        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
                throw new IOException(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission, HttpHeaders responseHeaders) {
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
            throw new IOException(e1.getMessage());
        }
    }
}
