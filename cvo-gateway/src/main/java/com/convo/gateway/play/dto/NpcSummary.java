package com.convo.gateway.play.dto;

public record NpcSummary(Long id, String name, String email, String description, String bookUrl) {

    public static NpcSummary from(NpcResponse npcResponse) {
        return new NpcSummary(npcResponse.id(), npcResponse.name(), npcResponse.email(), npcResponse.description(), npcResponse.bookUrl());
    }
}