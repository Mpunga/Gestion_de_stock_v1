package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {
   // Optional<LigneVente> findAllByArticleId(Integer idArticle);

    List<LigneVente> findAllByArticleId(Integer idArticle);
}
