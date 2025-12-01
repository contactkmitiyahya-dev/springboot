package com.example.testproject.repository;

import com.example.testproject.entities.ProjetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetDetailRepository extends JpaRepository<ProjetDetail, Long> {
}
