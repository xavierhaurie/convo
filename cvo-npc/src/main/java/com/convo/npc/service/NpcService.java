package com.convo.npc.service;

import com.convo.npc.controller.NpcController;
import com.convo.npc.dto.NpcDTO;
import com.convo.npc.entity.Npc;
import com.convo.npc.exception.NpcNotFoundException;
import com.convo.npc.repository.NpcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NpcService {

    private Logger logger = LoggerFactory.getLogger(NpcController.class);

    private final NpcRepository npcRepository;
    private final StorageService storageService;

    public NpcService(NpcRepository npcRepository, StorageService storageService) {
        this.npcRepository = npcRepository;
        this.storageService = storageService;
    }
    // fill out later when we move methods from controller to service

    public NpcDTO getNpc(Long npcId) {
        NpcDTO npcDTO = npcRepository.findById(npcId)
                .map(NpcDTO::from)
                .orElseThrow(() -> new NpcNotFoundException(npcId));

        return npcDTO;

    }


    public Resource getNpcBook(Long npcId) throws IOException {
        logger.info("NpcService.getNpcBook() called with Npc id {}", npcId);
        NpcDTO npcDTO = npcRepository.findById(npcId)
                .map(NpcDTO::from)
                .orElseThrow(() -> new NpcNotFoundException(npcId));
        logger.info("npcRepository.findById() returning {}", npcDTO);
        String url = npcDTO.bookUrl();
        File file = storageService.download(url);
        Resource resource = new org.springframework.core.io.FileSystemResource(file);
        return resource;
    }

    public List<NpcDTO> getAllNpcs() {
        List<Npc> npcs = npcRepository.findAll();
        return npcs.stream()
                .map(NpcDTO::from)
                .collect(Collectors.toList());
    }

    public NpcDTO createNpc(NpcDTO npcDTO, MultipartFile file) throws IOException {
        Npc npc = npcDTO.toEntity();
        String url = storageService.upload(file);
        npc.setBookUrl(url);
        NpcDTO saved = NpcDTO.from(npcRepository.save(npc));

        return saved;
    }
}


