package com.example.testproject.controller;

import com.example.testproject.entities.Reservation;
import com.example.testproject.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable long id) {
        return reservationService.getReservationById(id);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable long id, @RequestBody Reservation reservation) {
        reservation.setIdReservation(id);
        return reservationService.updateReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping("/ajouter/{idChambre}/{cinEtudiant}")
    public Reservation ajouterReservation(@PathVariable long idChambre, @PathVariable long cinEtudiant) {
        return reservationService.ajouterReservation(idChambre, cinEtudiant);
    }

    @PutMapping("/annuler/{cinEtudiant}")
    public Reservation annulerReservation(@PathVariable long cinEtudiant) {
        return reservationService.annulerReservation(cinEtudiant);
    }

    @GetMapping("/universite/{nomUniversite}/annee/{anneeUniversite}")
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(
            @PathVariable String nomUniversite,
            @PathVariable String anneeUniversite) {

        LocalDate date = LocalDate.parse(anneeUniversite + "-09-01");
        return reservationService.getReservationParAnneeUniversitaireEtNomUniversite(date, nomUniversite);
    }
}