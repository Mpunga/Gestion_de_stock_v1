package com.emmanuelapp.gestiondestock.controller;

import com.emmanuelapp.gestiondestock.controller.api.ClientApi;
import com.emmanuelapp.gestiondestock.dto.ClientDto;
import com.emmanuelapp.gestiondestock.services.ClientService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @Override
    public ClientDto save(ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public ClientDto findByCodeClient(String codeClient) {
        return clientService.findByCodeClientDto(codeClient);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }
}
