package com.iesfranciscodelosrios;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.iesfranciscodelosrios.services.GoogleDriveService;

public class GoogleDriveTest {
	
	public static void main(String[] args) {
		
		GoogleDriveService gs=new GoogleDriveService();
		
		List<File> fileList=new ArrayList<File>();
		
		fileList.add(new File("mierdadesage.pdf"));
		fileList.add(new File("mierdadesage.pdf"));
		fileList.add(new File("mierda sage 2.pdf"));
		
		try {
			//gs.createFolder();
			//gs.uploadFile();
			gs.createOrderFolder("Pedido de Paco", fileList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
