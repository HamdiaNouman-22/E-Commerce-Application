package com.example.pinkbullmakeup.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final Path uploadPath = Paths.get("uploads");

    public ImageStorageService() throws IOException {
        Files.createDirectories(uploadPath); // ensure folder exists
    }

    public String store(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;  // public URL path (served statically)
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}

