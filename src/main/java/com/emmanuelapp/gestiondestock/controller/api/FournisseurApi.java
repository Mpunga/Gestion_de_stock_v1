package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;
import static com.emmanuelapp.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

@Api(APP_ROOT + "/fournisseurs")
public interface FournisseurApi {

    @PostMapping(FOURNISSEUR_ENDPOINT + "/create")
    FournisseurDto save(@RequestBody FournisseurDto FournisseurDto);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/{idfournisseur}")
    FournisseurDto findById(@PathVariable("idfournisseur") Integer id);

   // FournisseurDto findByCodeFournisseur( String codeFournisseur);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/all")
    List<FournisseurDto> findAll();

    @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/idfournisseur")
    void delete(@PathVariable("idfournisseur") Integer id);
}
