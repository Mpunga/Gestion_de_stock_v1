package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.UtilisateurDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.UTILISATEUR_ENDPOINT;

public interface UtilisateurApi {

    @PostMapping(UTILISATEUR_ENDPOINT + "/create")
    UtilisateurDto save(@RequestBody UtilisateurDto utilisateurDto);

    @GetMapping(UTILISATEUR_ENDPOINT + "{idutilisateur}")
    UtilisateurDto findById(@PathVariable("idutilisateur") Integer id);

    //UtilisateurDto findByCodeUtilisateur(String codeUtilisateur);

    @GetMapping(UTILISATEUR_ENDPOINT + "/all")
    List<UtilisateurDto> findAll();

    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{idutilisateur}")
    void delete(@PathVariable("idutilisateur") Integer id);
}
