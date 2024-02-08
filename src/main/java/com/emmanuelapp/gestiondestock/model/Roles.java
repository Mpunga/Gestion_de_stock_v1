package com.emmanuelapp.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Roles")
public class Roles extends AbstractEntity{

    @Column(name = "rolename")
    private String roleName;

     @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;
}
