package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.ArticleDto;
import com.emmanuelapp.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/categories")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "category/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une categorie", notes = "Cette methode permet d'enregistrer ou modifier une categorie", response = CategoryDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "L'objet categorie créé / modifier"),
                    @ApiResponse(code = 400, message = "L'objet categorie n'est pas valide"),
            })
    CategoryDto save(@RequestBody CategoryDto categoryDto);

    @GetMapping(value = APP_ROOT + "/ }", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par ID", notes = "Cette methode permet chercher une categorie par son ID", response = CategoryDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "La categire a été trouvé dans la BDD"),
                    @ApiResponse(code = 404, message = "Aucun categorie existe dans la BDD avec l'ID fourni")
            })
    CategoryDto findById(@PathVariable("idCategory") Integer id);

    @GetMapping(value = APP_ROOT + "/categorys/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par CODE", notes = "Cette methode permet chercher une categorie par son CODE", response = CategoryDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "La categorie a été trouvé dans la BDD"),
                    @ApiResponse(code = 404, message = "Aucune categorie existe dans la BDD avec le CODE fourni")
            })
    CategoryDto findByCodeCategory(@PathVariable("codeCategory") String codeCategory);

    @GetMapping(value = APP_ROOT + "/categorys/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des categories", notes = "Cette methode permet chercher et renvoyer la liste des categories qui existent" + "dans la BDD", responseContainer = "List<CategoryDto>")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "La liste des categories / une liste vide")

            })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "categorys/delete/{idCategory}")
    @ApiOperation(value = "Supprimer une categorie", notes = "Cette methode permet de supprimer une categorie par ID", response = CategoryDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "La categorie a été supprimer")
            })
    void delete(@PathVariable("idCategory") Integer id);
}
