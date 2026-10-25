package com.convo.cvofile.controller;

import com.convo.cvofile.dto.CvoFileDTO;
import com.convo.cvofile.service.CvoFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class CvoFileController {

    private final CvoFileService fileService;

    public CvoFileController(CvoFileService fileService) {
        this.fileService = fileService;
    }

    private Logger logger = LoggerFactory.getLogger(CvoFileController.class);

    @PostMapping(value = "/file/create", consumes = "multipart/form-data")
    public ResponseEntity<CvoFileDTO> createCvoFile(
            @ModelAttribute CvoFileDTO cvoFileDTO,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("Received empty file to upload");
            return ResponseEntity.status(500).build();
        }
        logger.info("Received upload: {} ({} bytes)",
                file.getOriginalFilename(), file.getSize());
        try {
            String uuid = fileService.create(file);
            CvoFileDTO cvoFileWithUuidDTO = new CvoFileDTO(
                    cvoFileDTO.id(),
                    cvoFileDTO.name(),
                    cvoFileDTO.description(),
                    uuid);
            return ResponseEntity.status(HttpStatus.CREATED).body(cvoFileWithUuidDTO);
        } catch (Exception e) {
            String errorMessage = "Error saving the file just uploaded: " + file.getOriginalFilename();
            logger.error(errorMessage, e);
            return ResponseEntity.status(500).build();
        }

    }
}