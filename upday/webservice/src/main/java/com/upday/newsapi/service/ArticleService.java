package com.upday.newsapi.service;

import com.upday.newsapi.repository.ArticleRepository;
import com.upday.newsapi.repository.AuthorRepository;
import com.upday.newsapi.repository.KeywordRepository;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Service
public class ArticleService {
    
    private static final Logger LOGGER = Logger.getLogger(ArticleService.class);
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired 
    private AuthorRepository authorRepository;
    
    @Autowired
    private KeywordRepository keywordRepository;
    
    
    public Article createArticle(final Article article) {
        LOGGER.info("----------------- article to save: " + article);
        
        // save keywords and authors if new
        Set<Author> authors = article.getAuthors();
        article.setAuthors(new HashSet<>());
        if(!CollectionUtils.isEmpty(authors))
            for (Author author : authors) {
                if(author.isNew()) {
                    author = authorRepository.save(author);
                }
                article.addAuthor(author);
        }
        
        Set<Keyword> keywords = article.getKeywords();
        article.setKeywords(new HashSet<>());
        if(!CollectionUtils.isEmpty(keywords))
            for (Keyword keyword : keywords) {
                if(keyword.isNew()) {
                    keyword = keywordRepository.save(keyword);
                }
                article.addKeyword(keyword);
            }
        
        // save article
        article.setCreatedOn(LocalDateTime.now());
        article.setUpdatedOn(LocalDateTime.now());
        Article save = articleRepository.save(article);
        
        return save;
    }
    
    public Article updateArticle(Article article) {
        
        Article save = articleRepository.save(article);
        
        return save;
    }
    
    public boolean deleteArticle(Article article) {
        
        articleRepository.delete(article);
        
        return true;
    }
    
}
