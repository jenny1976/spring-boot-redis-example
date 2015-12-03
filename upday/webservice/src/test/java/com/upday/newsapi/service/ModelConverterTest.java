package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.Keyword;
import com.upday.newsapi.model.UpdateArticle;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jschulz
 */
public class ModelConverterTest {

    private final Article dummyArticle = new Article();

    public ModelConverterTest() {
    }

    @Before
    public void setUp() throws ParseException {
        dummyArticle.setHeadline("headline");
        dummyArticle.setId(1L);
        dummyArticle.setMainText("dummy maintext");
        dummyArticle.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));
        dummyArticle.setTeaserText("dummy teaser text");

        Author rsAuthor = new Author(2L, "firstname1", "lastname1");
        Author rsAuthor2 = new Author(3L, "firstname2", "lastname2");
        List<Author> rsAuthors = new ArrayList<>(2);
        rsAuthors.add(rsAuthor);
        rsAuthors.add(rsAuthor2);
        dummyArticle.setAuthors(rsAuthors);

        Keyword rsKeyword = new Keyword(5L, "keyword1");
        Keyword rsKeyword2 = new Keyword(6L, "keyword2");
        List<Keyword> keywords = new ArrayList<>(2);
        keywords.add(rsKeyword);
        keywords.add(rsKeyword2);
        dummyArticle.setKeywords(keywords);

    }

    @After
    public void tearDown() {
    }


    @Test
    public void testConvertToArticle() {
        UpdateArticle updateArticle = new UpdateArticle("headline", "dummy teaser text", "dummy maintext",
                Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));
        updateArticle.getAuthors().add(new Author(2L, "firstname1", "lastname1"));
        updateArticle.getAuthors().add(new Author(3L, "firstname2", "lastname2"));
        updateArticle.getKeywords().add(new Keyword(5L, "keyword1"));
        updateArticle.getKeywords().add(new Keyword(6L, "keyword2"));

        Article result = ModelConverter.convertToArticle(updateArticle, 1L);

        Assert.assertEquals(dummyArticle, result);
    }

}
