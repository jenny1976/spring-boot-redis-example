package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Keyword;
import com.upday.newsapi.model.UpdateArticle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author jschulz
 */
@Service
public class ArticleService {

    private static final Logger LOGGER = Logger.getLogger(ArticleService.class);

    private final RedisTemplate<String, String> redisTemplate;

//    private final RedisList<Article> articlesQueue;
//    private final RedisList<Author> authorsQueue;
//    private final RedisList<Keyword> keywordsQueue;


    @Autowired
    public ArticleService(RedisTemplate template) {
        this.redisTemplate = template;

//        articlesQueue = new DefaultRedisList<>("articles", redisTemplate);
//        authorsQueue = new DefaultRedisList<>("authors", redisTemplate);
//        keywordsQueue = new DefaultRedisList<>("keywords", redisTemplate);
    }


    public Article createArticle(final CreateArticle createArticle) {
        LOGGER.info("----------------- createArticle from: " + createArticle);

        final Article article = ModelConverter.convertToNewArticle(createArticle);
        if(null == article) {
            return null;
        }

        final String articleId = UUID.randomUUID().toString();

        saveArticle(article, articleId);
        saveAuthors(article.getAuthors(), articleId);
        saveKeywords(article.getKeywords(), articleId);

        article.setId(articleId);
        return article;
    }

    public Article updateArticle(final UpdateArticle input, final String articleId) {
        LOGGER.info("----------------- updateArticle from: " + input);
        final Article article = ModelConverter.convertToArticle(input, articleId);
        if(null == article) {
            return null;
        }

        saveArticle(article, articleId);
        saveAuthors(article.getAuthors(), articleId);
        saveKeywords(article.getKeywords(), articleId);

        return article;
    }

    public boolean deleteArticle(final String articleId) {
        LOGGER.info("----------------- delete article with id: " + articleId);
//        if(articleRepository.exists(articleId)) {
//            articleRepository.delete(articleId);
//        } else {
//            return false;
//        }
        return true;
    }

    public Article findOne(final Long articleId) {
        LOGGER.info("----------------- find article with id: " + articleId);
//        return articleRepository.findOne(articleId);
        return new Article();
    }

    public List<Article> findByAuthorId(final Long authorId) {
        LOGGER.info("----------------- find articles by authorId: " + authorId);

//        final Author author = authorRepository.findOne(authorId);
//        return author.getArticles();
        return null;

    }

    public List<Article> findByKeywordName(final String searchKeyword) {
        LOGGER.info("----------------- find articles by keyword: " + searchKeyword);
//        return articleRepository.findByKeywordsNameIgnoreCase(searchKeyword);
        return Collections.EMPTY_LIST;
    }

    public List<Article> findByDateRange(final LocalDate from, final LocalDate to) {
        LOGGER.info("----------------- findByDateRange: " + from +" - "+ to);
//        return articleRepository.findByPublishedOnBetween(from, to);
        return Collections.EMPTY_LIST;
    }



    private void saveKeywords(final List<Keyword> keywords, final String articleId) {

        redisTemplate.delete("article:"+articleId+":keyword:");

        if(!CollectionUtils.isEmpty(keywords)) {
            keywords.stream().map((keyword) -> {
                String id = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set("article:"+articleId+"keyword:"+id, keyword.getName());
                return keyword;
            }).forEach((keyword) -> {
//                this.keywordsQueue.addFirst(keyword);
            });
        }
    }

    private void saveAuthors(final List<Author> authors, final String articleId) {

        redisTemplate.delete("article:"+articleId+":author:");

        if(!CollectionUtils.isEmpty(authors)) {
            authors.stream().map((author) -> {
                String id = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set("article:"+articleId+":author:"+id+":firstname", author.getFirstname());
                redisTemplate.opsForValue().set("article:"+articleId+":author:"+id+":lastname", author.getLastname());
                return author;
            }).forEach((author) -> {
//                this.authorsQueue.add(author);
            });
        }
    }

    private void saveArticle(final Article article, final String articleId) {
        redisTemplate.opsForValue().set("article:"+articleId+":headline", article.getHeadline());
        redisTemplate.opsForValue().set("article:"+articleId+":subheadline", article.getTeaserText());
        redisTemplate.opsForValue().set("article:"+articleId+":maintext", article.getMainText());
        redisTemplate.opsForValue().set("article:"+articleId+":publishedon", article.getPublishedOn().toString());
    }

}
