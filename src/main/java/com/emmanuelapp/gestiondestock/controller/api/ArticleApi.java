package com.emmanuelapp.gestiondestock.controller.api;

import com.emmanuelapp.gestiondestock.dto.ArticleDto;
import com.emmanuelapp.gestiondestock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/articles")
public interface ArticleApi {

    @PostMapping(value = APP_ROOT + "article/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un article", notes = "Cette methode permet d'enregistrer ou modifier un article", response = ArticleDto.class)
     @ApiResponses(value =
             {
                     @ApiResponse(code = 200, message = "L'objet article créé / modifier"),
                     @ApiResponse(code = 400, message = "L'objet article n'est pas valide"),
             })
    ArticleDto save(@RequestBody ArticleDto articleDto);

    @GetMapping(value = APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet chercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
                    @ApiResponse(code = 404, message = "Aucun article existe dans la BDD avec l'ID fourni")
            })
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value = APP_ROOT + "/articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet chercher un article par son CODE", response = ArticleDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
                    @ApiResponse(code = 404, message = "Aucun article existe dans la BDD avec le CODE fourni")
            })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);


    @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet chercher et renvoyer la liste des articles qui existent" + "dans la BDD", responseContainer = "List<ArticleDto>")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "La liste des articles / une liste vide")

            })
    List<ArticleDto> findAll();

    @GetMapping(value = APP_ROOT + "/articles/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
      List< LigneVenteDto > findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);



    @GetMapping(value = APP_ROOT + "/articles/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idCategory);

    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID", response = ArticleDto.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "L'article a été supprimer")
            })
    void delete(@PathVariable("idArticle") Integer id);
}
