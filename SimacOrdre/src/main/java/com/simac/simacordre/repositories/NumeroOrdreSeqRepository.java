package com.simac.simacordre.repositories;
import com.simac.simacordre.entities.NumeroOrdreSeq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface NumeroOrdreSeqRepository extends JpaRepository<NumeroOrdreSeq, Long> {
    Optional<NumeroOrdreSeq> findByAnneeAndMois(Integer anne, Integer mois);
}
