package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;
import static com.emmanuelapp.gestiondestock.utils.Constants.UTILISATEUR_ENDPOINT;

@Api(APP_ROOT + "/Utilisateurs")
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
