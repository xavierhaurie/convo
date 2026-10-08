package com.convo.npc.controller;

import com.convo.npc.service.NpcService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.convo.npc.dto.NpcDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NpcController {

    private final NpcService npcService;

    public NpcController(NpcService npcService) {
        this.npcService = npcService;
    }

    private Logger logger = LoggerFactory.getLogger(NpcController.class);

    @GetMapping("/npc/{npcId}")
    public NpcDTO getNpc(@PathVariable Long npcId) {
        logger.info("getNpc() called with npc_id {}", npcId);
        return npcService.getNpc(npcId);
    }

    @GetMapping("/npc/book/{npcId}")
    public ResponseEntity<Resource> getNpcBook(@PathVariable Long npcId) {
        logger.info("getNpcBook() called with Npc id {}", npcId);
        try {
            Resource resource = npcService.getNpcBook(npcId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (Exception e) {
            String errorMessage = "Error downloading book for NPC id " + npcId;
            logger.error(errorMessage, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/npcs")
    public List<NpcDTO> getAllNpcs(
    ) {
        logger.info("getAllNpcs() called");

        List<NpcDTO> npcs = npcService.getAllNpcs();

        logger.info("getAllNpcs() is returning {} records", npcs.size());

        return npcs;
    }

    @PostMapping(value = "/npc/create", consumes = "multipart/form-data")
    public ResponseEntity<NpcDTO> createNpc(
            @ModelAttribute NpcDTO npcDTO,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("Received empty file to upload");
            return ResponseEntity.status(500).build();
        }
        logger.info("Received upload: {} ({} bytes)",
                file.getOriginalFilename(), file.getSize());
        try {
            NpcDTO saved = npcService.createNpc(npcDTO, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            String errorMessage = "Error saving the file just uploaded " + file.getOriginalFilename();
            logger.error(errorMessage, e);
            return ResponseEntity.status(500).build();
        }


    }
}