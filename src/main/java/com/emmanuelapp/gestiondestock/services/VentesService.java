package com.emmanuelapp.gestiondestock.services;

import com.emmanuelapp.gestiondestock.dto.VentesDto;

import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto ventesDto);

    VentesDto findById(Integer id);

    VentesDto findByCode(String code);
    
    List<VentesDto> findAll();

    void delete(Integer id);
}
