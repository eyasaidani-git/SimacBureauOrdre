package com.simac.simacordre.controllers;
import com.simac.simacordre.entities.Departement;
import com.simac.simacordre.repositories.DepartementRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {
    private final DepartementRepository departementRepository;

    public DepartementController(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }
}
