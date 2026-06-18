package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreateCourrierRequest;
import com.simac.simacordre.dto.response.CourrierResponse;
import com.simac.simacordre.services.CourrierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courriers")
public class CourrierController {

    private final CourrierService courrierService;

    public CourrierController(CourrierService courrierService) {
        this.courrierService = courrierService;
    }

    @GetMapping
    public List<CourrierResponse> getAllCourriers() {
        return courrierService.getAllCourriers();
    }

    @GetMapping("/{id}")
    public CourrierResponse getCourrierById(@PathVariable Long id) {
        return courrierService.getCourrierById(id);
    }

    @PostMapping
    public CourrierResponse createCourrier(@RequestBody CreateCourrierRequest request) {
        return courrierService.createCourrier(request);
    }
}