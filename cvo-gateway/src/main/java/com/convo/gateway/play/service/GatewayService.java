package com.convo.gateway.play.service;

import com.convo.gateway.play.controller.GatewayController;
import com.convo.gateway.play.dto.NpcResponse;
import com.convo.gateway.play.dto.NpcSummary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GatewayService {

    private final RestClient restClient;
    private final String utterServiceBaseUrl;
    private final String npcServiceBaseUrl;
    private final String creditServiceBaseUrl;

    private Logger logger = LoggerFactory.getLogger(GatewayController.class);

    public GatewayService(
            RestClient.Builder restClientBuilder,
            @Value("${service.utter-service.url}") String utterServiceBaseUrl,
            @Value("${service.npc-service.url}") String npcServiceBaseUrl,
            @Value("${service.credit-service.url}") String creditServiceBaseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.utterServiceBaseUrl = utterServiceBaseUrl;
        this.npcServiceBaseUrl = npcServiceBaseUrl;
        this.creditServiceBaseUrl = creditServiceBaseUrl;
    }

    public NpcResponse getNpc(@PathVariable Long npcId) {
        logger.info("getNpc() called with npcId {}", npcId);
        NpcResponse npcResponse = restClient.get()
                .uri(npcServiceBaseUrl + "/npc/{npcId}", npcId)
                .retrieve()
                .body(NpcResponse.class);

        return npcResponse;
    }

    public List<NpcResponse> getAllNpcs() {
        List<NpcResponse> npcs = restClient.get()
                            .uri(npcServiceBaseUrl + "/npcs")
                            .retrieve()
                            .body(new ParameterizedTypeReference<List<NpcResponse>>() {});
        return npcs;
    }

    public NpcResponse createNpc(
            NpcSummary npcDTO,
            MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("Received empty file to upload");
            throw new IllegalArgumentException("File is empty");
        }
        logger.info("Received upload: {} ({} bytes)",
                file.getOriginalFilename(), file.getSize());
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("name", npcDTO.name());
        parts.add("description", npcDTO.description());

        // Wrap the file so RestClient preserves filename + content type
        try {
            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override public String getFilename() { return file.getOriginalFilename(); }
            };
            parts.add("file", fileResource);

            return restClient.post()
                    .uri(npcServiceBaseUrl + "/npc/create")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(parts)
                    .retrieve()
                    .body(NpcResponse.class);
        }
        catch (Exception e) {
            String errorMessage = "Error saving the file just uploaded " + file.getOriginalFilename();
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }

    }
}