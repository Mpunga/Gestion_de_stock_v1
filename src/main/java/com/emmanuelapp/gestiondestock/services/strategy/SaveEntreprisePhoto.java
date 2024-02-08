package com.emmanuelapp.gestiondestock.services.strategy;

import com.emmanuelapp.gestiondestock.model.Entreprise;

import java.io.InputStream;

public class SaveEntreprisePhoto implements Strategy< Entreprise >{

    @Override
    public Entreprise savePhoto(Integer id, InputStream photo, String titre) {
        return null;
    }
}
