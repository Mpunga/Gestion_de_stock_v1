package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.LigneVente;
import com.emmanuelapp.gestiondestock.model.Ventes;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class VentesDto {

    private Integer id;

    private String code;

    private Instant dateVente;

    private String commentaire;

    private Integer idEntreprise;

    private List<LigneVenteDto> ligneVentes;

    public static VentesDto fromEntity(Ventes ventes){
        if(ventes == null){
            return null;
        }
        return VentesDto.builder()
                .id(ventes.getId())
                .code(ventes.getCode())
                .commentaire(ventes.getCommentaire())
                .idEntreprise(ventes.getIdEntreprise())
                .build();
    }
    public static Ventes toEntity(VentesDto ventesDto){
        if(ventesDto == null){
            return null;
        }
        Ventes ventes = new Ventes();
        ventes.setId(ventesDto.getId());
        ventes.setCode(ventesDto.getCode());
        ventes.setCommentaire(ventesDto.getCommentaire());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());

        return ventes;
    }
}
