package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.Adresse;
import com.emmanuelapp.gestiondestock.model.Fournisseur;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FournisseurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private Adresse adresse;

    private String photo;

    private String mail;

    private String numTel;

    private Integer idEntreprise;

    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if(fournisseur == null){
            return null;
        }
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .photo(fournisseur.getPhoto())
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getNumTel())
                .commandeFournisseurs(
                        fournisseur.getCommandeFournisseurs() != null ?
                                fournisseur.getCommandeFournisseurs().stream()
                                        .map(CommandeFournisseurDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }
    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if(fournisseurDto == null){
            return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseur.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        return fournisseur;
    }
}
