package com.upday.newsapi.controller;

import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Keyword;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.service.ArticleService;

import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handles Requests for the News-Article Service.
 *
 * @author jschulz
 */
@RestController
@RequestMapping(
        path = "/articles",
        produces = { "application/json" },
        consumes = { "application/json" }
)
@Slf4j
public class ArticlesController {

    @Inject
    private ArticleService articleService;

    /**
     * Create a new Article, and if needed also new Authors and Keywords from
     * the given {@link CreateArticle}.
     *
     * @param   newArticle  the input
     * @param   validationResult    result from bean-validation
     * @return  new created {@link Article} and HttpStatusCode
     */
    @PutMapping( path = "/" )
    public ResponseEntity<Article> createArticle(
            @RequestBody @Valid CreateArticle newArticle,
            BindingResult validationResult ) {
        if(validationResult.hasErrors()) {
            throw new IllegalArgumentException("There are invalid arguments in 'newArticle'.");
        }

        ResponseEntity<Article> response;

        final Article article = articleService.createArticle(newArticle);
        if( null!=article ) {
            response = new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return response;
    }

    /**
     * Updates an Article by given article.id from the data
     * {@link UpdateArticle}.
     *
     * @param articleId         id of the article to change.
     * @param updateArticle     the input
     * @param validationResult  result from bean-validation
     * @return the updated {@link Article}
     */
    @PostMapping( path = "/{articleId}" )
    public ResponseEntity<Article> updateArticle(
            @PathVariable(name = "articleId") String articleId,
            @RequestBody @Valid UpdateArticle updateArticle,
            BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            throw new IllegalArgumentException("There are invalid arguments in 'updateArticle'.");
        }

        final Article article = articleService.updateArticle(updateArticle, articleId);

        ResponseEntity<Article> response;
        if( article == null ) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(article, HttpStatus.OK);
        }

        return response;
    }

    /**
     * Delete an article by id-parameter.
     *
     * @param articleId     article.id
     */
    @DeleteMapping( path = "/{articleId}" )
    public void deleteArticle( @PathVariable(name = "articleId") String articleId ) {

        boolean success = articleService.deleteArticle(articleId);
        if(!success) {
            throw new IllegalArgumentException("Invalid articleId!");
        }
    }

    /**
     * Get an {@link Article} by a given id.
     *
     * @param   articleId     article.id
     * @return  the {@link Article}
     */
    @GetMapping( path = "/{articleId}" )
    public ResponseEntity<Article> getArticle( @PathVariable(name = "articleId") String articleId ) {

        final Article dbArticle = articleService.findOne(articleId);

        ResponseEntity<Article> response;
        if(dbArticle == null) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(dbArticle, HttpStatus.OK);
        }
        return response;
    }

    /**
     * Get a List of {@link Article}s by a given authorId.
     *
     * @param   author     an author.name, can be first- or lastname
     * @return  an {@link Article} List
     */
    @GetMapping( path = "/author/{author}" )
    public @ResponseBody List<Article> getArticlesByAuthor( @PathVariable(name = "author") String author ) {

        return articleService.findByAuthorName(author);
    }

    /**
     * the given Date Objects must be in ISO-8601 format: yyyy-MM-dd
     * e.g. '2011-06-23'
     *
     * @param fromDate  start-Date, mandatory parameter
     * @param toDate    end-Date, mandatory parameter
     *
     * @return  an {@link Article} List
     */
    @GetMapping( path = "/date/{from}/{to}" )
    public @ResponseBody List<Article> getArticlesByDateRange(
            @PathVariable(name = "from") @DateTimeFormat(iso=ISO.DATE) LocalDate fromDate,
            @PathVariable(name = "to") @DateTimeFormat(iso=ISO.DATE) LocalDate toDate) {

        List<Article> result;

        if(fromDate.isBefore(toDate)) {

            result = articleService.findByDateRange(fromDate, toDate);
        } else {
            throw new IllegalArgumentException("First Date has to be before the second one!");
        }
        return result;
    }

    /**
     * Search the {@link Keyword}.name attribute, return the suitable Articles.
     *
     * @param searchKeyword     the keyword.name
     * @return  an {@link Article} List
     */
    @GetMapping( path = "/search/{searchKeyword}" )
    public @ResponseBody List<Article> getArticlesByKeyword( @PathVariable(name = "searchKeyword") String searchKeyword ) {

        return articleService.findByKeywordName(searchKeyword);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) throws Exception {
	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
