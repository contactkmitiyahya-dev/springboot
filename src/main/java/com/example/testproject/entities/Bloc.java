package com.example.testproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBloc;
    private String nomBloc;
    private long capacite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloc")
    @JsonIgnore
    private Set<Chambre> chambreSet;

    //bloc file
    @ManyToOne
    private Foyer foyer;

    public Bloc() {}

    public Bloc(long idBloc, String nomBloc, long capacite, Set<Chambre> chambreSet, Foyer foyer) {
        this.idBloc = idBloc;
        this.nomBloc = nomBloc;
        this.capacite = capacite;
        this.chambreSet = chambreSet;
        this.foyer = foyer;
    }

    public long getIdBloc() {
        return idBloc;
    }

    public String getNomBloc() {
        return nomBloc;
    }

    public long getCapacite() {
        return capacite;
    }

    public Set<Chambre> getChambreSet() {
        return chambreSet;
    }

    public Foyer getFoyer() {
        return foyer;
    }

    public void setIdBloc(long idBloc) {
        this.idBloc = idBloc;
    }

    public void setNomBloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }

    public void setCapacite(long capacite) {
        this.capacite = capacite;
    }

    public void setChambreSet(Set<Chambre> chambreSet) {
        this.chambreSet = chambreSet;
    }

    public void setFoyer(Foyer foyer) {
        this.foyer = foyer;
    }
}