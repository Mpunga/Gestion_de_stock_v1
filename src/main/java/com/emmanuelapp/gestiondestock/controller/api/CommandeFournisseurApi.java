package com.emmanuelapp.gestiondestock.controller.api;


import com.emmanuelapp.gestiondestock.dto.CommandeClientDto;
import com.emmanuelapp.gestiondestock.dto.CommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.*;

@Api(APP_ROOT + "/commandeFournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto);

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/commandesfournisseurs/update/etat/{idCommande}/{etatCommande}")
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/commandesfournisseurs/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer
                                                                     idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                             @PathVariable("quantite") BigDecimal quantite);

   @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "update/fournisseur/{idCommande}/{idFournisseurs}")
   CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande")Integer idCommande,
                                                @PathVariable("idFournisseurs") Integer idFournisseurs);

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
                                                    CommandeFournisseurDto updateArticle(@PathVariable("idCommande")Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                    @PathVariable("idArticle")Integer idArticle);

    @DeleteMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande")Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    CommandeFournisseurDto findById(@PathVariable("idcommandefournisseur") Integer id);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    CommandeFournisseurDto findByCode(@PathVariable("codecommandefournisseur")String code);

    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    List<CommandeFournisseurDto> findAll();

    // @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/ligneCommande/{idCommande}")
   //  CommandeFournisseurDto findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);


    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    void delete(@PathVariable("idcommandefournisseur")Integer id);
}
