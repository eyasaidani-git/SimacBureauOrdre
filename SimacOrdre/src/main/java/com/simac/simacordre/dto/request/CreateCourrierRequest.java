package com.simac.simacordre.dto.request;

import com.simac.simacordre.enums.PrioriteEnum;
import com.simac.simacordre.enums.TypeCourrierEnum;

import java.time.LocalDate;

public class CreateCourrierRequest {

    private String objet;
    private String expediteur;
    private String destination;
    private TypeCourrierEnum type;
    private PrioriteEnum priorite;
    private LocalDate dateReception;
    private LocalDate dateLimite;
    private String description;
    private Long createurId;
    private Long departementId;

    public String getObjet() {
        return objet;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getDestination() {
        return destination;
    }

    public TypeCourrierEnum getType() {
        return type;
    }

    public PrioriteEnum getPriorite() {
        return priorite;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public LocalDate getDateLimite() {
        return dateLimite;
    }

    public String getDescription() {
        return description;
    }

    public Long getCreateurId() {
        return createurId;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setType(TypeCourrierEnum type) {
        this.type = type;
    }

    public void setPriorite(PrioriteEnum priorite) {
        this.priorite = priorite;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public void setDateLimite(LocalDate dateLimite) {
        this.dateLimite = dateLimite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateurId(Long createurId) {
        this.createurId = createurId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }
}