package com.example.testproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Foyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFoyer;
    private String nomFoyer;
    private long capacite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foyer")
    @JsonIgnore
    private Set<Bloc> blocSet;

    @OneToOne(mappedBy = "foyer")
    @JsonIgnore
    private Universite universite;

    public Foyer() {}

    public Foyer(long idFoyer, String nomFoyer, long capacite, Set<Bloc> blocSet, Universite universite) {
        this.idFoyer = idFoyer;
        this.nomFoyer = nomFoyer;
        this.capacite = capacite;
        this.blocSet = blocSet;
        this.universite = universite;
    }

    public long getIdFoyer() {
        return idFoyer;
    }

    public String getNomFoyer() {
        return nomFoyer;
    }

    public long getCapacite() {
        return capacite;
    }

    public Set<Bloc> getBlocSet() {
        return blocSet;
    }

    public Universite getUniversite() {
        return universite;
    }

    public void setIdFoyer(long idFoyer) {
        this.idFoyer = idFoyer;
    }

    public void setNomFoyer(String nomFoyer) {
        this.nomFoyer = nomFoyer;
    }

    public void setCapacite(long capacite) {
        this.capacite = capacite;
    }

    public void setBlocSet(Set<Bloc> blocSet) {
        this.blocSet = blocSet;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
    }
}