package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.VentesDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.VENTES_ENDPOINT;

public interface VentesApi {

    @PostMapping(VENTES_ENDPOINT + "/create")
    VentesDto save(@RequestBody VentesDto ventesDto);

    @GetMapping(VENTES_ENDPOINT + "/{idvente}")
    VentesDto findById(@PathVariable("/idvente") Integer id);

    @GetMapping(VENTES_ENDPOINT + "/{codevente}")
    VentesDto findByCode(@PathVariable("codevente") String code);

    @GetMapping(VENTES_ENDPOINT + "/all")
    List<VentesDto> findAll();

    @DeleteMapping(VENTES_ENDPOINT + "/delete/{idvente}")
    void delete(@PathVariable("idvente") Integer id);
}
