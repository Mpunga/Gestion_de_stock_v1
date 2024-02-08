package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.ClientDto;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.Client;
import com.emmanuelapp.gestiondestock.model.CommandeClient;
import com.emmanuelapp.gestiondestock.repository.ClientRepository;
import com.emmanuelapp.gestiondestock.repository.CommandeClientRepository;
import com.emmanuelapp.gestiondestock.services.ClientService;
import com.emmanuelapp.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository) {
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {

        List<String> errors = ClientValidator.validate(clientDto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", clientDto);
            throw new InvalidEntityException("le Client n'est pas valide", ErrorCodes.CLIENT_NOT_VALIDE, errors);
        }
        return ClientDto.fromEntity(clientRepository
                .save(ClientDto.toEntity(clientDto)));

    }

    @Override
    public ClientDto findById(Integer id) {

        if (id == null) {
            log.error("Client ID is null");
            return null;
        }

        Optional<Client> client = clientRepository.findById(id);

        ClientDto clientDto = ClientDto.fromEntity(client.get());

        return Optional.of(clientDto).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun client avec l'ID = " + id + "n'ete trouve dans la BDD", ErrorCodes.CLIENT_NOT_FOUND
                )
        );
    }

    @Override
    public ClientDto findByCodeClientDto(String codeClient) {

        if (!StringUtils.hasLength(codeClient)) {
            log.error("Client CODE is null");
            return null;
        }

        Optional<Client> client = clientRepository.findClientByCodeClient(codeClient);

        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun client avec CODE = " + codeClient + " n' ete trouvé dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public List<ClientDto> findAll() {

        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        List< CommandeClient > commandeClients = commandeClientRepository.findAllByClientId(id);
        if(!commandeClients.isEmpty()){
            throw new InvalidEntityException("Impossible de supprimer le client qui a deja une commande", ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        clientRepository.deleteById(id);
    }
}
