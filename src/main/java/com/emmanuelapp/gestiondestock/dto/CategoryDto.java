package com.emmanuelapp.gestiondestock.dto;

import com.emmanuelapp.gestiondestock.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryDto {

    private Integer id;

    private String code;

    private String designation;

    private Integer idEntreprise;

    @JsonIgnore
    private List<ArticleDto> articles;


    public static CategoryDto fromEntity(Category category){
        if(category == null){
            return null;
            // TODO Throw an exception
        }
        // Category -> CategoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .articles(category.getArticles() != null ?
                        category.getArticles().stream()
                                .map(ArticleDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto){
        if(categoryDto == null){
            return null;
            // TODO throw an exception
        }
        Category category  = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());

        return category;
    }
}
