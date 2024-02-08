package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.*;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.exception.InvalidOperationException;
import com.emmanuelapp.gestiondestock.model.*;
import com.emmanuelapp.gestiondestock.repository.ArticleRepository;
import com.emmanuelapp.gestiondestock.repository.ClientRepository;
import com.emmanuelapp.gestiondestock.repository.CommandeClientRepository;
import com.emmanuelapp.gestiondestock.repository.LigneCommandeClientRepository;
import com.emmanuelapp.gestiondestock.services.CommandeClientService;
import com.emmanuelapp.gestiondestock.services.MvtStkService;
import com.emmanuelapp.gestiondestock.validator.ArticleValidator;
import com.emmanuelapp.gestiondestock.validator.CommandeClientValidator;
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
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,
                                     LigneCommandeClientRepository ligneCommandeClientRepository,
                                     ClientRepository clientRepository,
                                     ArticleRepository articleRepository, MvtStkService mvtStkService) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {

     List<String> errors = CommandeClientValidator.validate(commandeClientDto);

     if(!errors.isEmpty()){
         log.error("Commande client n'est pas valide");
         throw new InvalidEntityException("la Commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALIDE, errors);
     }

     if(commandeClientDto != null && commandeClientDto.isCommandeLivree()){
          throw new InvalidOperationException("Impossible de modier la commande lorsqu'elle est livrée"
                  ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }

        Optional<Client> client = clientRepository.findById(commandeClientDto.getClient().getId());
        if(client.isEmpty()){
            log.warn("Client with ID {] was not found in de BD", commandeClientDto.getId());
            throw new EntityNotFoundException("Aucun client avec l'ID" + commandeClientDto.getClient().getId()
                    + " n'a été trouver dans la BDD" ,ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if(commandeClientDto.getLigneCommandeClients() != null){
            commandeClientDto.getLigneCommandeClients().forEach(ligCmdClt ->{
                if(ligCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                    if(article.isEmpty()) {
                        articleErrors.add("L'article avec ID" + ligCmdClt.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer une commande avec un article Null");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));

        if(commandeClientDto.getLigneCommandeClients() != null){

            commandeClientDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClient(savedCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);
            });
        }


        return commandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
              if(!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'etat de la Commande client is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un etat  null"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommandeClient(idCommande);
        commandeClient.setEtatCommande(etatCommande);

      CommandeClient saveCmdClt =   commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
      if(commandeClient.isCommandeLivree()){
          updateMvtStk(idCommande);
      }
        return CommandeClientDto.fromEntity(saveCmdClt);
    }


    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null){
            log.error("Commande client ID est null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Commande Client n'a été trouver avec l'ID " + id,
                         ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande client ID est null");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a ete trouver avec le CODE"
                                + code, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDto> findAll() {

        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Commande client ID est null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
       if(!ligneCommandeClients.isEmpty()){
           throw new InvalidEntityException("Impossible de supprimer la ligne de commande client avec une commande", ErrorCodes.LIGNE_COMMANDE_ALREADY_IN_USE);
       }
        commandeClientRepository.deleteById(id);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
      checkIdCommande(idCommande);
        chekIdLigneCommande(idLigneCommande);

        if(quantite == null){
            log.error("L'ID de la ligne commande is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec une ligne de commande null"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommandeClient(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
       checkIdCommande(idCommande);
        if(idClient == null){
            log.error("L'ID de Client is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un ID Client null"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

       CommandeClientDto commandeClient = checkEtatCommandeClient(idCommande);
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if(clientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "AUcun client n'a ete trouver avec l'ID "+ idClient, ErrorCodes.CLIENT_NOT_FOUND
            );
        }
        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande,
                                            Integer idArticle) {
        checkIdCommande(idCommande);
        chekIdLigneCommande(idLigneCommande);
        chekIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommandeClient(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article n'a ete trouver avec l'ID" + idArticle
                    ,ErrorCodes.ARTICLE_NOT_FOUND);
        }
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if(errors.isEmpty()){
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALIDE, errors);
        }
        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        chekIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClient = checkEtatCommandeClient(idCommande);
        // Verification de Ligne Commande Client & informer le client dans le cas ou ça n'existe pas
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    private CommandeClientDto checkEtatCommandeClient(Integer idCommande) {
        CommandeClientDto commandeClient = findById(idCommande);
        if(commandeClient .isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modier la commande lorsqu'elle est livrée"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClient;
    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande client ID is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec un ID null"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClientOptional =  ligneCommandeClientRepository.findById(idLigneCommande);
        if(ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucune ligne commande Client n'a ete trouver avec l'ID" + idLigneCommande
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return ligneCommandeClientOptional;
    }

    private void chekIdLigneCommande(Integer idLigneCommande){
        if(idLigneCommande == null){
            log.error("L'ID de la ligne commande is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec une quantité null ou zero"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void chekIdArticle(Integer idArticle, String msg){
        if(idArticle == null){
            log.error("L'ID de "+ msg +" is Null");
            throw new InvalidOperationException("Impossible de modier l'etat de la commande avec  un "+ msg +" ID article null"
                    ,ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande){
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig -> {
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvtStk(TypeMvtStk.SORTIE)
                    .sourceMvtStk(SourceMvtStk.COMMANDE_CLIENT)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.sortieStock(mvtStkDto);
        });
    }
}
