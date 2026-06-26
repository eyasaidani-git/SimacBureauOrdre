package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.EmailImportLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailImportLogRepository extends JpaRepository<EmailImportLog, Long> {

    boolean existsByMessageId(String messageId);
}