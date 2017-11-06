package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Keyword;
import com.upday.newsapi.model.UpdateArticle;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jschulz
 */
public class ModelConverterTest {

    private Article dummyArticle;

    public ModelConverterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws ParseException {

        dummyArticle =  new Article("1", "headline", "dummy teaser text", "dummy maintext", Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)).toString());

        Author rsAuthor = new Author( "firstname1", "lastname1");
        Author rsAuthor2 = new Author("firstname2", "lastname2");
        List<Author> rsAuthors = new ArrayList<>(2);
        rsAuthors.add(rsAuthor);
        rsAuthors.add(rsAuthor2);
        dummyArticle.setAuthors(rsAuthors);

        Keyword rsKeyword = new Keyword("keyword1");
        Keyword rsKeyword2 = new Keyword("keyword2");
        List<Keyword> keywords = new ArrayList<>(2);
        keywords.add(rsKeyword);
        keywords.add(rsKeyword2);
        dummyArticle.setKeywords(keywords);

    }

    @Test
    public void testConvertToArticle() {
        UpdateArticle updateArticle = new UpdateArticle("headline", "dummy teaser text", "dummy maintext",
                Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)).toString(), null, null);
        updateArticle.getAuthors().add(new Author("firstname1", "lastname1"));
        updateArticle.getAuthors().add(new Author("firstname2", "lastname2"));
        updateArticle.getKeywords().add(new Keyword("keyword1"));
        updateArticle.getKeywords().add(new Keyword("keyword2"));

        Article result = ModelConverter.convertToArticle(updateArticle, "1");

        Assert.assertEquals(dummyArticle, result);
    }

    @Test
    public void testConvertToNewArticle() {
        CreateArticle article = new CreateArticle("headline", "dummy teaser text", "dummy maintext", null, null);
        article.getAuthors().add(new Author("firstname1", "lastname1"));
        article.getAuthors().add(new Author("firstname2", "lastname2"));
        article.getKeywords().add(new Keyword("keyword1"));
        article.getKeywords().add(new Keyword("keyword2"));

        Article result = ModelConverter.convertToNewArticle(article);

        Assert.assertEquals(dummyArticle.getAuthors(), result.getAuthors());
        Assert.assertEquals(dummyArticle.getKeywords(), result.getKeywords());
        Assert.assertEquals(dummyArticle.getHeadline(), result.getHeadline());
        Assert.assertEquals(dummyArticle.getTeaserText(), result.getTeaserText());
        Assert.assertEquals(dummyArticle.getMainText(), result.getMainText());
        Assert.assertNotNull(result.getPublishedOn());
    }

}
