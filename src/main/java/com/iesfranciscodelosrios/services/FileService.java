package com.iesfranciscodelosrios.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	
	private static final Logger logger = LogManager.getLogger(FileService.class);

    public List<File> uploadToLocal(List<MultipartFile> mfiles) throws IOException{
    	List<File> fileList=new ArrayList<File>();
    	
    	for (MultipartFile mfile : mfiles) {		
    		
    		File file=new File("tmp/"+mfile.getOriginalFilename());
    		
    		if(!file.exists()) {
    			Path p=Paths.get("tmp");
    			Files.createDirectories(p);
    			
    			file=new File("tmp/"+mfile.getOriginalFilename());
    		}
    		
			try (OutputStream os = new FileOutputStream(file)) {
				os.write(mfile.getBytes());
				
				fileList.add(file);
			}catch (IOException e) {
    			logger.info("Error al subir el archivo "+mfile.getOriginalFilename());
    			throw e;
    		}			
		}
    	
    	return fileList;
    }
    
    public void flushTmp() throws SecurityException{
    	File tmp=new File("tmp");
    	
    	File[] tmpFiles=tmp.listFiles();
    	
    	try {
    		for (File file : tmpFiles) {
    			file.delete();    			    			
    		}
		}catch(SecurityException e) {
			logger.info("Error al borrar la carpeta tmp");
			throw e;
		}
    }
}
