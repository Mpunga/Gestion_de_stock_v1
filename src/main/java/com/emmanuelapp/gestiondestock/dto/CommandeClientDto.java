package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.CommandeClient;
import com.emmanuelapp.gestiondestock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private ClientDto client;

    private Integer idEntreprise;

    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCommandeClients;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {

        if(commandeClient == null){
            return null;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
               .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .idEntreprise(commandeClient.getIdEntreprise())
                .ligneCommandeClients(
                        commandeClient.getLigneCommandeClients() != null ?
                                commandeClient.getLigneCommandeClients().stream()
                                        .map(LigneCommandeClientDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }
    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){
        if(commandeClientDto == null){
            return null;
        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());

        return commandeClient;
    }

   public boolean isCommandeLivree(){
       return EtatCommande.LIVREE.equals(this.etatCommande);
     }
}
