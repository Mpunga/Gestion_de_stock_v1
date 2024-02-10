package com.emmanuelapp.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MvtStk")
public class MvtStk extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    @Column(name = "datemvt")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "typemvtstk")
    private TypeMvtStk typeMvtStk;

    @Column(name = "sourcemvtstk")
    private SourceMvtStk sourceMvtStk;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

}
