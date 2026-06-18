package com.simac.simacordre.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DatabaseTestController {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseTestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/test-db")
    public Map<String, Object> testDatabase() {
        Integer nombreDepartements = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM departement",
                Integer.class
        );

        List<Map<String, Object>> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema = 'public' ORDER BY table_name"
        );

        return Map.of(
                "message", "Connexion PostgreSQL réussie",
                "nombreDepartements", nombreDepartements,
                "tables", tables
        );
    }
}