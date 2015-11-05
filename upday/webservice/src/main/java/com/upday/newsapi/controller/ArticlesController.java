package com.upday.newsapi.controller;

import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.model.RsArticle;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.repository.domain.Keyword;
import com.upday.newsapi.service.ArticleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        produces = { "application/json" }
)
public class ArticlesController {
    
    @Autowired 
    private ArticleService articleService;

    /**
     * Create a new Article, and if needed also new Authors and Keywords from 
     * the given {@link CreateArticle}.
     * 
     * @param   newArticle  the input
     * @return  new created {@link RsArticle} and HttpStatusCode
     */
    @RequestMapping( value = "/", method = PUT )
    public ResponseEntity<RsArticle> createArticle(final @RequestBody @Valid CreateArticle newArticle) {
        
        final Article article = articleService.createArticle(ModelConverter.convertToJpaArticle(newArticle));
        
        ResponseEntity<RsArticle> response;
        if( article == null ) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(article), HttpStatus.OK);
        }
        
        return response;
    }

    /**
     * Updates an Article by given article.id from the data {@link UpdateArticle}.
     * 
     * @param   articleId       id of the article to change.
     * @param   updateArticle   the input
     * @return  the updated {@link RsArticle}
     */
    @RequestMapping( value = "/{articleId}", method = POST )
    public ResponseEntity<RsArticle> updateArticle(final @PathVariable("articleId") Long articleId, 
            @RequestBody @Valid UpdateArticle updateArticle) {
        
        final Article article = articleService.updateArticle(ModelConverter.convertToJpaArticle(updateArticle, articleId));
        
        ResponseEntity<RsArticle> response;
        if( article == null ) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(article), HttpStatus.OK);
        }
        
        return response;
    }

    /**
     * Delete an article by id-parameter.
     * 
     * @param articleId     article.id
     */
    @RequestMapping( value = "/{articleId}", method = DELETE )
    public void deleteArticle(final @PathVariable("articleId") Long articleId) {
        
        boolean success = articleService.deleteArticle(articleId);
        if(!success) {
            throw new EntityNotFoundException("Entity not found.");
        }
    }
    
    /**
     * Get an {@link RsArticle} by a given id.
     * 
     * @param   articleId     article.id
     * @return  the {@link RsArticle}
     */
    @RequestMapping( value = "/{articleId}", method = GET )
    public ResponseEntity<RsArticle> getArticle(final @PathVariable("articleId") Long articleId) {
        
        final Article dbArticle = articleService.findOne(articleId);

        ResponseEntity<RsArticle> response;
        if(dbArticle == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(dbArticle), HttpStatus.OK);
        }
        return response;
    }

    /**
     * Get a List of {@link RsArticle}s by a given authorId.
     * 
     * @param   authorId    an author.id
     * @return  an {@link RsArticle} List
     */
    @RequestMapping( value = "/author/{authorId}", method = GET )
    public @ResponseBody List<RsArticle> getArticleByAuthor(final @PathVariable("authorId") Long authorId) {
        
        final List<Article> articles = articleService.findByAuthorId(authorId);
        return ModelConverter.convertArticles(articles);
    }

    /**
     * the given Date-Strings must be in ISO-8601 format: yyyy-MM-dd
     * e.g. '2011-06-23'
     * 
     * @param fromDate  start-Date
     * @param toDate    end-Date
     * @return  an {@link RsArticle} List
     */
    @RequestMapping( value = "/date/{from}/{to}", method = GET )
    public @ResponseBody List<RsArticle> getArticleByDateRange(final @PathVariable("from") String fromDate,
            final @PathVariable("to") String toDate) {
        
        final LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_DATE);
        final LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ISO_DATE);
        List<RsArticle> result;
        
        if(from.isBefore(to)) {
            // find articles
            final List<Article> findByDateRange = articleService.findByDateRange(from, to);
            result = ModelConverter.convertArticles(findByDateRange);
        } else {
            throw new IllegalArgumentException("First Date has to be before the second one!");
        }
        return result;
    }

    /**
     * Search the {@link Keyword}.name attribute, return the suitable Articles.
     * 
     * @param searchKeyword     the keyword.name
     * @return  an {@link RsArticle} List
     */
    @RequestMapping( value = "/search/{searchKeyword}", method = GET )
    public @ResponseBody List<RsArticle> getArticleByKeyword(final @PathVariable("searchKeyword") String searchKeyword) {
        
        final List<Article> articles = articleService.findByKeywordName(searchKeyword);
        return ModelConverter.convertArticles(articles);
    }

}
