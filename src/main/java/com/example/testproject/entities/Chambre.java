package com.example.testproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idChambre;
    private long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre typeC;

    @ManyToOne
    @JsonIgnore
    private Bloc bloc;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reservation> reservationSet;


    public Chambre() {}

    public Chambre(long idChambre, long numeroChambre, TypeChambre typeC, Bloc bloc, Set<Reservation> reservationSet) {
        this.idChambre = idChambre;
        this.numeroChambre = numeroChambre;
        this.typeC = typeC;
        this.bloc = bloc;
        this.reservationSet = reservationSet;
    }

    public long getIdChambre() {
        return idChambre;
    }

    public long getNumeroChambre() {
        return numeroChambre;
    }

    public TypeChambre getTypeC() {
        return typeC;
    }

    public Bloc getBloc() {
        return bloc;
    }

    public Set<Reservation> getReservationSet() {
        return reservationSet;
    }

    public void setIdChambre(long idChambre) {
        this.idChambre = idChambre;
    }

    public void setNumeroChambre(long numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public void setTypeC(TypeChambre typeC) {
        this.typeC = typeC;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public void setReservationSet(Set<Reservation> reservationSet) {
        this.reservationSet = reservationSet;
    }
}