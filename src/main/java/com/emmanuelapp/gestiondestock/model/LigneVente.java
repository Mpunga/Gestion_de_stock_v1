package com.emmanuelapp.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "LigneVente")
public class LigneVente extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idvente")
    private Ventes ventes;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "identreprise")
    private Integer IdEntreprise;

    @ManyToOne
    @JoinColumn(name = "idarticle")
    public Article article;

}
