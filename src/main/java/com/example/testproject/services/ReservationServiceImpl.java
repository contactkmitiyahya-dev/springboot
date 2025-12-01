package com.example.testproject.services;

import com.example.testproject.entities.*;
import com.example.testproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Reservation ajouterReservation(long idChambre, long cinEtudiant) {
        Chambre chambre = chambreRepository.findById(idChambre).orElse(null);
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);

        if (chambre == null || etudiant == null) {
            return null;
        }

        // Vérifier capacité de la chambre
        int capaciteMax = getCapaciteMax(chambre.getTypeC());
        int reservationsActuelles = chambre.getReservationSet().size();

        if (reservationsActuelles >= capaciteMax) {
            return null; // Capacité maximale atteinte
        }


        String numReservation = genererNumReservation(chambre);

        Reservation reservation = new Reservation();
        reservation.setNumReservation(numReservation);
        reservation.setAnneeUniversitare(LocalDate.now());
        reservation.setValide(true);

        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiant);
        reservation.setEtudiantSet(etudiants);

        Reservation reservationSauvegardee = reservationRepository.save(reservation);

        chambre.getReservationSet().add(reservationSauvegardee);
        chambreRepository.save(chambre);

        return reservationSauvegardee;
    }

    private int getCapaciteMax(TypeChambre type) {
        switch (type) {
            case SIMPLE: return 1;
            case DOUBLE: return 2;
            case TRIPLE: return 3;
            default: return 0;
        }
    }

    private String genererNumReservation(Chambre chambre) {
        String nomBloc = chambre.getBloc() != null ? chambre.getBloc().getNomBloc() : "Unknown";
        int annee = LocalDate.now().getYear();
        return chambre.getNumeroChambre() + "-" + nomBloc + "-" + annee;
    }

    @Override
    @Transactional
    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);
        if (etudiant == null) {
            return null;
        }

        Reservation reservation = trouverReservationActiveParEtudiant(etudiant);
        if (reservation == null) {
            return null;
        }

        reservation.setValide(false);

        reservation.getEtudiantSet().remove(etudiant);
        etudiant.getReservationSet().remove(reservation);

        Chambre chambre = trouverChambreParReservation(reservation);
        if (chambre != null) {
            chambre.getReservationSet().remove(reservation);
            chambreRepository.save(chambre);
        }

        etudiantRepository.save(etudiant);
        return reservationRepository.save(reservation);
    }

    private Reservation trouverReservationActiveParEtudiant(Etudiant etudiant) {
        for (Reservation res : etudiant.getReservationSet()) {
            if (res.isValide()) {
                return res;
            }
        }
        return null;
    }

    private Chambre trouverChambreParReservation(Reservation reservation) {
        List<Chambre> toutesChambres = chambreRepository.findAll();
        for (Chambre chambre : toutesChambres) {
            if (chambre.getReservationSet().contains(reservation)) {
                return chambre;
            }
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(LocalDate anneeUniversite, String nomUniversite) {
        List<Reservation> toutesReservations = reservationRepository.findAll();
        List<Reservation> resultat = new ArrayList<>();

        for (Reservation reservation : toutesReservations) {
            boolean memeAnnee = reservation.getAnneeUniversitare().getYear() == anneeUniversite.getYear();

            if (memeAnnee) {
                boolean appartientAUniversite = reservationAppartientAUniversite(reservation, nomUniversite);

                if (appartientAUniversite) {
                    resultat.add(reservation);
                }
            }
        }

        return resultat;
    }

    private boolean reservationAppartientAUniversite(Reservation reservation, String nomUniversite) {
        List<Chambre> toutesChambres = chambreRepository.findAll();

        for (Chambre chambre : toutesChambres) {
            if (chambre.getReservationSet().contains(reservation)) {
                if (chambre.getBloc() != null &&
                        chambre.getBloc().getFoyer() != null &&
                        chambre.getBloc().getFoyer().getUniversite() != null &&
                        chambre.getBloc().getFoyer().getUniversite().getNomUniversite().equals(nomUniversite)) {
                    return true;
                }
            }
        }

        return false;
    }
}