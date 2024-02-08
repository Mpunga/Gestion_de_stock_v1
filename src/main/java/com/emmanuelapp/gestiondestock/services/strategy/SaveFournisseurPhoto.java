package com.emmanuelapp.gestiondestock.services.strategy;

import com.emmanuelapp.gestiondestock.model.Fournisseur;

import java.io.InputStream;

public class SaveFournisseurPhoto implements Strategy< Fournisseur >{

    @Override
    public Fournisseur savePhoto(Integer id, InputStream photo, String titre) {
        return null;
    }
}
