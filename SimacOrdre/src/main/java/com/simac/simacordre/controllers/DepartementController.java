package com.simac.simacordre.controllers;
import com.simac.simacordre.entities.Departement;
import com.simac.simacordre.repositories.DepartementRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {
    private final DepartementRepository departementRepository;

    public DepartementController(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }
}
