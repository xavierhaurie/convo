package com.convo.cvofile.service;

import com.convo.cvofile.dto.CvoFileDTO;
import com.convo.cvofile.entity.CvoFile;
import com.convo.cvofile.repository.CvoFileRepository;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Profile("gcp")
public class GcsCvoFileService implements CvoFileService {

    private final CvoFileRepository cvoFileRepository;

    private final Storage storage;
    private final String bucket;

    public GcsCvoFileService(
            CvoFileRepository cvoFileRepository,
            @Value("${gcs.bucket}") String bucket) {
        this.cvoFileRepository = cvoFileRepository;
        this.bucket = bucket;
        this.storage = StorageOptions.getDefaultInstance().getService();
        ensureBucketExists();
    }

    private void ensureBucketExists() {
        if (storage.get(bucket) == null) {
            storage.create(
                    BucketInfo.newBuilder(bucket)
                            .setLocation("US")            // or "US-EAST1", "EUROPE-WEST1", etc.
                            .build()
            );
        }
    }

    @Override
    public CvoFileDTO createCvoFile(CvoFileDTO cvoFileDTO, MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String objectName = uuid + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucket, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        // Take care of saving the file info into the database
        CvoFile fileEntity = cvoFileDTO.toEntity();
        fileEntity.setUuid(uuid.toString());
        cvoFileRepository.save(fileEntity);
        return CvoFileDTO.from(fileEntity);

        // maybe also save thios info:
        //        String.format("https://storage.googleapis.com/%s/%s", bucket, objectName);
    }

    /// This code auto-generated.
    /// CHECK carefully
    /// Also externalize the bucket name in the URL to avoid hardcoding it.
    @Override
    public File get(String uuid) throws IOException {
        Path filePath = Paths.get(uuid.replace("https://storage.googleapis.com/" + bucket + "/", ""));
        if (!Files.exists(filePath)) {
            throw new IOException("CvoFile not found: " + uuid);
        }
        return filePath.toFile();
    }

}
