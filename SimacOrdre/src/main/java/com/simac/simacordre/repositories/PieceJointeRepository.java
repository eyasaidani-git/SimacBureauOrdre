package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {

    List<PieceJointe> findByCourrierIdOrderByUploadedAtDesc(Long courrierId);
}