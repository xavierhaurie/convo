package com.convo.npc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NpcNotFoundException extends RuntimeException {
    public NpcNotFoundException(Long id) {
        super("NPC not found with id " + id);
    }
}