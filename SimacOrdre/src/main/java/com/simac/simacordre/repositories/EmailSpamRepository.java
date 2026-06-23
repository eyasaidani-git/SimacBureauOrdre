package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.EmailSpam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailSpamRepository extends JpaRepository<EmailSpam, Long> {

    List<EmailSpam> findAllByOrderByDateReceptionDesc();

    List<EmailSpam> findByConfirmeSpamTrueOrderByDateReceptionDesc();

    List<EmailSpam> findByConfirmeSpamFalseOrderByDateReceptionDesc();

    List<EmailSpam> findByResponsableConcerneIdOrderByDateReceptionDesc(Long responsableConcerneId);
    List<EmailSpam> findByTraiteParIdOrderByDateReceptionDesc(Long traiteParId);

    long countByConfirmeSpamTrueAndCreatedAtBetween(LocalDateTime debut, LocalDateTime fin);
}