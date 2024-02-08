package com.emmanuelapp.gestiondestock.services;

import com.emmanuelapp.gestiondestock.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto clientDto);

    ClientDto findById(Integer id);

    ClientDto findByCodeClientDto(String codeClient);

    List<ClientDto> findAll();

    void delete(Integer id);
}
