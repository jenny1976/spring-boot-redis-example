package com.upday.newsapi.controller;

import com.upday.newsapi.model.RsArticle;
import com.upday.newsapi.model.RsAuthor;
import com.upday.newsapi.model.RsKeyword;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
public final class ModelConverter {
    
    protected static RsArticle convert(final Article dbArticle) {
        final RsArticle article = new RsArticle();
        article.setId(dbArticle.getId());
        article.setHeadline(dbArticle.getHeadline());
        article.setTeaserText(dbArticle.getDescription());
        article.setMainText(dbArticle.getMainText());
        article.setAuthors(convertAuthors(dbArticle.getAuthors()));
        article.setKeywords(convertKeywords(dbArticle.getKeywords()));
        article.setPublishedOn(dbArticle.getPublishedOn());
        return article;
    }

    protected static List<RsAuthor> convertAuthors(final Set<Author> dbAuthors) {
        final List<RsAuthor> rsAuthors = new ArrayList<>();
        for (Author dbAuthor : dbAuthors) {
            rsAuthors.add(convert(dbAuthor));
        }
        return rsAuthors;
    }

    protected static RsAuthor convert(final Author dbAuthor) {
        final RsAuthor author = new RsAuthor(dbAuthor.getId(), dbAuthor.getFirstname(), dbAuthor.getLastname());
        return author;
    }

    protected static List<RsKeyword> convertKeywords(final Set<Keyword> dbKeywords) {
        final List<RsKeyword> rsKeywords = new ArrayList<>();
        for (Keyword dbKeyword : dbKeywords) {
            rsKeywords.add(convert(dbKeyword));
        }
        return rsKeywords;
    }

    protected static RsKeyword convert(final Keyword dbKeyword) {
        final RsKeyword keyword = new RsKeyword(dbKeyword.getId(), dbKeyword.getName());
        return keyword;
    }

    protected static List<RsArticle> convertArticles(final List<Article> dbArticles) {
        final List<RsArticle> rsArticles = new ArrayList<>();
        for (Article article : dbArticles) {
            rsArticles.add(convert(article));
        }
        return rsArticles;
    }
    
    protected static Article convertToJpaArticle(final RsArticle article) {
        final Article dbArticle = new Article();
        
        dbArticle.setDescription(article.getTeaserText());
        dbArticle.setHeadline(article.getHeadline());
        dbArticle.setId(article.getId());
        dbArticle.setMainText(article.getMainText());
        dbArticle.setPublishedOn(article.getPublishedOn());
        
        if(!CollectionUtils.isEmpty(article.getAuthors())) {
            for(RsAuthor author : article.getAuthors()) {
                dbArticle.addAuthor(convertToJpaAuthor(author));
            }
        }
        
        if(!CollectionUtils.isEmpty(article.getKeywords())) {
            for(RsKeyword keyword : article.getKeywords()) {
                dbArticle.addKeyword(convertToJpaKeyword(keyword));
            }
        }
        return dbArticle;
    }
    
    protected static Author convertToJpaAuthor(final RsAuthor author) {
        final Author dbAuthor = new Author(author.getFirstname(), author.getLastname());
        dbAuthor.setId(author.getId());
        return dbAuthor;
    }
    
    protected static Keyword convertToJpaKeyword(final RsKeyword keyword) {
        final Keyword dbKeyword = new Keyword(keyword.getName());
        dbKeyword.setId(keyword.getId());
        return dbKeyword;
    }
}
