package com.emmanuelapp.gestiondestock.services;

import com.emmanuelapp.gestiondestock.dto.ArticleDto;
import com.emmanuelapp.gestiondestock.dto.LigneCommandeClientDto;
import com.emmanuelapp.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.emmanuelapp.gestiondestock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto articleDto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

   List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

  // List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

   // List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);

    void delete(Integer id);
}
