package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.*;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.Article;
import com.emmanuelapp.gestiondestock.model.LigneCommandeClient;
import com.emmanuelapp.gestiondestock.model.LigneCommandeFournisseur;
import com.emmanuelapp.gestiondestock.model.LigneVente;
import com.emmanuelapp.gestiondestock.repository.*;
import com.emmanuelapp.gestiondestock.services.ArticleService;
import com.emmanuelapp.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private CommandeClientRepository commandeClientRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              LigneVenteRepository ligneVenteRepository,
                              LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                              LigneCommandeClientRepository ligneCommandeClientRepository,
                              CommandeFournisseurRepository commandeFournisseurRepository,
                              CommandeClientRepository commandeClientRepository) {
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {

        List<String> errors = ArticleValidator.validate(articleDto);
        if(!errors.isEmpty()){
            log.error("Article is not valid {}", articleDto);
            throw new InvalidEntityException("l'Article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALIDE, errors);
        }
        return ArticleDto.fromEntity(articleRepository
                .save(ArticleDto.toEntity(articleDto)));
    }

   @Override
  public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
      return ligneVenteRepository.findAllByArticleId(idArticle).stream()
               .map(LigneVenteDto::fromEntity)
               .collect(Collectors.toList());

   }
/*
   @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
        .map(LigneCommandeClientDto::fromEntity)
        .collect(Collectors.toList());
    }


    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
        .map(LigneCommandeFournisseurDto::fromEntity)
        .collect(Collectors.toList());
    }

 */

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public ArticleDto findById(Integer id) {

        if(id == null){
            log.error("Article ID is null");
            return null;
        }

        Optional<Article> article = articleRepository.findById(id);

       // ArticleDto articleDto = ArticleDto.fromEntity(article.get());

        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID = "+ id + "n'ete trouve dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {

        if(!StringUtils.hasLength(codeArticle)){
            log.error("Article CODE is null");
            return null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);

        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec CODE = " + codeArticle + " n' ete trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public List<ArticleDto> findAll() {

        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }



    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return ;
        }
        List< LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllById(id);
        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidEntityException("Impossible de supprimer un article deja utilisé dans une commande client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List< LigneCommandeFournisseur > ligneCommandeFournisseurs = commandeFournisseurRepository.findAllById(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidEntityException("Impossible de supprimer un article deja utilisé dans une commande fournisseur", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List< LigneVente > ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidEntityException("Impossible de supprimer un article deja utilisé dans la ligne de vente", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        articleRepository.deleteById(id);
    }
}

