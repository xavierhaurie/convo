package com.convo.cvofile.service;

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
public class LocalCvoFileService implements CvoFileService {

    private final Path storageDir;

    public LocalCvoFileService(
            @Value("${local.storage.dir}") String storageDir) throws IOException {
        this.storageDir = Paths.get(storageDir).toAbsolutePath().normalize();
        Files.createDirectories(this.storageDir);
    }

    @Override
    public String create(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        Path target = this.storageDir.resolve(uuid.toString()).normalize();
        if (!target.startsWith(this.storageDir)) {
            throw new IOException("Invalid file path: " + file.getOriginalFilename());
        }
        Files.copy(file.getInputStream(), target);
        return uuid.toString();
    }

    @Override
    public File get(String uuid) throws IOException {
        Path filePath = this.storageDir.resolve(uuid).normalize();

        if (!Files.exists(filePath)) {
            throw new IOException("CvoFile not found with UUID: " + uuid);
        }
        File file = filePath.toFile();
        return file;

    }

}
