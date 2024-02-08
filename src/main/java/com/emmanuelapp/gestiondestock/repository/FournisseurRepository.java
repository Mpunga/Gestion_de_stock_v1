package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

    Optional<Fournisseur> findFournisseurByCode(String codeFournisseur);
}
