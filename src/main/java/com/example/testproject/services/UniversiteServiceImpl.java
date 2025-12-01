package com.example.testproject.services;

import com.example.testproject.entities.Foyer;
import com.example.testproject.entities.Universite;
import com.example.testproject.repository.FoyerRepository;
import com.example.testproject.repository.UniversiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UniversiteServiceImpl implements IUniversiteService {

    @Autowired
    private UniversiteRepository universiteRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    @Override
    public Universite addUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite getUniversiteById(Long id) {
        return universiteRepository.findById(id).orElse(null);
    }

    @Override
    public Universite updateUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public void deleteUniversite(Long id) {
        universiteRepository.deleteById(id);
    }

    @Override
    public Universite affecterFoyerToUniversite(Universite universite) {
        if (universite.getFoyer() != null) {
            Foyer foyer = foyerRepository.findById(universite.getFoyer().getIdFoyer()).orElse(null);
            if (foyer != null) {
                universite.setFoyer(foyer);
                return universiteRepository.save(universite);
            }
        }
        return universite;
    }

    @Override
    public Universite affecterFoyerToUniversite(Long idUniversite, Foyer foyer) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite != null && foyer != null) {
            Foyer foyerCree = foyerRepository.save(foyer);
            universite.setFoyer(foyerCree);
            return universiteRepository.save(universite);
        }
        return universite;
    }

    @Override
    @Transactional
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite).orElse(null);

        if (foyer == null || universite == null) {
            return null;
        }

        if (foyer.getUniversite() != null || universite.getFoyer() != null) {
            return null;
        }

        universite.setFoyer(foyer);
        foyer.setUniversite(universite);

        foyerRepository.save(foyer);
        return universiteRepository.save(universite);
    }
    @Override
    @Transactional
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);

        if (universite == null || universite.getFoyer() == null) {
            return null;
        }

        Foyer foyer = universite.getFoyer();

        universite.setFoyer(null);
        foyer.setUniversite(null);

        universiteRepository.save(universite);
        foyerRepository.save(foyer);

        return universite;
    }
}