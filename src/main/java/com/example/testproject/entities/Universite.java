package com.example.testproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Universite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUniversite;
    private String nomUniversite;
    private String adresse;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Foyer foyer;

    public Universite() {}

    public Universite(Long idUniversite, String nomUniversite, String adresse, Foyer foyer) {
        this.idUniversite = idUniversite;
        this.nomUniversite = nomUniversite;
        this.adresse = adresse;
        this.foyer = foyer;
    }
    public Long getIdUniversite() {
        return idUniversite;
    }

    public String getNomUniversite() {
        return nomUniversite;
    }

    public String getAdresse() {
        return adresse;
    }

    public Foyer getFoyer() {
        return foyer;
    }


    public void setIdUniversite(Long idUniversite) {
        this.idUniversite = idUniversite;
    }

    public void setNomUniversite(String nomUniversite) {
        this.nomUniversite = nomUniversite;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setFoyer(Foyer foyer) {
        this.foyer = foyer;
    }
}