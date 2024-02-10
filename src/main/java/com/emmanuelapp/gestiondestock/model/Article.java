package com.emmanuelapp.gestiondestock.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(name = "Article")
public class Article extends AbstractEntity{

  @Column(name = "codeartcle")
  private String codeArticle;

  @Column(name = "designation")
  private String designation;

  @Column(name = "prixunitaireht")
  private BigDecimal prixUnitaireHt;

  @Column(name = "prixunitairettc")
 private BigDecimal prixUnitaireTtc;

  @Column(name = "tauxtva")
 private BigDecimal tauxTva;

  @Column(name = "photo")
 private String photo;

  @ManyToOne
 @JoinColumn(name = "idcategory")
 private Category category;

  @OneToMany(mappedBy = "article")
  private List<LigneVente> ligneVentes;

}
