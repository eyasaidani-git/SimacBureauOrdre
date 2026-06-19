package com.simac.simacordre.dto.request;

import com.simac.simacordre.enums.CategorieReclamationEnum;

public class CreateReclamationRequest {

    private CategorieReclamationEnum categorie;
    private String sujet;
    private String description;
    private Long createurId;
    private Long courrierId;

    public CreateReclamationRequest() {
    }

    public CategorieReclamationEnum getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieReclamationEnum categorie) {
        this.categorie = categorie;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateurId() {
        return createurId;
    }

    public void setCreateurId(Long createurId) {
        this.createurId = createurId;
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public void setCourrierId(Long courrierId) {
        this.courrierId = courrierId;
    }
}