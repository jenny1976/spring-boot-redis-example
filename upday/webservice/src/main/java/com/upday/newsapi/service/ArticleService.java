package com.upday.newsapi.service;

import com.upday.newsapi.repository.ArticleRepository;
import com.upday.newsapi.repository.AuthorRepository;
import com.upday.newsapi.repository.KeywordRepository;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Service
public class ArticleService {

    private static final Logger LOGGER = Logger.getLogger(ArticleService.class);


    private final ArticleRepository articleRepository;

    private final AuthorRepository authorRepository;

    private final KeywordRepository keywordRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository,
            KeywordRepository keywordRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.keywordRepository = keywordRepository;
    }


    public Article createArticle(final Article article) {
        LOGGER.info("----------------- createArticle from: " + article);

        final List<Author> detachedAuthors = article.getAuthors();
        article.setAuthors(new ArrayList<>());
        final List<Keyword> detachedKeywords = article.getKeywords();
        article.setKeywords(new ArrayList<>());

        // first persist to get the PK
        Article newArticle = articleRepository.save(article);

        // save keywords and authors if new and attach to new article.
        saveAuthors(detachedAuthors, newArticle.getId());

        saveKeywords(detachedKeywords, newArticle.getId());

        return articleRepository.findOne(newArticle.getId());
    }

    public Article updateArticle(final Article input) {
        LOGGER.info("----------------- updateArticle from: " + input);
        Article toUpdate = articleRepository.findOne(input.getId());

        if(null == toUpdate) {
            return null;
        }

        final List<Author> detachedAuthors = input.getAuthors();
        final List<Keyword> detachedKeywords = input.getKeywords();

        toUpdate.setAuthors(new ArrayList<>()); // reset attributes
        toUpdate.setKeywords(new ArrayList<>());
        toUpdate.setDescription(input.getDescription());
        toUpdate.setHeadline(input.getHeadline());
        toUpdate.setMainText(input.getMainText());

        // merge
        articleRepository.saveAndFlush(toUpdate);

        // save keywords and authors.
        saveAuthors(detachedAuthors, toUpdate.getId());

        saveKeywords(detachedKeywords, toUpdate.getId());
        
        return articleRepository.getOne(toUpdate.getId());
    }

    public boolean deleteArticle(Long articleId) {
        LOGGER.info("----------------- delete article with id: " + articleId);
        if(articleRepository.exists(articleId)) {
            articleRepository.delete(articleId);
        } else {
            return false;
        }
        return true;
    }

    public Article findOne(final Long articleId) {
        LOGGER.info("----------------- find article with id: " + articleId);
        return articleRepository.findOne(articleId);
    }

    public List<Article> findByAuthorId(final Long authorId) {
        LOGGER.info("----------------- find articles by authorId: " + authorId);

        final Author author = authorRepository.findOne(authorId);
        return author.getArticles();

    }

    public List<Article> findByKeywordName(final String searchKeyword) {
        LOGGER.info("----------------- find articles by keyword: " + searchKeyword);
        return articleRepository.findByKeywordsNameIgnoreCase(searchKeyword);
    }

    public List<Article> findByDateRange(final LocalDate from, final LocalDate to) {
        LOGGER.info("----------------- findByDateRange: " + from +" - "+ to);
        return articleRepository.findByPublishedOnBetween(from, to);
    }



    @Transactional
    private void saveKeywords(final List<Keyword> detachedKeywords, final Long articleId) {

        if(!CollectionUtils.isEmpty(detachedKeywords)) {

            Article entity = articleRepository.getOne(articleId);
            for (Keyword keyword : detachedKeywords) {

                if(keyword.isNew()|| !authorRepository.exists(keyword.getId())) {
                    keyword = keywordRepository.saveAndFlush(keyword);
                }
                entity.addKeyword(keywordRepository.findOne(keyword.getId()));
            }
            articleRepository.saveAndFlush(entity);
        }
    }

    @Transactional
    private void saveAuthors(final List<Author> detachedAuthors, final Long articleId) {

        if(!CollectionUtils.isEmpty(detachedAuthors)) {

            Article entity = articleRepository.getOne(articleId);
            for (Author author : detachedAuthors) {

                if(author.isNew() || !authorRepository.exists(author.getId())) {
                    author = authorRepository.saveAndFlush(author);
                }
                entity.addAuthor(authorRepository.findOne(author.getId()));
            }
            articleRepository.saveAndFlush(entity);
        }
    }

}
