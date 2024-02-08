package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.ArticleDto;
import com.emmanuelapp.gestiondestock.dto.LigneVenteDto;
import com.emmanuelapp.gestiondestock.dto.MvtStkDto;
import com.emmanuelapp.gestiondestock.dto.VentesDto;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.*;
import com.emmanuelapp.gestiondestock.repository.ArticleRepository;
import com.emmanuelapp.gestiondestock.repository.LigneVenteRepository;
import com.emmanuelapp.gestiondestock.repository.VentesRepository;
import com.emmanuelapp.gestiondestock.services.MvtStkService;
import com.emmanuelapp.gestiondestock.services.VentesService;
import com.emmanuelapp.gestiondestock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImp implements VentesService {

    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkService mvtStkService;

    public VentesServiceImp(ArticleRepository articleRepository,
                            VentesRepository ventesRepository,
                            LigneVenteRepository ligneVenteRepository,
                            MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public VentesDto save(VentesDto ventesDto) {
         List<String> errors = VentesValidator.validate(ventesDto);
         if(!errors.isEmpty()){
             log.error("Ventes n'est pas valide");
             throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALIDE, errors);
         }
         List<String> articleErrors = new ArrayList<>();

         ventesDto.getLigneVentes().forEach(ligneVenteDto -> {
             Optional<Article> article = articleRepository.findById(ligneVenteDto.getVentes().getId());
             if(article.isEmpty()){
                 articleErrors.add("Aucun article avec l'ID"+ ligneVenteDto.getVentes().getId()+
                                                                   "n'a ete trouver dans la BDD");
             }
         });

         if(!articleErrors.isEmpty()){
             log.error("One or more articles were not found in the DB, {]", errors);
             throw new InvalidEntityException("un ou plusieurs articles n'ont pas ete trouver dans la BDD", ErrorCodes.VENTE_NOT_VALIDE, errors);
         }

         Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(ventesDto));

         ventesDto.getLigneVentes().forEach(ligneVenteDto -> {
             LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
             ligneVente.setVentes(savedVentes);
             ligneVenteRepository.save(ligneVente);
         });

        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findById(Integer id) {
        if(id == null){
            log.error("Ventes ID s NULL");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente n'a ete trouver dans la BDD",
                        ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Vente Code is NUll");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente client n'a ete trouver avec le code "+ code, ErrorCodes.VENTE_NOT_VALIDE
                ));
    }

    @Override
    public List<VentesDto> findAll() {

        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Ventes ID is null");
            return ;
        }
        ventesRepository.deleteById(id);
    }
    private void updateMvtStk(LigneVente lig){
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .dateMvt(Instant.now())
                    .typeMvtStk(TypeMvtStk.SORTIE)
                    .sourceMvtStk(SourceMvtStk.VENTE)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.entreeStock(mvtStkDto);
        }
    }

