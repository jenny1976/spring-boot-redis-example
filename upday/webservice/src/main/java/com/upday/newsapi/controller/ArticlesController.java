package com.upday.newsapi.controller;

import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.model.RsArticle;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.service.ArticleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
@RequestMapping(value = "/articles")
public class ArticlesController {
    
    private static final Logger LOGGER = Logger.getLogger(ArticlesController.class);
    
    @Autowired 
    private ArticleService articleService;

    
    @RequestMapping( value = "/", method = PUT )
    public ResponseEntity<RsArticle> createArticle(@RequestBody @Valid CreateArticle newArticle) {
        
        final Article article = articleService.createArticle(ModelConverter.convertToJpaArticle(newArticle));
        
        ResponseEntity<RsArticle> response;
        if(article == null) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(article), HttpStatus.OK);
        }
        return response;
    }

    
    @RequestMapping( value = "/{articleId}", method = POST )
    public ResponseEntity<RsArticle> updateArticle(@PathVariable("articleId") Long articleId, 
            @RequestBody @Valid UpdateArticle updateArticle) {
        
        final Article article = articleService.updateArticle(ModelConverter.convertToJpaArticle(updateArticle, articleId));
        
        ResponseEntity<RsArticle> response;
        if( article==null ) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(article), HttpStatus.OK);
        }
        
        return response;
    }

    
    @RequestMapping( value = "/{articleId}", method = DELETE )
    public void deleteArticle(@PathVariable("articleId") Long articleId) {
        
        boolean success = articleService.deleteArticle(articleId);
        if(!success) {
            throw new EntityNotFoundException("Entity not found.");
        }
    }
    
    
    @RequestMapping( value = "/{articleId}", method = GET )
    public ResponseEntity<RsArticle> getArticle(@PathVariable("articleId") Long articleId) {
        
        final Article dbArticle = articleService.findOne(articleId);

        ResponseEntity<RsArticle> response;
        if(dbArticle == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(dbArticle), HttpStatus.OK);
        }
        return response;
    }

    
    @RequestMapping( value = "/author/{authorId}", method = GET )
    public @ResponseBody List<RsArticle> getArticleByAuthor(@PathVariable("authorId") Long authorId) {
        
        final List<Article> articles = articleService.findByAuthorId(authorId);
        return ModelConverter.convertArticles(articles);
    }

    /**
     * the given Date-Strings must be in ISO-8601 format: yyyy-MM-dd
     * e.g. '2011-06-23'
     * 
     * @param fromDate
     * @param toDate
     * @return 
     */
    @RequestMapping( value = "/date/{from}/{to}", method = GET )
    public @ResponseBody List<RsArticle> getArticleByDateRange(@PathVariable("from") String fromDate,
            @PathVariable("to") String toDate) {
        
        LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_DATE);
        LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ISO_DATE);
        List<RsArticle> result;
        
        if(from.isBefore(to)) {
            // find articles
            List<Article> findByDateRange = articleService.findByDateRange(from, to);
            result = ModelConverter.convertArticles(findByDateRange);
        } else {
            //error
            throw new IllegalArgumentException("First Date has to be before the second one!");
        }
        return result;
    }

    
    @RequestMapping( value = "/search/{searchKeyword}", method = GET )
    public @ResponseBody List<RsArticle> getArticleByKeyword(
            @PathVariable("searchKeyword") String searchKeyword) {
        Assert.notNull(searchKeyword);
        final List<Article> articles = articleService.findByKeywordName(searchKeyword);
        return ModelConverter.convertArticles(articles);
    }

    
//    @ExceptionHandler(IllegalArgumentException.class)    
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) throws Exception {	
	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
