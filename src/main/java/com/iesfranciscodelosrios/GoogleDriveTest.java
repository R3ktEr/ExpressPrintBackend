package com.iesfranciscodelosrios;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.iesfranciscodelosrios.services.GoogleDriveService;

public class GoogleDriveTest {
	
	public static void main(String[] args) {
		try {
			//GoogleDriveService.createFolder();
			//GoogleDriveService.uploadFile();
			GoogleDriveService.setPermission();
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
