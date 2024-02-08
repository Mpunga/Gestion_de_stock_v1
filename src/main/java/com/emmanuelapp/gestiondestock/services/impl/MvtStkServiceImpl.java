package com.emmanuelapp.gestiondestock.services.impl;

import com.emmanuelapp.gestiondestock.dto.MvtStkDto;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.TypeMvtStk;
import com.emmanuelapp.gestiondestock.repository.MvtStkRepository;
import com.emmanuelapp.gestiondestock.services.ArticleService;
import com.emmanuelapp.gestiondestock.services.MvtStkService;
import com.emmanuelapp.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService{

    private MvtStkRepository mvtStkRepository;
    private ArticleService articleService;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository mvtStkRepository, ArticleService articleService) {
        this.mvtStkRepository = mvtStkRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if(idArticle == null){
            log.warn("ID article is null");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mvtStkRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        return mvtStkRepository.findAllByArticleId(idArticle).stream()
                .map(MvtStkDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto mvtStkDto) {
     return entreePositive(mvtStkDto, TypeMvtStk.ENTREE);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto mvtStkDto) {
        return sortieNegative(mvtStkDto, TypeMvtStk.SORTIE);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto mvtStkDto) {
       return entreePositive(mvtStkDto, TypeMvtStk.CORRECTION_POS);
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto mvtStkDto) {
      return sortieNegative(mvtStkDto, TypeMvtStk.CORRECTION_NEG);
    }

    private MvtStkDto entreePositive(MvtStkDto mvtStkDto, TypeMvtStk typeMvtStk){
        List<String> errors = MvtStkValidator.validate(mvtStkDto);
        if(!errors.isEmpty()){
            log.error("Article is not valide {}", mvtStkDto);
            throw new InvalidEntityException("Le Mouvemenet du stock n'est pas valide",
                    ErrorCodes.MVT_STK_NOT_VALIDE);
        }
        mvtStkDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvtStkDto.getQuantite().doubleValue())
                )
        );
        mvtStkDto.setTypeMvtStk(typeMvtStk);
        return MvtStkDto.fromEntity(
                mvtStkRepository.save(MvtStkDto.toEntity(mvtStkDto))
        );
    }

    private MvtStkDto sortieNegative(MvtStkDto mvtStkDto, TypeMvtStk typeMvtStk){
        List<String> errors = MvtStkValidator.validate(mvtStkDto);
        if(!errors.isEmpty()){
            log.error("Article is not valide {}", mvtStkDto);
            throw new InvalidEntityException("Le Mouvemenet du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALIDE);
        }
        mvtStkDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvtStkDto.getQuantite().doubleValue()) * -1
                )
        );
        mvtStkDto.setTypeMvtStk(typeMvtStk);
        return MvtStkDto.fromEntity(
                mvtStkRepository.save(MvtStkDto.toEntity(mvtStkDto))
        );
    }
}
