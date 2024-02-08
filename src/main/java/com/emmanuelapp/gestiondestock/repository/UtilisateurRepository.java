package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.dto.UtilisateurDto;
import com.emmanuelapp.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findUtilisateurByCode(String codeUtilisateur);

    Optional<Utilisateur> findUtilisateurByEmail(String email);
}
