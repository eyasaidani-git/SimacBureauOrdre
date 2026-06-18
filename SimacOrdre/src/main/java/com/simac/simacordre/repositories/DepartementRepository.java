package com.simac.simacordre.repositories;
import com.simac.simacordre.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
