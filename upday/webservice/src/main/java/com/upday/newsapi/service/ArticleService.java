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

    private final RedisTemplate redisTemplate;

    private final RedisList<Article> articles;
    private final RedisList<Author> authors;
    private final RedisList<Keyword> keywords;


    @Autowired
    public ArticleService(RedisTemplate redisTemplate1) {
        this.redisTemplate = redisTemplate1;

        articles = new DefaultRedisList<>("articles", redisTemplate);
        authors = new DefaultRedisList<>("authors", redisTemplate);
        keywords = new DefaultRedisList<>("keywords", redisTemplate);
    }


    public Article createArticle(final CreateArticle createArticle) {
        LOGGER.info("----------------- createArticle from: " + createArticle);

        final Article article = ModelConverter.convertToNewArticle(createArticle);

        final List<Author> detachedAuthors = article.getAuthors();
        article.setAuthors(new ArrayList<>());
        final List<Keyword> detachedKeywords = article.getKeywords();
        article.setKeywords(new ArrayList<>());

//        Article newArticle = articleRepository.save(article);

        // save keywords and authors if new and attach to new article.
//        saveAuthors(detachedAuthors, newArticle.getId());
//
//        saveKeywords(detachedKeywords, newArticle.getId());

        return null;
    }

    public Article updateArticle(final UpdateArticle input, final Long id) {
        LOGGER.info("----------------- updateArticle from: " + input);
        final Article toUpdate = null;//TODO

        if(null == toUpdate) {
            return null;
        }

        final List<Author> detachedAuthors = input.getAuthors();
        final List<Keyword> detachedKeywords = input.getKeywords();

        toUpdate.setAuthors(new ArrayList<>()); // reset attributes
        toUpdate.setKeywords(new ArrayList<>());
        toUpdate.setTeaserText(input.getTeaserText());
        toUpdate.setHeadline(input.getHeadline());
        toUpdate.setMainText(input.getMainText());

        // merge
//        articleRepository.saveAndFlush(toUpdate);

        // save keywords and authors.
        saveAuthors(detachedAuthors, toUpdate.getId());

        saveKeywords(detachedKeywords, toUpdate.getId());

        return null;
    }

    public boolean deleteArticle(Long articleId) {
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



    private void saveKeywords(final List<Keyword> keywords, final Long articleId) {

        if(!CollectionUtils.isEmpty(keywords)) {
            for (Keyword keyword : keywords) {
                String id = UUID.randomUUID().toString();

                if(redisTemplate.hasKey("keyword:"+id)) {

                }

                redisTemplate.opsForValue().set("keyword:"+id, keyword.getName() + ":article:" + articleId);
                this.keywords.addFirst(keyword);
            }
        }
    }

    private void saveAuthors(final List<Author> authors, final Long articleId) {

        if(!CollectionUtils.isEmpty(authors)) {
            authors.stream().map((author) -> {
                String id = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set("author:"+id, author + ":article:" + articleId);
                return author;
            }).forEach((author) -> {
                this.authors.add(author);
            });
        }
    }

}
