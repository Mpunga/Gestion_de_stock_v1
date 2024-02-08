package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.model.CommandeFournisseur;
import com.emmanuelapp.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    List< LigneCommandeFournisseur> findAllById(Integer id);


  //  List< CommandeFournisseur> findAllByIdFournisseur(Integer id);

    // List<LigneCommandeFournisseur> findAllByArticleId(Integer id);
}
