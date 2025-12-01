package com.example.testproject.services;

import com.example.testproject.entities.Foyer;
import java.util.List;

public interface IFoyerService {
    Foyer addFoyer(Foyer foyer);
    List<Foyer> getAllFoyers();
    Foyer getFoyerById(long id);
    Foyer updateFoyer(Foyer foyer);
    void deleteFoyer(long id);
    Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite);
}