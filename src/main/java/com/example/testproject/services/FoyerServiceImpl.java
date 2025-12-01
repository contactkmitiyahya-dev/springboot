package com.example.testproject.services;

import com.example.testproject.entities.Bloc;
import com.example.testproject.entities.Foyer;
import com.example.testproject.entities.Universite;
import com.example.testproject.repository.BlocRepository;
import com.example.testproject.repository.FoyerRepository;
import com.example.testproject.repository.UniversiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FoyerServiceImpl implements IFoyerService {

    @Autowired
    private FoyerRepository foyerRepository;

    @Autowired
    private UniversiteRepository universiteRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Override
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer getFoyerById(long id) {
        return foyerRepository.findById(id).orElse(null);
    }

    @Override
    public Foyer updateFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public void deleteFoyer(long id) {
        foyerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);

        if (universite == null) {
            return null;
        }

        if (universite.getFoyer() != null) {
            return null;
        }

        Foyer foyerSauvegarde = foyerRepository.save(foyer);

        if (foyer.getBlocSet() != null) {
            for (Bloc bloc : foyer.getBlocSet()) {
                bloc.setFoyer(foyerSauvegarde);
                blocRepository.save(bloc);
            }
        }

        universite.setFoyer(foyerSauvegarde);
        foyerSauvegarde.setUniversite(universite);

        universiteRepository.save(universite);
        return foyerRepository.save(foyerSauvegarde);
    }
}