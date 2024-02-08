package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.Adresse;
import com.emmanuelapp.gestiondestock.model.Entreprise;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class EntrepriseDto {

    private Integer id;

    private String nom;

    private String description;

    private Adresse adresse;

    private String codeFiscal;

    private String photo;

    private String email;

    private String numTel;

    private String siteWeb;

    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise){
        if(entreprise == null){
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .siteWeb(entreprise.getSiteWeb())
                .utilisateurs(
                        entreprise.getUtilisateurs() != null ?
                                entreprise.getUtilisateurs().stream()
                                        .map(UtilisateurDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto){
        if(entrepriseDto == null){
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSiteWeb(entrepriseDto.getSiteWeb());
        return entreprise;
    }
}
