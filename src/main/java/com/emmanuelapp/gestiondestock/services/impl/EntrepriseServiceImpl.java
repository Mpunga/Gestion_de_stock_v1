package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.EntrepriseDto;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.Entreprise;
import com.emmanuelapp.gestiondestock.repository.EntrepriseRepository;
import com.emmanuelapp.gestiondestock.services.EntrepriseService;
import com.emmanuelapp.gestiondestock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;


    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {

        List<String> errors = EntrepriseValidator.validate(entrepriseDto);
        if (!errors.isEmpty()) {
            log.error("Entreprise is not valid {}", entrepriseDto);
            throw new InvalidEntityException("l'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALIDE, errors);
        }
        return entrepriseDto.fromEntity(entrepriseRepository
                .save(entrepriseDto.toEntity(entrepriseDto)));
    }

    @Override
    public EntrepriseDto findById(Integer id) {


        if (id == null) {
            log.error("Entreprise ID is null");
            return null;
        }

        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);

        EntrepriseDto entrepriseDto = EntrepriseDto.fromEntity(entreprise.get());

        return Optional.of(entrepriseDto).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Entreprise avec l'ID = " + id + "n'ete trouve dans la BDD", ErrorCodes.ENTREPRISE_NOT_FOUND
                )
        );
    }

    @Override
    public EntrepriseDto findByCodeEntreprise(String codeEntreprise) {

        if (!StringUtils.hasLength(codeEntreprise)) {
            log.error("Client CODE is null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findEntrepriseByCode(codeEntreprise);

        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Entreprise avec CODE = " + codeEntreprise + " n' ete trouvé dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
        );

    }

    @Override
    public List<EntrepriseDto> findAll() {

        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Entreprise ID is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}
