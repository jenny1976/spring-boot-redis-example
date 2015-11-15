package com.upday.newsapi.controller;

import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.RsArticle;
import com.upday.newsapi.model.RsAuthor;
import com.upday.newsapi.model.RsKeyword;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * Converts the view-model into dbDomain and viceversa.
 *
 * @author jschulz
 */
public final class ModelConverter {

    protected static RsArticle convert(final Article dbArticle) {
        if(dbArticle != null) {
            final RsArticle article = new RsArticle( dbArticle.getId(), dbArticle.getHeadline(),
                        dbArticle.getDescription(), dbArticle.getMainText(),
                        publishedOnAsDate(dbArticle.getPublishedOn()));

            if(!CollectionUtils.isEmpty(dbArticle.getAuthors())) {
                article.setAuthors(convertAuthors(dbArticle.getAuthors()));
            }
            if(!CollectionUtils.isEmpty(dbArticle.getKeywords())) {
                article.setKeywords(convertKeywords(dbArticle.getKeywords()));
            }

            return article;
        }
        return null;
    }

    protected static List<RsAuthor> convertAuthors(final List<Author> dbAuthors) {
        final List<RsAuthor> rsAuthors = new ArrayList<>();
        dbAuthors.stream().forEach(
                (dbAuthor) -> {
            rsAuthors.add(convert(dbAuthor));
        });
        return rsAuthors;
    }

    protected static RsAuthor convert(final Author dbAuthor) {

        return new RsAuthor(dbAuthor.getId(), dbAuthor.getFirstname(), dbAuthor.getLastname());
    }

    protected static List<RsKeyword> convertKeywords(final List<Keyword> dbKeywords) {
        final List<RsKeyword> rsKeywords = new ArrayList<>();
        dbKeywords.stream().forEach(
                (dbKeyword) -> {
            rsKeywords.add(convert(dbKeyword));
        });
        return rsKeywords;
    }

    protected static RsKeyword convert(final Keyword dbKeyword) {

        return new RsKeyword(dbKeyword.getId(), dbKeyword.getName());
    }

    protected static List<RsArticle> convertArticles(final List<Article> dbArticles) {
        final List<RsArticle> rsArticles = new ArrayList<>();
        dbArticles.stream().forEach(
                (article) -> {
            rsArticles.add(convert(article));
        });
        return rsArticles;
    }

    /* Rest-Model to JPAs: */
    protected static Article convertToJpaArticle(final RsArticle article) {
        final Article dbArticle = new Article( article.getId(), article.getHeadline(),
                                            article.getTeaserText(), article.getMainText(),
                                            article.publishedOnAsLocalDate() );

        if(!CollectionUtils.isEmpty(article.getAuthors())) {
            article.getAuthors().stream().forEach(
                    (author) -> {
                dbArticle.addAuthor(convertToJpaAuthor(author));
            });
        }

        if(!CollectionUtils.isEmpty(article.getKeywords())) {
            article.getKeywords().stream().forEach(
                    (keyword) -> {
                dbArticle.addKeyword(convertToJpaKeyword(keyword));
            });
        }
        return dbArticle;
    }

    protected static Article convertToJpaArticle(final CreateArticle article) {
        final Article dbArticle = new Article( null, article.getHeadline(),
                                            article.getTeaserText(), article.getMainText(),
                                            article.publishedOnAsLocalDate() );

        if(!CollectionUtils.isEmpty(article.getAuthors())) {
            article.getAuthors().stream().forEach(
                    (author) -> {
                dbArticle.addAuthor(convertToJpaAuthor(author));
            });
        }

        if(!CollectionUtils.isEmpty(article.getKeywords())) {
            article.getKeywords().stream().forEach(
                    (keyword) -> {
                dbArticle.addKeyword(convertToJpaKeyword(keyword));
            });
        }
        return dbArticle;
    }

    protected static Article convertToJpaArticle(final UpdateArticle article, final Long id) {
        final Article dbArticle = new Article();

        dbArticle.setId(id);
        dbArticle.setDescription(article.getTeaserText());
        dbArticle.setHeadline(article.getHeadline());
        dbArticle.setMainText(article.getMainText());
        dbArticle.setPublishedOn(article.publishedOnAsLocalDate());

        if(!CollectionUtils.isEmpty(article.getAuthors())) {
            article.getAuthors().stream().forEach(
                    (author) -> {
                dbArticle.addAuthor(convertToJpaAuthor(author));
            });
        }

        if(!CollectionUtils.isEmpty(article.getKeywords())) {
            article.getKeywords().stream().forEach(
                    (keyword) -> {
                dbArticle.addKeyword(convertToJpaKeyword(keyword));
            });
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


    private static Date publishedOnAsDate(final LocalDate publishedOn) {

        return Date.from(publishedOn.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
