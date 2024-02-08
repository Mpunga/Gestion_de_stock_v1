package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.AdresseDto;
import com.emmanuelapp.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();

        if(utilisateurDto == null){
            errors.add("Veillez renseigner le nom d'utlisateur");
            errors.add("Veillez renseigner le prenom d'utlisateur");
            errors.add("Veillez renseigner l'Email d'utlisateur");
            errors.add("Veillez renseigner le mot de passe d'utlisateur");
            errors.add("Veillez renseigner l'Adresse d'utlisateur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("Veillez renseigner le nom d'utlisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("Veillez renseigner le prenom d'utlisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veillez renseigner l'Email d'utlisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("Veillez renseigner le mot de passe d'utlisateur");
        }
        if(utilisateurDto.getDateNaissance() == null){
            errors.add("Veillez renseigner la date de naissance d'utlisateur");
        }
        errors.addAll(AdresseValidator.validate(AdresseDto.fromEntity(utilisateurDto.getAdresse())));
        return errors;
    }
}
