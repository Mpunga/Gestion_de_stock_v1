package com.emmanuelapp.gestiondestock.services;

import com.emmanuelapp.gestiondestock.dto.CommandeClientDto;
import com.emmanuelapp.gestiondestock.dto.CommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto);

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

   CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    // Delete article ==> LigneCommandeFournisseur
    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    void delete(Integer id);
}
