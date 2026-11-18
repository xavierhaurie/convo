package com.convo.cvofile.repository;

import com.convo.cvofile.entity.CvoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvoFileRepository extends JpaRepository<CvoFile, Long> {
}
