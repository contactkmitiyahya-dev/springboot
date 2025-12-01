package com.example.testproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReservation;
    private String numReservation;
    private LocalDate anneeUniversitare;
    private boolean isValide;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Etudiant> etudiantSet;

    public Reservation() {}

    public Reservation(String numReservation,long idReservation, LocalDate anneeUniversitare, boolean isValide, Set<Etudiant> etudiantSet) {
        this.idReservation = idReservation;
        this.numReservation = numReservation;
        this.anneeUniversitare = anneeUniversitare;
        this.isValide = isValide;
        this.etudiantSet = etudiantSet;
    }

    public long getIdReservation() {
        return idReservation;
    }

    public LocalDate getAnneeUniversitare() {
        return anneeUniversitare;
    }

    public boolean isValide() {
        return isValide;
    }

    public Set<Etudiant> getEtudiantSet() {
        return etudiantSet;
    }

    public void setIdReservation(long idReservation) {
        this.idReservation = idReservation;
    }

    public void setAnneeUniversitare(LocalDate anneeUniversitare) {
        this.anneeUniversitare = anneeUniversitare;
    }

    public void setValide(boolean valide) {
        isValide = valide;
    }

    public void setEtudiantSet(Set<Etudiant> etudiantSet) {
        this.etudiantSet = etudiantSet;
    }
    public String getNumReservation() {
        return numReservation;
    }

    public void setNumReservation(String numReservation) {
        this.numReservation = numReservation;
    }
}