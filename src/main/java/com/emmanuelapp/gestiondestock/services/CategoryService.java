package com.emmanuelapp.gestiondestock.services;

import com.emmanuelapp.gestiondestock.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto findById(Integer id);

    CategoryDto findByCodeCategory(String codeCategory);

    List<CategoryDto> findAll();

    void delete(Integer id);
}
