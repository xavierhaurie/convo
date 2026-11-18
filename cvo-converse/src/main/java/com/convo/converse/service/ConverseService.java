package com.convo.converse.service;

import com.convo.converse.dto.UtteranceDTO;
import com.convo.converse.repository.ConverseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ConverseService {

    private final Logger logger = LoggerFactory.getLogger(ConverseService.class);

    private final ConverseRepository converseRepository;

    public ConverseService(ConverseRepository converseRepository) {
        this.converseRepository = converseRepository;
    }

    public UtteranceDTO getReply(Long replyId) {
        return converseRepository.findById(replyId)
                .map(UtteranceDTO::from)
                .orElseThrow(() -> new NoSuchElementException("Utterance not found: " + replyId));
    }

    public List<UtteranceDTO> getAllUtterances() {
        return converseRepository.findAll().stream()
                .map(UtteranceDTO::from)
                .toList();
    }

    public UtteranceDTO createUtterance(UtteranceDTO utteranceDTO) {
        logger.info("createUtterance() called from {} to {}", utteranceDTO.fromPersonId(), utteranceDTO.toPersonId());
        return UtteranceDTO.from(converseRepository.save(utteranceDTO.toEntity()));
    }

}
