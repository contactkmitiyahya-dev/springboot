package com.example.testproject.services;

import com.example.testproject.entities.Chambre;
import com.example.testproject.entities.TypeChambre;

import java.util.List;

public interface IChambreService {
    Chambre addChambre(Chambre chambre);
    List<Chambre> getAllChambres();
    Chambre getChambreById(long id);
    Chambre updateChambre(Chambre chambre);
    void deleteChambre(long id);
    List<Chambre> getChambresParNomUniversite(String nomUniversite);
    List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC);
    List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type);
}