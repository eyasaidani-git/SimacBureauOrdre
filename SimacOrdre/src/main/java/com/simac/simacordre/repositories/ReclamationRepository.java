package com.simac.simacordre.repositories;

import com.simac.simacordre.entities.Reclamation;
import com.simac.simacordre.enums.CategorieReclamationEnum;
import com.simac.simacordre.enums.StatutReclamationEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    List<Reclamation> findByCreateurId(Long createurId);

    List<Reclamation> findByCategorie(CategorieReclamationEnum categorie);

    List<Reclamation> findByStatut(StatutReclamationEnum statut);

    List<Reclamation> findByCourrierId(Long courrierId);
}