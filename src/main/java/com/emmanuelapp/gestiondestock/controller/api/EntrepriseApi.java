package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;
import static com.emmanuelapp.gestiondestock.utils.Constants.ENTREPRISE_ENDPOINT;

@Api(APP_ROOT + "/entreprises")
public interface EntrepriseApi {

    @PostMapping(ENTREPRISE_ENDPOINT + "/create")
    EntrepriseDto save(@RequestBody EntrepriseDto entrepriseDto);

    @GetMapping(ENTREPRISE_ENDPOINT + "{identreprise}")
    EntrepriseDto findById(@PathVariable("identreprise") Integer id);

   // EntrepriseDto findByCodeEntreprise(String codeEntreprise);

    @GetMapping(ENTREPRISE_ENDPOINT + "/all")
    List<EntrepriseDto> findAll();

    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{identreprise}")
    void delete(@PathVariable("identreprise") Integer id);
}
