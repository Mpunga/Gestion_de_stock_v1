package com.emmanuelapp.gestiondestock.services.strategy;

import com.emmanuelapp.gestiondestock.model.Article;
import com.emmanuelapp.gestiondestock.services.ArticleService;
import com.emmanuelapp.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class SaveArticlePhoto implements Strategy< Article >{


    @Override
    public Article savePhoto(Integer id, InputStream photo, String titre) {
        return null;
    }
}
