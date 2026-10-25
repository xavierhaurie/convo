package com.convo.converse.dto;

import com.convo.converse.entity.Utterance;

public record UtteranceDTO(Long id, Utterance replyTo, String content, Long fromPersonId, Long toPersonId) {

    public static UtteranceDTO from(Utterance utterance) {
        return new UtteranceDTO(utterance.getId(), utterance.getReplayTo(), utterance.getContent(), utterance.getFromPersonId(), utterance.getToPersonId());
    }

    public Utterance toEntity() {
        return new Utterance(replyTo, content, fromPersonId, toPersonId);
    }
}
