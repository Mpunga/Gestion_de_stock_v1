package com.emmanuelapp.gestiondestock.services.strategy;

import com.emmanuelapp.gestiondestock.model.Utilisateur;

import java.io.InputStream;

public class SaveUtilisateurPhoto implements Strategy< Utilisateur >{

    @Override
    public Utilisateur savePhoto(Integer id, InputStream photo, String titre) {
        return null;
    }
}
