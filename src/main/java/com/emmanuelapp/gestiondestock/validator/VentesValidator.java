package com.emmanuelapp.gestiondestock.validator;

import com.emmanuelapp.gestiondestock.dto.CommandeClientDto;
import com.emmanuelapp.gestiondestock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VentesValidator {

    public static List<String> validate(VentesDto ventesDto){
        List<String> errors = new ArrayList<>();
        if(ventesDto == null){
            errors.add("Veuillez renseigner le code de la vente");
            errors.add("Veuillez renseigner la date de la vente");

            return errors;
        }
        if(!StringUtils.hasLength(ventesDto.getCode())){
            errors.add("Veuillez renseigner le code de la vente");
        }
        if(ventesDto.getDateVente() == null) {
            errors.add("Veuillez renseigner la date de la vente");
        }

        return errors;
    }
}
