package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    Optional<Entreprise> findEntrepriseByCode(String codeEntreprise);
}
