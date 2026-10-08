package com.convo.npc.dto;

import com.convo.npc.entity.Npc;

public record NpcDTO(Long id, String name, String description, String bookUrl) {

    static public NpcDTO from(Npc npcEntity) {
        return new NpcDTO(npcEntity.getId(), npcEntity.getName(), npcEntity.getDescription(), npcEntity.getBookUrl());
    }

    // bookUrl field excluded on purpose
    public Npc toEntity() {
        Npc npcEntity = new Npc();
        npcEntity.setName(this.name);
        npcEntity.setDescription(this.description);
        npcEntity.setBookUrl(this.bookUrl);
        return npcEntity;
    }
}
