package com.convo.converse.dto;

import com.convo.converse.entity.Utterance;

public record UtteranceDTO(Long id, Long replyToId, String content, Long fromPersonId, Long toPersonId) {

    public static UtteranceDTO from(Utterance utterance) {
        return new UtteranceDTO(utterance.getId(), utterance.getReplyToId(), utterance.getContent(), utterance.getFromPersonId(), utterance.getToPersonId());
    }

    public Utterance toEntity() {
        return new Utterance(replyToId, content, fromPersonId, toPersonId);
    }
}
