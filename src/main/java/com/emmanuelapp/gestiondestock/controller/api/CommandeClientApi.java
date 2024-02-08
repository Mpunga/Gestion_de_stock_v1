package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.CommandeClientDto;
import com.emmanuelapp.gestiondestock.dto.LigneCommandeClientDto;
import com.emmanuelapp.gestiondestock.model.CommandeClient;
import com.emmanuelapp.gestiondestock.model.EtatCommande;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;


public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/commandesclients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto commandeClientDto);

    @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer
                                     idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande,
                                        @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande")Integer idCommande,
                                                @PathVariable("idClient") Integer idClient);

    @PatchMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande")Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                    @PathVariable("idArticle")Integer idArticle);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande")Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(APP_ROOT + "/commandesclients/{idcommandeclient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idcommandeclient);

    @GetMapping(APP_ROOT + "/commandesclients/{codecommandeclient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codecommandeclient") String code);

    @GetMapping(APP_ROOT + "/commandesclients/all")
    ResponseEntity <List<CommandeClientDto>> findAll();

    @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
    ResponseEntity <List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/{idcommandeclient}")
    ResponseEntity delete(@PathVariable("idcommandeclient") Integer id);
}
