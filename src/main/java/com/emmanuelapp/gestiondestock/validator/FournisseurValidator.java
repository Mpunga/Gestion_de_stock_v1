package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.AdresseDto;
import com.emmanuelapp.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto fournisseurDto){
        List<String> errors = new ArrayList<>();

        if(fournisseurDto == null){
            errors.add("Veuillez reseinger le nom du fournisseur");
            errors.add("Veuillez reseinger le prenom du fournisseur");
            errors.add("Veuillez reseinger le Mail du fournisseur");
            errors.add("Veuillez reseinger le numero de telephone du fournisseur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(fournisseurDto.getNom())){
            errors.add("Veuillez reseinger le nom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getPrenom())){
            errors.add("Veuillez reseinger le prenom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getMail())){
            errors.add("Veuillez reseinger le Mail du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getNumTel())){
            errors.add("Veuillez reseinger le numero de telephone du fournisseur");
        }
        errors.addAll(AdresseValidator.validate(AdresseDto.fromEntity(fournisseurDto.getAdresse())));
        return errors;
    }
}
