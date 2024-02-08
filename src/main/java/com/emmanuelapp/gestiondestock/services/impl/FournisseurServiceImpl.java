package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.FournisseurDto;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.CommandeClient;
import com.emmanuelapp.gestiondestock.model.CommandeFournisseur;
import com.emmanuelapp.gestiondestock.model.Fournisseur;
import com.emmanuelapp.gestiondestock.model.LigneCommandeFournisseur;
import com.emmanuelapp.gestiondestock.repository.CommandeFournisseurRepository;
import com.emmanuelapp.gestiondestock.repository.FournisseurRepository;
import com.emmanuelapp.gestiondestock.services.FournisseurService;
import com.emmanuelapp.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, CommandeFournisseurRepository commandeFournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }


    @Override
    public FournisseurDto save(FournisseurDto fournisseurDto) {

        List<String> errors = FournisseurValidator.validate(fournisseurDto);
        if (!errors.isEmpty()) {
            log.error("Fournisseur is not valid {}", fournisseurDto);
            throw new InvalidEntityException("le Fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALIDE, errors);
        }
        return FournisseurDto.fromEntity(fournisseurRepository
                .save(FournisseurDto.toEntity(fournisseurDto)));
    }

    @Override
    public FournisseurDto findById(Integer id) {

        if (id == null) {
            log.error("Fournisseur ID is null");
            return null;
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);

        FournisseurDto clientDto = FournisseurDto.fromEntity(fournisseur.get());

        return Optional.of(clientDto).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Fournisseur avec l'ID = " + id + "n'ete trouve dans la BDD", ErrorCodes.FOURNISSEUR_NOT_FOUND
                )
        );
    }

    @Override
    public FournisseurDto findByCodeFournisseur(String codeFournisseur) {

        if (!StringUtils.hasLength(codeFournisseur)) {
            log.error("Fournisseur CODE is null");
            return null;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findFournisseurByCode(codeFournisseur);

        return Optional.of(FournisseurDto.fromEntity(fournisseur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun fournisseur avec CODE = " + codeFournisseur + " n' ete trouvé dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND)
        );
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Fournisseur ID is null");
            return;
        }

        fournisseurRepository.deleteById(id);
    }
}
