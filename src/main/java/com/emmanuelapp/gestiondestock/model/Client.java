package com.emmanuelapp.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client extends AbstractEntity{

    @Column(name = "codeclient")
    private String codeClient;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Embedded
    private Adresse adresse;

    @Column(name = "photo")
    private String photo;

    @Column(name = "mail")
    private String mail;

    @Column(name = "numtel")
    private String numTel;

    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients;

}
