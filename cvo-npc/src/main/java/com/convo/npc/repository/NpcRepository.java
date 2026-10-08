package com.convo.npc.repository;

import com.convo.npc.entity.Npc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NpcRepository extends JpaRepository<Npc, Long> {
}

