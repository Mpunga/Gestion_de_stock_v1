package com.emmanuelapp.gestiondestock.services;

import com.emmanuelapp.gestiondestock.dto.FournisseurDto;

import java.util.List;

public interface FournisseurService {

    FournisseurDto save(FournisseurDto FournisseurDto);

    FournisseurDto findById(Integer id);

    FournisseurDto findByCodeFournisseur(String codeFournisseur);

    List<FournisseurDto> findAll();

    void delete(Integer id);
}
