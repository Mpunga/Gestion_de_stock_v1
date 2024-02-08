package com.emmanuelapp.gestiondestock.services.impl;


import com.emmanuelapp.gestiondestock.dto.CategoryDto;
import com.emmanuelapp.gestiondestock.exception.EntityNotFoundException;
import com.emmanuelapp.gestiondestock.exception.ErrorCodes;
import com.emmanuelapp.gestiondestock.exception.InvalidEntityException;
import com.emmanuelapp.gestiondestock.model.Article;
import com.emmanuelapp.gestiondestock.model.Category;
import com.emmanuelapp.gestiondestock.repository.ArticleRepository;
import com.emmanuelapp.gestiondestock.repository.CategoryRepository;
import com.emmanuelapp.gestiondestock.services.CategoryService;
import com.emmanuelapp.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository) {

        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if(!errors.isEmpty()){
            log.error("Category is not valid {}", categoryDto);
            throw new InvalidEntityException("la Category n'est pas valide",
                    ErrorCodes.CATEGORY_NOT_VALIDE, errors);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(CategoryDto.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return null;
        }

        Optional<Category> category = categoryRepository.findById(id);

        CategoryDto categoryDto = CategoryDto.fromEntity(category.get());

        return Optional.of(categoryDto).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune category avec ID = " + id + "n'ete trouve dans le BDD", ErrorCodes.CATEGORY_NOT_FOUND
                )
        );
    }

    @Override
    public CategoryDto findByCodeCategory(String codeCategory) {
        if(!StringUtils.hasLength(codeCategory)){
            log.error("Category CODE is null");
            return null;
        }

        Optional<Category> category = categoryRepository.findCategoryByCode(codeCategory);
        return Optional.of(CategoryDto.fromEntity(category.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune category avec CODE = " + codeCategory + "n'ete trouvé dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
        );
    }

    @Override
    public List<CategoryDto> findAll() {

        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return ;
        }
        List< Article > articles = articleRepository.findAllByCategoryId(id);
        if(!articles.isEmpty()){
            throw new InvalidEntityException("Impossible de supprimer cette categorie qui est deja utilisé", ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepository.deleteById(id);
    }
}
