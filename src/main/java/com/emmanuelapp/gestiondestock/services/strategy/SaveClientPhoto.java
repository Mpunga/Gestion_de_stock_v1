package com.emmanuelapp.gestiondestock.services.strategy;

import com.emmanuelapp.gestiondestock.model.Client;

import java.io.InputStream;

public class SaveClientPhoto implements Strategy< Client >{
    @Override
    public Client savePhoto(Integer id, InputStream photo, String titre) {
        return null;
    }
}
