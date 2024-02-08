package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto articleDto){
        List<String> errors = new ArrayList<>();

        if(articleDto == null){
            errors.add("Veuillez reseinger le code de l'article");
            errors.add("Veuillez reseinger la designation de l'article");
            errors.add("Veuillez reseinger le prix unitaire HT de l'article");
            errors.add("Veuillez reseinger le TVA de l'article");
            errors.add("Veuillez reseinger le prix unitaire TTC de l'article");
            errors.add("Veuillez selectionner la categorie");
            return errors;
        }
        if(!StringUtils.hasLength(articleDto.getCodeArticle())){
            errors.add("Veuillez reseinger le code de l'article");
        }
        if(!StringUtils.hasLength(articleDto.getDesignation())){
            errors.add("Veuillez reseinger la designation de l'article");
        }
        if(articleDto.getPrixUnitaireHt() == null){
            errors.add("Veuillez reseinger le prix unitaire HT de l'article");
        }
        if(articleDto.getTauxTva() == null){
            errors.add("Veuillez reseinger le TVA de l'article");
        }
        if(articleDto.getPrixUnitaireTtc() == null){
            errors.add("Veuillez reseinger le prix unitaire TTC de l'article");
        }
        if(articleDto.getCategory() == null){
            errors.add("Veuillez selectionner la categorie");
        }
        return errors;
    }
}
