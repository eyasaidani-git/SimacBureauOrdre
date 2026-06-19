package com.simac.simacordre.services;

import com.simac.simacordre.entities.NumeroOrdreSeq;
import com.simac.simacordre.repositories.NumeroOrdreSeqRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class NumeroOrdreService {

    private final NumeroOrdreSeqRepository numeroOrdreSeqRepository;

    public NumeroOrdreService(NumeroOrdreSeqRepository numeroOrdreSeqRepository) {
        this.numeroOrdreSeqRepository = numeroOrdreSeqRepository;
    }

    @Transactional
    public synchronized ResultatNumeroOrdre genererNumeroOrdre(LocalDate dateReception) {
        LocalDate date = dateReception != null ? dateReception : LocalDate.now();

        Integer annee = date.getYear();
        Integer mois = date.getMonthValue();

        NumeroOrdreSeq sequence = numeroOrdreSeqRepository.findByAnneeAndMois(annee, mois)
                .orElseGet(() -> {
                    NumeroOrdreSeq nouvelleSequence = new NumeroOrdreSeq();
                    nouvelleSequence.setAnnee(annee);
                    nouvelleSequence.setMois(mois);
                    nouvelleSequence.setDernierNumero(0);
                    nouvelleSequence.setPrefixe(String.format("%04d/%02d", annee, mois));
                    nouvelleSequence.setUpdatedAt(LocalDateTime.now());
                    return numeroOrdreSeqRepository.save(nouvelleSequence);
                });

        Integer prochainNumero = sequence.getDernierNumero() + 1;

        sequence.setDernierNumero(prochainNumero);
        sequence.setUpdatedAt(LocalDateTime.now());

        NumeroOrdreSeq sequenceSauvegardee = numeroOrdreSeqRepository.save(sequence);

        String numeroOrdre = sequenceSauvegardee.getPrefixe() + "/" + String.format("%04d", prochainNumero);

        return new ResultatNumeroOrdre(numeroOrdre, sequenceSauvegardee);
    }

    public static class ResultatNumeroOrdre {

        private final String numeroOrdre;
        private final NumeroOrdreSeq numeroOrdreSeq;

        public ResultatNumeroOrdre(String numeroOrdre, NumeroOrdreSeq numeroOrdreSeq) {
            this.numeroOrdre = numeroOrdre;
            this.numeroOrdreSeq = numeroOrdreSeq;
        }

        public String getNumeroOrdre() {
            return numeroOrdre;
        }

        public NumeroOrdreSeq getNumeroOrdreSeq() {
            return numeroOrdreSeq;
        }
    }
}