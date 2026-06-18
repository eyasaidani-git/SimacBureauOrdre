package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {

    List<Affectation> findByCourrierId(Long courrierId);

    List<Affectation> findByCourrierIdAndActiveTrue(Long courrierId);
}