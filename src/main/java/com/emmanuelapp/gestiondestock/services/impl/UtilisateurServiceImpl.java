package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.emmanuelapp.gestiondestock.dto.UtilisateurDto;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.exception.InvalidOperationException;
import com.emmanuelapp.gestiondestock.model.Utilisateur;
import com.emmanuelapp.gestiondestock.repository.UtilisateurRepository;
import com.emmanuelapp.gestiondestock.services.UtilisateurService;
import com.emmanuelapp.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {

        List<String> errors = UtilisateurValidator.validate(utilisateurDto);
        if (!errors.isEmpty()) {
            log.error("Utilisateur is not valid {}", utilisateurDto);
            throw new InvalidEntityException("l'Utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALIDE, errors);
        }
        return UtilisateurDto.fromEntity(utilisateurRepository
                .save(UtilisateurDto.toEntity(utilisateurDto)));
    }

    @Override
    public UtilisateurDto findById(Integer id) {

        if (id == null) {
            log.error("Utilisateur ID is null");
            return null;
        }

        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);

        UtilisateurDto utilisateurDto = UtilisateurDto.fromEntity(utilisateur.get());

        return Optional.of(utilisateurDto).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec l'ID = " + id + "n'ete trouve dans la BDD", ErrorCodes.UTILISATEUR_NOT_FOUND
                )
        );
    }

    @Override
    public UtilisateurDto findByCodeUtilisateur(String codeUtilisateur) {

        if (!StringUtils.hasLength(codeUtilisateur)) {
            log.error("Utilisateur CODE is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByCode(codeUtilisateur);

        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec CODE = " + codeUtilisateur + " n' ete trouvé dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Utilisateur ID is null");
            return;
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'email = "+ email + "n'a été trouver dans la BDD", ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public UtilisateurDto cangerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if(utilisateurOptional.isEmpty()){
            log.warn("Aucun utilisateur n'a été trouver avec l'ID"+ dto.getId());
            throw  new EntityNotFoundException("Aucun utilisateur n'a été trouver avec l'ID"+ dto.getId(), ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(dto.getMotDePasse());

        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }
    private void validate(ChangerMotDePasseUtilisateurDto dto){
        if(dto == null){
            log.warn("impossible de modifier le mot de passe avec un objet null");
            throw new InvalidOperationException("Aucune information n'a été fourni pour pouvoir changer le mot de passz",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if(dto == null){
            log.warn("impossible de modifier le mot de passe avec un ID null");
            throw new InvalidOperationException("ID utilisateur null , impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("impossible de modifier le mot de passe avec un mot de passe vide");
            throw new InvalidOperationException("mot de passe utilisateur null , impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("impossible de modifier le mot de passe avec deux mot de passe different");
            throw new InvalidOperationException("mots de passe utilisateur non idententique , impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
