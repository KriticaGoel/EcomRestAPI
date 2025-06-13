package com.kritica.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {

        // Create absolute path using user.dir system property
        String projectPath = System.getProperty("user.dir");
        File uploadDir = new File(projectPath + path);

        // Ensure directory exists
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new IOException("Failed to create directory " + uploadDir);
            }
        }

        //1. File names of the current / original file
        String currentFileName = image.getOriginalFilename();
        //2. Generate a unique file name
        String randomId= UUID.randomUUID().toString();
        String filename = randomId.concat(currentFileName.substring(currentFileName.lastIndexOf('.')));
        // Create complete file path
        Path filePath = uploadDir.toPath().resolve(filename);


//        //3. Check if a path exists and create
//        File folder = new File(filePath);
//        if(!folder.exists()){
//            folder.mkdirs();
//        }
        //4. Upload the file on server
        // Copy file using atomic operation
        Files.copy(image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);


        //5. Return file name
        return filename;

    }
}
