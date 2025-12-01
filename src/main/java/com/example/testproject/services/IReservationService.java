package com.example.testproject.services;

import com.example.testproject.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservationById(long id);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(long id);
    Reservation ajouterReservation(long idChambre, long cinEtudiant);
    Reservation annulerReservation(long cinEtudiant);
    List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(LocalDate anneeUniversite, String nomUniversite);
}