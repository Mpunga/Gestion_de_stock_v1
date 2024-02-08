package com.emmanuelapp.gestiondestock.controller;

import com.emmanuelapp.gestiondestock.controller.api.CommandeFournisseurApi;
import com.emmanuelapp.gestiondestock.dto.CommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.model.EtatCommande;
import com.emmanuelapp.gestiondestock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    private CommandeFournisseurService commandeFournisseurService;

    @Autowired
    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {
        return commandeFournisseurService.save(commandeFournisseurDto);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return commandeFournisseurService.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeFournisseurService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseurs) {
        return commandeFournisseurService.updateFournisseur(idCommande, idFournisseurs);
    }


    @Override
    public  CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return commandeFournisseurService.updateArticle(idCommande, idLigneCommande, idArticle);
    }



    @Override
    public  CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return commandeFournisseurService.deleteArticle(idCommande, idLigneCommande);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        return commandeFournisseurService.findById(id);
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        return commandeFournisseurService.findByCode(code);
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurService.findAll();
    }

    /*
     @Override
    public  CommandeFournisseurDto findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ResponseEntity.ok(commandeFournisseurService.findAllLignesCommandesFournisseurByCommandeClientId(idCommande));
    }


     */
    @Override
    public void delete(Integer id) {
        commandeFournisseurService.delete(id);
    }
}
