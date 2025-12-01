package com.example.testproject.services;

import com.example.testproject.entities.Chambre;
import com.example.testproject.entities.Reservation;
import com.example.testproject.entities.TypeChambre;
import com.example.testproject.entities.Universite;
import com.example.testproject.repository.ChambreRepository;
import com.example.testproject.repository.UniversiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChambreServiceImpl implements IChambreService {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private UniversiteRepository universiteRepository;

    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    @Override
    public Chambre getChambreById(long id) {
        return chambreRepository.findById(id).orElse(null);
    }

    @Override
    public Chambre updateChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public void deleteChambre(long id) {
        chambreRepository.deleteById(id);
    }

    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite).orElse(null);

        if (universite == null || universite.getFoyer() == null) {
            return new ArrayList<>();
        }

        List<Chambre> chambres = new ArrayList<>();

        universite.getFoyer().getBlocSet().forEach(bloc -> {
            chambres.addAll(bloc.getChambreSet());
        });

        return chambres;
    }
    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        // SOLUTION 1: Utilisation des Keywords
        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);

        // SOLUTION 2: Utilisation de JPQL
        // return chambreRepository.findChambresByBlocAndType(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        List<Chambre> toutesChambres = chambreRepository.findAll();
        List<Chambre> chambresNonReservees = new ArrayList<>();

        for (Chambre chambre : toutesChambres) {
            boolean appartientAUniversite = chambreAppartientAUniversite(chambre, nomUniversite);

            boolean bonType = chambre.getTypeC() == type;

            boolean nonReservee = estNonReserveeCetteAnnee(chambre);

            if (appartientAUniversite && bonType && nonReservee) {
                chambresNonReservees.add(chambre);
            }
        }

        return chambresNonReservees;
    }

    private boolean chambreAppartientAUniversite(Chambre chambre, String nomUniversite) {
        if (chambre.getBloc() == null ||
                chambre.getBloc().getFoyer() == null ||
                chambre.getBloc().getFoyer().getUniversite() == null) {
            return false;
        }

        return chambre.getBloc().getFoyer().getUniversite().getNomUniversite().equals(nomUniversite);
    }

    private boolean estNonReserveeCetteAnnee(Chambre chambre) {
        int anneeActuelle = LocalDate.now().getYear();

        for (Reservation reservation : chambre.getReservationSet()) {
            if (reservation.isValide() && reservation.getAnneeUniversitare().getYear() == anneeActuelle) {
                return false;
            }
        }

        return true;
    }
}