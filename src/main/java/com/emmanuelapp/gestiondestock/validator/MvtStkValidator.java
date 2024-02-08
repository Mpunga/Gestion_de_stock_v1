package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.CategoryDto;
import com.emmanuelapp.gestiondestock.dto.MvtStkDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {

    public static List<String> validate(MvtStkDto dto){
        List<String> errors = new ArrayList<>();
        if(dto == null){
            errors.add("Veuillez renseigner la date du mouvement");
            errors.add("Veuillez renseigner la quantité du mouvement");
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner la date du mouvement");
            errors.add("Veuillez renseigner le type du mouvement");
            return errors;
        }
        if(dto.getDateMvt() == null){
            errors.add("Veuillez renseigner la date du mouvement");
        }
        if(dto.getQuantite() == null || dto.getQuantite().compareTo(BigDecimal.ZERO) == 0){
            errors.add("Veuillez renseigner la quantité du mouvement");
        }
        if(dto.getArticle() == null || dto.getArticle().getId() == null){
            errors.add("Veuillez renseigner l'article");
        }
        if(dto.getDateMvt() == null){
            errors.add("Veuillez renseigner la date du mouvement");
        }
        if(!StringUtils.hasLength(dto.getTypeMvtStk().name())){
            errors.add("Veuillez renseigner le type du mouvement");
        }
        return errors;
    }
}
