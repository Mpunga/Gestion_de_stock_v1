package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.Adresse;
import com.emmanuelapp.gestiondestock.model.Client;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClientDto {

    private Integer id;

    private String nom;

    private String prenom;

    @Embedded
    private Adresse adresse;

    private String photo;

    private String mail;

    private String numTel;

   // private Integer idEntreprise;

    private List<CommandeClientDto> commandeClients;

    public static ClientDto fromEntity(Client client){
        if(client == null){
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .commandeClients(
                        client.getCommandeClients() != null ?
                                client.getCommandeClients().stream()
                                        .map(CommandeClientDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
             //   .idEntreprise(client.getIdEntreprise())
                .build();
    }
    public static Client toEntity(ClientDto clientDto){
        if(clientDto == null){
            return null;
        }
        Client client  = new Client();
        client.setId(client.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setPhoto(clientDto.getPhoto());
        client.setMail(clientDto.getMail());
        client.setNumTel(clientDto.getNumTel());
     //   client.setIdEntreprise(clientDto.getIdEntreprise());

        return client;
    }
}

