package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.AdresseDto;
import com.emmanuelapp.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDto entrepriseDto){
        List<String> errors = new ArrayList<>();

        if(entrepriseDto == null){
            errors.add("Veillez renseigner le nom de l'entreprise");
            errors.add("Veillez renseigner la description de l'entreprise");
            errors.add("Veillez renseigner l'adresse de l'entreprise");
            errors.add("Veillez renseigner le code postale de l'entreprise");
            errors.add("Veillez renseigner l'email de l'entreprise");
            errors.add("Veillez renseigner le numero de telephone");
            errors.add("Veillez renseigner le site web de l'entreprise");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(entrepriseDto.getNom())){
            errors.add("Veillez renseigner le nom de l'entreprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getDescription())){
            errors.add("Veillez renseigner la description de l'entreprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getEmail())){
            errors.add("Veillez renseigner l'email de l'entriprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getSiteWeb())){
            errors.add("Veillez renseigner le site web de l'entreprise");
        }
        if(entrepriseDto.getAdresse() == null){
            errors.add("Veillez renseigner l'Adresse d'utlisateur");
        } else {
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getAdresse1())){
                errors.add("le champs 'adresse 1' est obligatoir");
            }
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getVille())){
                errors.add("le champs 'ville' est obligatoir");
            }
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getCodePostale())){
                errors.add("le champs 'Code postale' est obligatoir");
            }
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getPays())){
                errors.add("le champs 'pays' est obligatoir");
            }
        }

        errors.addAll(AdresseValidator.validate(AdresseDto.fromEntity(entrepriseDto.getAdresse())));
        return errors;
    }
}
