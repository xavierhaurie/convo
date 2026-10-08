package com.convo.npc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Profile("!gcp")
public class LocalStorageService implements StorageService {

    private final Path storageDir;

    public LocalStorageService(
            @Value("${local.storage.dir}") String storageDir) throws IOException {
        this.storageDir = Paths.get(storageDir).toAbsolutePath().normalize();
        Files.createDirectories(this.storageDir);
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = this.storageDir.resolve(objectName).normalize();
        if (!target.startsWith(this.storageDir)) {
            throw new IOException("Invalid file path: " + file.getOriginalFilename());
        }
        Files.copy(file.getInputStream(), target);
        return target.toUri().toString();
    }

    @Override
    public File download(String url) throws IOException {
        Path filePath = Paths.get(URI.create(url));
        if (!Files.exists(filePath)) {
            throw new IOException("File not found with URL: " + url);
        }
        File file = filePath.toFile();
        return file;

    }

}
