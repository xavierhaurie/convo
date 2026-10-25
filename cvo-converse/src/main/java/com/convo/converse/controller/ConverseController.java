package com.convo.converse.controller;

import com.convo.converse.dto.UtteranceDTO;
import com.convo.converse.service.ConverseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ConverseController {

    private final Logger logger = LoggerFactory.getLogger(ConverseController.class);

    private final ConverseService converseService;

    public ConverseController(ConverseService converseService) {
        this.converseService = converseService;
    }

    @GetMapping("/utterance/{utteranceId}")
    public UtteranceDTO getReply(@PathVariable Long utteranceId) {
        logger.info("getReply() called with utterance id {}", utteranceId);
        return converseService.getReply(utteranceId);
    }

    @GetMapping("/utterances")
    public List<UtteranceDTO> getAllReplies() {
        return converseService.getAllReplies();
    }

    @PostMapping("/utterance/create")
    public ResponseEntity<UtteranceDTO> createReply(@RequestBody UtteranceDTO utteranceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(converseService.createReply(utteranceDTO));
    }
}
