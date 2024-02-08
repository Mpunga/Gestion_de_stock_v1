package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.dto.CommandeClientDto;
import com.emmanuelapp.gestiondestock.dto.LigneCommandeClientDto;
import com.emmanuelapp.gestiondestock.model.CommandeClient;
import com.emmanuelapp.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {

   Optional<CommandeClient>  findCommandeClientByCode(String code);

    List< LigneCommandeClient> findAllById(Integer id);

    List< CommandeClient> findAllByClientId(Integer id);

    //  Optional<LigneCommandeClient> findAllByCommandeClientId(Integer idArticle);

     //Optional<CommandeClient> findAllById(Integer idArticle);
}
