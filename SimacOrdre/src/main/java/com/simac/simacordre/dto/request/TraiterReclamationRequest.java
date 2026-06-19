package com.simac.simacordre.dto.request;

import com.simac.simacordre.enums.StatutReclamationEnum;

public class TraiterReclamationRequest {

    private Long traiteeParId;
    private StatutReclamationEnum nouveauStatut;
    private String reponse;

    public TraiterReclamationRequest() {
    }

    public Long getTraiteeParId() {
        return traiteeParId;
    }

    public void setTraiteeParId(Long traiteeParId) {
        this.traiteeParId = traiteeParId;
    }

    public StatutReclamationEnum getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(StatutReclamationEnum nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}