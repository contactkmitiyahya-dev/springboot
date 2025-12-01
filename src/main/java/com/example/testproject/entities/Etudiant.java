package com.example.testproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEtudiant;
    private String nomEtudiant;
    private String prenomEtudiant;
    private long cin;
    private String ecole;
    private LocalDate dateNaissance;

    @ManyToMany(mappedBy = "etudiantSet", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reservation> reservationSet;

    public Etudiant() {}

    public Etudiant(long idEtudiant, String nomEtudiant, String prenomEtudiant, long cin, String ecole, LocalDate dateNaissance, Set<Reservation> reservationSet) {
        this.idEtudiant = idEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.cin = cin;
        this.ecole = ecole;
        this.dateNaissance = dateNaissance;
        this.reservationSet = reservationSet;
    }

    public long getIdEtudiant() {
        return idEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public long getCin() {
        return cin;
    }

    public String getEcole() {
        return ecole;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Set<Reservation> getReservationSet() {
        return reservationSet;
    }

    public void setIdEtudiant(long idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public void setCin(long cin) {
        this.cin = cin;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setReservationSet(Set<Reservation> reservationSet) {
        this.reservationSet = reservationSet;
    }
}