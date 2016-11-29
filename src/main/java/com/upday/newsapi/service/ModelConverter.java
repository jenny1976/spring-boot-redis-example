package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.UpdateArticle;


import org.springframework.util.CollectionUtils;

/**
 * Converts the view-model into dbDomain and viceversa.
 *
 * @author jschulz
 */
public final class ModelConverter {

    protected static Article convertToNewArticle(final CreateArticle article) {
        final Article newArticle = new Article( null, article.getHeadline(),
                                            article.getTeaserText(), article.getMainText());

        if(!CollectionUtils.isEmpty(article.getAuthors())) {
            newArticle.setAuthors(article.getAuthors());
        }

        if(!CollectionUtils.isEmpty(article.getKeywords())) {
            newArticle.setKeywords(article.getKeywords());
        }
        return newArticle;
    }

    protected static Article convertToArticle(final UpdateArticle article, final String id) {
        final Article toUpdate = new Article(id, article.getHeadline(), article.getTeaserText(),
                article.getMainText(), article.getPublishedOn());

        if(!CollectionUtils.isEmpty(article.getAuthors())) {
            toUpdate.setAuthors(article.getAuthors());
        }

        if(!CollectionUtils.isEmpty(article.getKeywords())) {
            toUpdate.setKeywords(article.getKeywords());
        }
        return toUpdate;
    }
}
