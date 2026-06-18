package com.simac.simacordre.repositories;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.enums.StatutCourrierEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CourrierRepository extends JpaRepository<Courrier, Long> {
    List<Courrier> findByStatut(StatutCourrierEnum statut);
    List<Courrier> findByDepartementId(Long departementId);
    List<Courrier> findByArchiveFalse();


}
