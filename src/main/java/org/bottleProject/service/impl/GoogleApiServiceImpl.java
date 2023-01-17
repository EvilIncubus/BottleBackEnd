package org.bottleProject.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.bottleProject.service.GoogleApiService;
import org.bottleProject.util.PropertiesReader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;
public class GoogleApiServiceImpl implements GoogleApiService {
    private static final String APPLICATION_NAME = "ExcelSave";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final Set<String> SCOPES = DriveScopes.all();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private final NetHttpTransport HTTP_TRANSPORT;
    private final Drive DRIVE;
    private final String MIME_TYPE;

    public GoogleApiServiceImpl(String MIME_TYPE) {
        this.MIME_TYPE = MIME_TYPE;
        try {
            this.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            this.DRIVE = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveProperty(String key, String value) throws IOException {
        PropertiesReader propsReader = new PropertiesReader("application.properties");

        Properties properties = propsReader.loadProperties();

        properties.setProperty(key, value);
        properties.store(Files.newOutputStream(Paths.get("C:\\botllewater\\src\\main\\resources\\application.properties")), "");
    }

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        InputStream in = GoogleApiServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();


        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("vladislav.odai@gmail.com");

        saveProperty("OauthAccessToken", credential.getAccessToken());
        //returns an authorized Credential object.
        return credential;
    }

    public List<File> getAllFilesFromDrive() {
        FileList result = null;
        try {
            result = DRIVE.files().list().setFields("files(id, name)").execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.getFiles();
    }

    public String uploadFileIntoDrive(String fileName, java.io.File fileToInsert) {
        File uploadedFile = null;
        Boolean fileDoesNotExist = getAllFilesByName(fileName) == null;
        if (getAllFilesByName(fileName) != null) {
            try {
                String fileId = DRIVE.files().get(Objects.requireNonNull(getAllFilesByName(fileName)).get(0).getId()).execute().getId();
                File file = new File();
                file.setName(fileName);
                // File's new content.
                FileContent mediaContent = new FileContent(MIME_TYPE, fileToInsert);

                uploadedFile = DRIVE.files().update(fileId, file, mediaContent).execute();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File();
            file.setName(fileName);

            //mime type and file like new java.io.File(PathToFileFromComputer)
            FileContent fileContent = new FileContent(MIME_TYPE, fileToInsert);

            //uploading
            try {
                uploadedFile = DRIVE.files().create(file, fileContent).setFields("id").execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return String.format("{fileId: '%s'}", uploadedFile.getId());

    }

    private List<File> getAllFilesByName(String fileName) {
        FileList result = null;
        try {
            result = DRIVE.files().list()
                    .setQ("mimeType='" + MIME_TYPE + "'")
                    .setQ("name = '" + fileName + "'")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (result.getFiles().size() != 0) {
            return new ArrayList<>(result.getFiles());
        } else return null;
    }
}