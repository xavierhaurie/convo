package com.convo.converse.repository;

import com.convo.converse.entity.Utterance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConverseRepository extends JpaRepository<Utterance, Long> {
}
