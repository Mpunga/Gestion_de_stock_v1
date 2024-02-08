package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.*;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.exception.InvalidOperationException;
import com.emmanuelapp.gestiondestock.model.*;
import com.emmanuelapp.gestiondestock.repository.ArticleRepository;
import com.emmanuelapp.gestiondestock.repository.CommandeFournisseurRepository;
import com.emmanuelapp.gestiondestock.repository.FournisseurRepository;
import com.emmanuelapp.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.emmanuelapp.gestiondestock.services.CommandeFournisseurService;
import com.emmanuelapp.gestiondestock.services.MvtStkService;
import com.emmanuelapp.gestiondestock.validator.ArticleValidator;
import com.emmanuelapp.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                                          FournisseurRepository fournisseurRepository,
                                          ArticleRepository articleRepository, MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {

        List<String> errors = CommandeFournisseurValidator.validate(commandeFournisseurDto);

        if(!errors.isEmpty()){
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException("La commande fournisseur n'est pas valide",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALIDE, errors);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getFournisseur().getId());
        if(fournisseur.isEmpty()){
            log.warn("Fournisseur with ID {} was not found in the BD", commandeFournisseurDto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec l'ID" + commandeFournisseurDto.getFournisseur().getId()
            + "n'a ete trouver dans le BD", ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if(commandeFournisseurDto.getLigneCommandeFournisseur() != null){
            commandeFournisseurDto.getLigneCommandeFournisseur().forEach(ligCmdFrs -> {
                if(ligCmdFrs.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticle().getId());
                    if(article.isEmpty()){
                        articleErrors.add("L'article avec l'ID" + ligCmdFrs.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer une commande avec article Null");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeFournisseur savedCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));

        if(commandeFournisseurDto.getLigneCommandeFournisseur() != null){
            commandeFournisseurDto.getLigneCommandeFournisseur().forEach(ligCmdFrs -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs);
            });
        }

        return CommandeFournisseurDto.fromEntity(savedCmdFrs);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if(!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'etat de la Commande fournisseur is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un etat  null"
                    ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommandeFournisseur(idCommande);

        commandeFournisseur.setEtatCommande(etatCommande);
       CommandeFournisseur savedCmdF =  commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
       if(commandeFournisseur.isCommandeLivree()){
           // updateMvtStk(idCommande);
       }
        return CommandeFournisseurDto.fromEntity(savedCmdF);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        chekIdLigneCommande(idLigneCommande);

        if(quantite == null){
            log.error("L'ID de la ligne commande is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec une ligne de commande null"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommandeFournisseur(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

        @Override
        public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
            checkIdCommande(idCommande);
            if(idFournisseur == null){
                log.error("L'ID de Fournisseur is Null");
                throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un ID Client null"
                        ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
            }

            CommandeFournisseurDto commandeFournisseur = checkEtatCommandeFournisseur(idCommande);
            Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
            if(fournisseurOptional.isEmpty()){
                throw new EntityNotFoundException(
                        "AUcun fournisseur n'a ete trouver avec l'ID "+ idFournisseur, ErrorCodes.FOURNISSEUR_NOT_FOUND
                );
            }
            //commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

            return CommandeFournisseurDto.fromEntity(
                    commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
            );
        }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        chekIdLigneCommande(idLigneCommande);
        chekIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommandeFournisseur(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article n'a ete trouver avec l'ID" + idArticle
                    ,ErrorCodes.ARTICLE_NOT_FOUND);
        }
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if(errors.isEmpty()){
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALIDE, errors);
        }
        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        chekIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommandeFournisseur(idCommande);
        // Verification de Ligne Commande Fournisseur & informer le client dans le cas ou ça n'existe pas
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Commande client ID is null");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouver avec l'ID"+ id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande fournisseur code is null");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "AUcune commande fournisseur n'a ete trouver avec le CODE"+ code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {

        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }



    @Override
    public void delete(Integer id) {

        if(id == null){
            log.error("Commande fournisseur ID is Null");
            return ;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidEntityException("Impossible de supprimer la ligne de commande fournisseur avec une commande en cours", ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    private CommandeFournisseurDto checkEtatCommandeFournisseur(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if(commandeFournisseur .isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modier la commande lorsqu'elle est livrée"
                    ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande fournisseur ID is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un ID null"
                    ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional =  ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if(ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucune ligne commande FOurnisseur n'a ete trouver avec l'ID" + idLigneCommande
                    ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void chekIdLigneCommande(Integer idLigneCommande){
        if(idLigneCommande == null){
            log.error("L'ID de la ligne commande is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec une quantité null ou zero"
                    ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void chekIdArticle(Integer idArticle, String msg){
        if(idArticle == null){
            log.error("L'ID de "+ msg +" is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un "+ msg +" ID article null"
                    ,ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }
/*
    private void updateMvtStk(Integer idCommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs =
                ligneCommandeFournisseurRepository.findAllByCommandeFournisseurById(idCommande);
        ligneCommandeFournisseurs.forEach(lig -> {
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvtStk(TypeMvtStk.ENTREE)
                    .sourceMvtStk(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.entreeStock(mvtStkDto);
        });
    }


 */

}
