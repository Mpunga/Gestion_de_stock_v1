package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.ClientDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;

public interface ClientApi {

    @PostMapping(value = APP_ROOT + "client/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto save(@RequestBody ClientDto clientDto);

    @GetMapping(value = APP_ROOT + "/clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/clients/{codeClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findByCodeClient(@PathVariable("codeClient") String codeClient);

    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "clients/delete/{idClient}")
    void delete(@PathVariable("idClient") Integer id);
}
