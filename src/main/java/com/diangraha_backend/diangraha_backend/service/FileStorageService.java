package com.diangraha_backend.diangraha_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads"; // default folder

    public String storeFile(MultipartFile file, String subFolder) throws IOException {
        Path dirPath = Paths.get(uploadDir, subFolder);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // Clean filename - remove spaces and special characters
        String originalName = file.getOriginalFilename();
        String cleanName = originalName.replaceAll("[^a-zA-Z0-9.-]", "_");
        String fileName = System.currentTimeMillis() + "_" + cleanName;
        
        Path filePath = dirPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Return full URL with port 8080 for proper access
        return "http://103.103.20.23:8080/uploads/" + subFolder + "/" + fileName;
    }
}
