package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.Article;
import com.emmanuelapp.gestiondestock.model.LigneVente;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ArticleDto {

    private Integer id;

    private String codeArticle;

    private String designation;

    private BigDecimal prixUnitaireHt;

    private BigDecimal tauxTva;

    private BigDecimal prixUnitaireTtc;

    private String photo;

    private CategoryDto category;

    private Integer idEntreprise;

    private List< LigneVente > ligneVentes;

    public static ArticleDto fromEntity(Article article){
        if(article == null){
            return null;
        }
        return  ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .photo(article.getPhoto())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .tauxTva(article.getTauxTva())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .ligneVentes((List< LigneVente >) LigneVenteDto.fromEntity((LigneVente) article.getLigneVentes()))
                .build();
    }
    public static Article toEntity(ArticleDto articleDto){
        if(articleDto == null){
            return null;
        }
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPhoto(articleDto.getPhoto());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setTauxTva(articleDto.getTauxTva());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));

        return article;
    }
}
