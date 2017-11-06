package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Keyword;
import com.upday.newsapi.model.UpdateArticle;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.stereotype.Service;

/**
 * @author jschulz
 */
@Service
public class ArticleService {

    private static final Logger LOGGER = Logger.getLogger(ArticleService.class);

    private final StringRedisTemplate redisTemplate;

    private final ValueOperations<String, String> valueOps;

    private final RedisAtomicLong articleIdCounter;
    private final RedisAtomicLong authorIdCounter;
    private final RedisAtomicLong keywordIdCounter;

    private final HashMapper<Article, String, String> articleMapper = new DecoratingStringHashMapper<Article>(
			new JacksonHashMapper<Article>(Article.class));

//    private final RedisList<Article> articlesQueue;
//    private final RedisList<Author> authorsQueue;
//    private final RedisList<Keyword> keywordsQueue;

    public ArticleService(StringRedisTemplate template) {
        this.redisTemplate = template;
        this.valueOps = template.opsForValue();
        this.articleIdCounter = new RedisAtomicLong(KeyUtils.globalArticleId(), template.getConnectionFactory());
        this.authorIdCounter = new RedisAtomicLong(KeyUtils.globalAuthorId(), template.getConnectionFactory());
        this.keywordIdCounter = new RedisAtomicLong(KeyUtils.globalKeywordId(), template.getConnectionFactory());

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

        final String articleId = String.valueOf(articleIdCounter.incrementAndGet());

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
        deleteKeywords(articleId);
        deleteAuthors(articleId);
        deleteArticleById(articleId);
        return true;
    }

    public Article findOne(final String articleId) {
        LOGGER.info("----------------- find article with id: " + articleId);
        BoundHashOperations<String, String, String> articleOps = redisTemplate.boundHashOps(KeyUtils.articleId(articleId));
        if (articleOps.size() < 1) {
            return null;
        }
        Article result = new Article(articleId, articleOps.get("headline"), articleOps.get("subheadline"),
                articleOps.get("content"), articleOps.get("publishedOn"));

        RedisList<String> keywordIds = keywords(articleId);
        for (String kId : keywordIds) {
            result.getKeywords().add(new Keyword(valueOps.get(KeyUtils.keywordId(kId))));
        }

        RedisList<String> authorIds = authors(articleId);
        for (String authorId : authorIds) {
            BoundHashOperations<String, String, String> authOps =redisTemplate.boundHashOps(KeyUtils.authorId(authorId));
            result.getAuthors().add(new Author(authOps.get("firstname"), authOps.get("lastname")));
        }

        return result;
    }

    public List<Article> findByAuthorName(final String name) {
        LOGGER.info("----------------- find articles by author: " + name);

//        final Author author = authorRepository.findOne(authorId);
//        return author.getArticles();
        return Collections.EMPTY_LIST;

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

        deleteKeywords(articleId);

        keywords.stream().map((keyword) -> {
            String keywordId = String.valueOf(keywordIdCounter.incrementAndGet());
            redisTemplate.opsForValue().set(KeyUtils.keywordId(keywordId), keyword.getName());
            return keywordId;
        }).forEach((keywordId) -> {
            keywords(articleId).add(keywordId);
        });

    }

    private void saveAuthors(final List<Author> authors, final String articleId) {

        deleteAuthors(articleId);

        authors.stream().map((author) -> {
            String authorId = String.valueOf(authorIdCounter.incrementAndGet());
            BoundHashOperations<String, String, String> authorOps = redisTemplate.boundHashOps(KeyUtils.authorId(authorId));
            authorOps.put("firstname", author.getFirstname());
            authorOps.put("lastname", author.getLastname());
            return authorId;
        }).forEach((authorId) -> {
            authors(articleId).add(authorId);
        });

    }

    private void saveArticle(final Article article, final String articleId) {

        BoundHashOperations<String, String, String> articleOps = redisTemplate.boundHashOps(KeyUtils.articleId(articleId));
        articleOps.put("headline", article.getHeadline());
        articleOps.put("subheadline", article.getTeaserText());
        articleOps.put("content", article.getMainText());
        articleOps.put("publishedOn", article.getPublishedOn());

    }

    public boolean isArticleValid(String id) {
        return redisTemplate.hasKey(KeyUtils.articleId(id));
    }

    private RedisList<String> keywords(String articleId) {
        return new DefaultRedisList<String>(KeyUtils.keywords(articleId), redisTemplate);
    }

    private RedisList<String> authors(String articleId) {
        return new DefaultRedisList<String>(KeyUtils.authors(articleId), redisTemplate);
    }

    private void deleteKeywords(String articleId) {
        for (String kId : keywords(articleId)) {
            valueOps.getOperations().delete(KeyUtils.keywordId(kId));
        }
        keywords(articleId).clear();
    }

    private void deleteAuthors(String articleId) {
        for (String authorId : authors(articleId)) {
            valueOps.getOperations().delete(KeyUtils.authorId(authorId));
        }
        authors(articleId).clear();
    }

    private void deleteArticleById(String articleId) {
        valueOps.getOperations().delete(KeyUtils.articleId(articleId));
    }
}
