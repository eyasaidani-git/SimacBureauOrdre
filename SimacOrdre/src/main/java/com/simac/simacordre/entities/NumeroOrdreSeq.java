package com.simac.simacordre.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="numero_ordre_seq")
public class NumeroOrdreSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer annee;

    @Column(nullable = false)
    private Integer mois;

    @Column(name = "dernier_numero", nullable = false)
    private Integer dernierNumero = 0;

    @Column(nullable = false, length = 20)
    private String prefixe;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public NumeroOrdreSeq() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public void setDernierNumero(Integer dernierNumero) {
        this.dernierNumero = dernierNumero;
    }

    public void setPrefixe(String prefixe) {
        this.prefixe = prefixe;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public Integer getMois() {
        return mois;
    }

    public Integer getDernierNumero() {
        return dernierNumero;
    }

    public String getPrefixe() {
        return prefixe;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
