package com.upload.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class provides file upload functionality and meta-data of file 
 * @author ashwin.chavan
 *
 */
@RestController
public class FileUploadController {

    //Save the uploaded file to this folder
    private static String FILE_UPLOADED_FOLDER = "F://File//";
    private Map<String,Long> metadata= new HashMap<>();

    /**
     * Get meta-data of file by filename
     * @param fileName provide name of file
     * @return meta-data of file
     */
    @GetMapping("/meta-data")
    public ResponseEntity<String> getFileMetaData(@RequestParam("filename") String fileName) {
    	
    	return new ResponseEntity<String>("Meta-Data, File size: "+metadata.get(fileName)
        		, new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * Upload file and store on local file system
     * @param file File to be uploaded
     * @return ResponseEntity object which represents entire HTTP response 
     */
    @PostMapping("/upload")
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity<String>("Please select a file!", HttpStatus.OK);
        }

        try {

            byte[] bytes = file.getBytes();
            metadata.put(file.getOriginalFilename(), file.getSize());
            Path path = Paths.get(FILE_UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
            
        return new ResponseEntity<String>("File '"+file.getOriginalFilename()+ "' is successfully uploaded"
        		, new HttpHeaders(), HttpStatus.OK);

    }
}