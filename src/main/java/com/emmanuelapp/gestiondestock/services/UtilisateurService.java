package com.emmanuelapp.gestiondestock.services;


import com.emmanuelapp.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.emmanuelapp.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService{

    UtilisateurDto save(UtilisateurDto utilisateurDto);

    UtilisateurDto findById(Integer id);

    UtilisateurDto findByCodeUtilisateur(String codeUtilisateur);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    UtilisateurDto findByEmail(String email);

   UtilisateurDto cangerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
