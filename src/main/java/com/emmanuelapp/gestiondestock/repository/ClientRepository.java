package com.emmanuelapp.gestiondestock.repository;

import com.emmanuelapp.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByCodeClient(String codeClient);
}
