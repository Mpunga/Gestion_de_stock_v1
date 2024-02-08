package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);

    List<LigneCommandeFournisseur> findAllByArticleId(Integer idCommande);

    // List<LigneCommandeFournisseur> findAllByCommandeFournisseurById(Integer idCommande);

    // List< LigneCommandeFournisseur> findAllByCommandeFournisseurById(Integer idCommande);
}
