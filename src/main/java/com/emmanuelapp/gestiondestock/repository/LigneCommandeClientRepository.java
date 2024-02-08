package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.model.LigneCommandeClient;
import com.emmanuelapp.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {

    List<LigneCommandeClient> findAllByCommandeClientId(Integer idCommande);

    //  List<LigneCommandeClient> findAllByCommandeClientId(Integer id);

    List< LigneCommandeFournisseur > findAllByArticleId(Integer idArticle);

}
