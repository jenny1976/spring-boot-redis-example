package com.upday.newsapi.controller;

import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.service.ArticleService;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * Handles Requests for the News-Article Service.
 *
 * @author jschulz
 */
@RestController
@RequestMapping(
        value = "/articles",
        produces = { "application/json" },
        consumes = { "application/json" }
)
public class ArticlesController {

    private static final Logger LOGGER = Logger.getLogger(ArticlesController.class);

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(final ArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * Create a new Article, and if needed also new Authors and Keywords from
     * the given {@link CreateArticle}.
     *
     * @param   newArticle  the input
     * @param   validationResult    result from bean-validation
     * @return  new created {@link Article} and HttpStatusCode
     */
    @RequestMapping( value = "/", method = PUT )
    public ResponseEntity<Article> createArticle(final @RequestBody @Valid CreateArticle newArticle,
            final BindingResult validationResult ) {
        if(validationResult.hasErrors()) {
            throw new IllegalArgumentException("There are invalid arguments in 'newArticle'.");
        }

        ResponseEntity<Article> response;
        try {
            final Article article = articleService.createArticle(newArticle);
            response = new ResponseEntity<>(article, HttpStatus.OK);
        } catch (DataIntegrityViolationException dive) {
            LOGGER.error("article couldn't be saved: " + dive);
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
    @RequestMapping( value = "/{articleId}", method = POST )
    public ResponseEntity<Article> updateArticle(final @PathVariable("articleId") String articleId,
            @RequestBody @Valid UpdateArticle updateArticle, final BindingResult validationResult) {
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
    @RequestMapping( value = "/{articleId}", method = DELETE )
    public void deleteArticle(final @PathVariable("articleId") String articleId) {

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
    @RequestMapping( value = "/{articleId}", method = GET )
    public ResponseEntity<Article> getArticle(final @PathVariable("articleId") Long articleId) {

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
     * @param   authorId    an author.id
     * @return  an {@link Article} List
     */
    @RequestMapping( value = "/author/{authorId}", method = GET )
    public @ResponseBody List<Article> getArticlesByAuthor(final @PathVariable("authorId") Long authorId) {

        return articleService.findByAuthorId(authorId);
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
    @RequestMapping( value = "/date/{from}/{to}", method = GET )
    public @ResponseBody List<Article> getArticlesByDateRange(
            final @PathVariable("from") @DateTimeFormat(iso=ISO.DATE) LocalDate fromDate,
            final @PathVariable("to") @DateTimeFormat(iso=ISO.DATE) LocalDate toDate) {

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
    @RequestMapping( value = "/search/{searchKeyword}", method = GET )
    public @ResponseBody List<Article> getArticlesByKeyword(final @PathVariable("searchKeyword") String searchKeyword) {

        return articleService.findByKeywordName(searchKeyword);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) throws Exception {
	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
