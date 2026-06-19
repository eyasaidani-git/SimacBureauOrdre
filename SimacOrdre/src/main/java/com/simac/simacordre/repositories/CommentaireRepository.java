package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    List<Commentaire> findByCourrierIdOrderByCreatedAtDesc(Long courrierId);

    List<Commentaire> findByAuteurIdOrderByCreatedAtDesc(Long auteurId);
}