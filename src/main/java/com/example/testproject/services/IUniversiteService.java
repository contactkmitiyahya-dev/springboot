package com.example.testproject.services;

import com.example.testproject.entities.Foyer;
import com.example.testproject.entities.Universite;
import java.util.List;

public interface IUniversiteService {
    Universite addUniversite(Universite universite);
    List<Universite> getAllUniversites();
    Universite getUniversiteById(Long id);
    Universite updateUniversite(Universite universite);
    void deleteUniversite(Long id);
    Universite affecterFoyerToUniversite(Universite universite);
    Universite affecterFoyerToUniversite(Long idUniversite, Foyer foyer);
    Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite);
    Universite desaffecterFoyerAUniversite(long idUniversite);
}