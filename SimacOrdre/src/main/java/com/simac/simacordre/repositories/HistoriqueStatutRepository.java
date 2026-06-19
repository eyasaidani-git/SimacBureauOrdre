package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.HistoriqueStatut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueStatutRepository extends JpaRepository<HistoriqueStatut, Long> {

    List<HistoriqueStatut> findByCourrierIdOrderByCreatedAtDesc(Long courrierId);
}