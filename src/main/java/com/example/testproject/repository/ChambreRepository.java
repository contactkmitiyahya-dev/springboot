package com.example.testproject.repository;

import com.example.testproject.entities.Chambre;
import com.example.testproject.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    List<Chambre> findByNumeroChambreIn(List<Long> numeroChambres);

    // SOLUTION 1: Keywords
    List<Chambre> findByBlocIdBlocAndTypeC(long idBloc, TypeChambre typeC);

    // SOLUTION 2: JPQL
    //@Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = :idBloc AND c.typeC = :typeC")
    //List<Chambre> findChambresByBlocAndType(@Param("idBloc") long idBloc, @Param("typeC") TypeChambre typeC);
}