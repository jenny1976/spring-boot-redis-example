package com.upday.newsapi.controller;

import com.upday.newsapi.repository.ArticleRepository;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.model.RsArticle;
import com.upday.newsapi.service.ArticleService;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles Requests for the News-Article Service.
 *
 * @author jschulz
 */
@RestController
@RequestMapping(value = "/articles")
public class ServiceController {
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired 
    private ArticleService articleService;

    @RequestMapping(
            value = "/",
            method = RequestMethod.PUT
    )
    public @ResponseBody Long createArticle(@RequestBody RsArticle rsArticle) {

        Article article = articleService.createArticle(ModelConverter.convertToJpaArticle(rsArticle));
        
        return article.getId();
    }

    @RequestMapping(
            value = "/{articleId}",
            method = RequestMethod.POST
    )
    public @ResponseBody RsArticle updateArticle(@PathVariable("articleId") Long articleId, 
            @RequestBody RsArticle rsArticle) {

        
        return null;
    }

    @RequestMapping(
            value = "/{articleId}",
            method = RequestMethod.DELETE
    )
    public void deleteArticle(@PathVariable("articleId") Long articleId) {
        Assert.isNull(articleId);
        articleRepository.delete(articleId);
    }
    
    @RequestMapping(
            value = "/{articleId}",
            method = RequestMethod.GET
    )
    public RsArticle getArticle(@PathVariable("articleId") Long articleId) {
        Assert.notNull(articleId);
        
        final Article dbArticle = articleRepository.findOne(articleId);
        
        return ModelConverter.convert(dbArticle);
    }

    /**
     *
     * @param authorId
     * @return
     */
    @RequestMapping(
            value = "/author/{authorId}",
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> getArticleByAuthor(@PathVariable("authorId") Long authorId) {
        Assert.notNull(authorId);
        final List<Article> articles = articleRepository.findByAuthorsId(authorId);
        return ModelConverter.convertArticles(articles);
    }

    /**
     * the given Date-Strings must be in ISO-8601 format: yyyy-MM-ddTHH:mm:ss.SSSZ
     * e.g. '2011-06-23'
     * 
     * @param fromDate
     * @param toDate
     * @return 
     */
    @RequestMapping(
            value = "/date/{from}/{to}",
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> getArticleByDateRange(@PathVariable("from") String fromDate,
            @PathVariable("to") String toDate) {
        
        LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_DATE);
        LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ISO_DATE);
        
        if(from.isBefore(to)) {
            // find articles
        } else {
            //error
        }
        return null;
    }

    @RequestMapping(
            value = "/search/{searchKeyword}",
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> getArticleByKeyword(
            @PathVariable("searchKeyword") String searchKeyword) {
        Assert.notNull(searchKeyword);
        final List<Article> articles = articleRepository.findByKeywordsNameIgnoreCase(searchKeyword);
        return ModelConverter.convertArticles(articles);
    }

}
