package com.emmanuelapp.gestiondestock.controller;

import com.emmanuelapp.gestiondestock.controller.api.MvtStkApi;
import com.emmanuelapp.gestiondestock.dto.MvtStkDto;
import com.emmanuelapp.gestiondestock.services.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MvtStkController implements MvtStkApi {

    private MvtStkService service;

    @Autowired
    public MvtStkController(final MvtStkService service) {
        this.service = service;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return service.stockReelArticle(idArticle);
    }

    @Override
    public List< MvtStkDto > mvtstkArticle(Integer idArticle) {
        return service.mvtStkArticle(idArticle);
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto mvtStkDto) {
        return service.entreeStock(mvtStkDto);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto mvtStkDto) {
        return service.sortieStock(mvtStkDto);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto mvtStkDto) {
        return service.correctionStockPos(mvtStkDto);
    }

    @Override
    public MvtStkDto correctionNeg(MvtStkDto mvtStkDto) {
        return service.correctionStockNeg(mvtStkDto);
    }
}
