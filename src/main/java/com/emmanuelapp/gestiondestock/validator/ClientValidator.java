package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.AdresseDto;
import com.emmanuelapp.gestiondestock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientDto clientDto){
        List<String> errors = new ArrayList<>();

        if(clientDto == null){
            errors.add("Veuillez reseinger le nom du client");
            errors.add("Veuillez reseinger le prenom du client");
            errors.add("Veuillez reseinger le Mail du client");
            errors.add("Veuillez reseinger le numero de telephone du client");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(clientDto.getNom())){
            errors.add("Veuillez reseinger le nom du client");
        }
        if(!StringUtils.hasLength(clientDto.getPrenom())){
            errors.add("Veuillez reseinger le prenom du client");
        }
        if(!StringUtils.hasLength(clientDto.getMail())){
            errors.add("Veuillez reseinger le Mail du client");
        }
        if(!StringUtils.hasLength(clientDto.getNumTel())){
            errors.add("Veuillez reseinger le numero de telephone du client");
        }
        errors.addAll(AdresseValidator.validate(AdresseDto.fromEntity(clientDto.getAdresse())));
        return errors;
    }
}
