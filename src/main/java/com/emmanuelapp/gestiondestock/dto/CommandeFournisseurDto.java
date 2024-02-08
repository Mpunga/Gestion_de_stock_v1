package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.CommandeFournisseur;
import com.emmanuelapp.gestiondestock.model.EtatCommande;
import com.emmanuelapp.gestiondestock.model.Fournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeFournisseurDto {

    private Integer id;

    private String code;

    private EtatCommande etatCommande;

    private Instant dateCommande;

    private Fournisseur fournisseur;

    private Integer idEntreprise;


    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseur;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur){
        if(commandeFournisseur == null){
            return null;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .dateCommande(commandeFournisseur.getDateCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .ligneCommandeFournisseur(
                        commandeFournisseur.getLigneCommandeFournisseurs() != null ?
                                commandeFournisseur.getLigneCommandeFournisseurs().stream()
                                        .map(LigneCommandeFournisseurDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }
    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto){
        if(commandeFournisseurDto == null){
            return null;
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise());
        return commandeFournisseur;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
