package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.MvtStk;
import com.emmanuelapp.gestiondestock.model.SourceMvtStk;
import com.emmanuelapp.gestiondestock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStkDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ArticleDto article;

    private TypeMvtStk typeMvtStk;

    private SourceMvtStk sourceMvtStk;

    private Integer idEntreprise;

    public static MvtStkDto fromEntity(MvtStk mvtStk){
        if(mvtStk == null){
            return null;
        }
        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .article(ArticleDto.fromEntity(mvtStk.getArticle()))
                .typeMvtStk(mvtStk.getTypeMvtStk())
                .sourceMvtStk(mvtStk.getSourceMvtStk())
                .idEntreprise(mvtStk.getIdEntreprise())
                .build();
    }
    public static MvtStk toEntity(MvtStkDto mvtStkDto){
        if(mvtStkDto == null){
            return null;
        }
        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(mvtStkDto.getId());
        mvtStk.setDateMvt(mvtStkDto.getDateMvt());
        mvtStk.setQuantite(mvtStkDto.getQuantite());
        mvtStk.setArticle(ArticleDto.toEntity(mvtStkDto.getArticle()));
        mvtStk.setTypeMvtStk(mvtStkDto.getTypeMvtStk());
        mvtStk.setSourceMvtStk(mvtStkDto.getSourceMvtStk());
        mvtStk.setIdEntreprise(mvtStkDto.getIdEntreprise());

        return mvtStk;
    }
}
