package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Keyword;
import com.upday.newsapi.model.UpdateArticle;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    private final HashMapper<Article, String, String> postMapper = new DecoratingStringHashMapper<Article>(
			new JacksonHashMapper<Article>(Article.class));

//    private final RedisList<Article> articlesQueue;
//    private final RedisList<Author> authorsQueue;
//    private final RedisList<Keyword> keywordsQueue;


    @Autowired
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
//        if(articleRepository.exists(articleId)) {
//            articleRepository.delete(articleId);
//        } else {
//            return false;
//        }
        return true;
    }

    public Article findOne(final String articleId) {
        LOGGER.info("----------------- find article with id: " + articleId);
        BoundHashOperations<String, String, String> articleOps = redisTemplate.boundHashOps(KeyUtils.articleId(articleId));
        Article result = new Article(articleId, articleOps.get("headline"), articleOps.get("subheadline"),
                articleOps.get("content"), articleOps.get("publishedOn"));
        
        return result;
    }

    public List<Article> findByAuthorId(final String authorId) {
        LOGGER.info("----------------- find articles by authorId: " + authorId);

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

        //TODO
//        redisTemplate.delete("article:"+articleId+":keyword:");

        if(!CollectionUtils.isEmpty(keywords)) {
            keywords.stream().map((keyword) -> {
                String kwId = String.valueOf(keywordIdCounter.incrementAndGet());
                redisTemplate.opsForValue().set(KeyUtils.keyword(articleId, kwId), keyword.getName());
                return keyword;
            }).forEach((keyword) -> {
//                this.keywordsQueue.addFirst(keyword);
            });
        }
    }

    private void saveAuthors(final List<Author> authors, final String articleId) {

        if(isArticleValid(articleId)) {
//            redisTemplate.delete("article:"+articleId+":author:");
        }

        if(!CollectionUtils.isEmpty(authors)) {
            authors.stream().map((author) -> {
                String authorId = String.valueOf(authorIdCounter.incrementAndGet());
                redisTemplate.opsForValue().set(KeyUtils.firstname(articleId, authorId), author.getFirstname());
                redisTemplate.opsForValue().set(KeyUtils.lastname(articleId, authorId), author.getLastname());
                return author;
            }).forEach((author) -> {
//                this.authorsQueue.add(author);
            });
        }
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
}
