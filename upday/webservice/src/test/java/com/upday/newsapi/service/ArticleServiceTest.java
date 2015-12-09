package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Keyword;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

/**
 *
 * @author jschulz
 */
public class ArticleServiceTest {

    private RedisTemplate<String, String> template;


    public ArticleServiceTest() {
    }

    @Before
    public void setUp() {
        template = Mockito.mock(RedisTemplate.class);
    }

    @After
    public void tearDown() {
        reset(template);
    }

    @Test
    public void testCreateArticle() {
        CreateArticle dummy = new CreateArticle();
        dummy.setTeaserText("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));

        Article dummy2 = ModelConverter.convertToNewArticle(dummy);
        dummy2.setId("33");

        ValueOperations operations = Mockito.mock(ValueOperations.class);
        Mockito.when(template.opsForValue()).thenReturn(operations);


        final ArticleService toTest = new ArticleService(template);
        Article result = toTest.createArticle(dummy);

        Mockito.verify(operations, times(4)).set(Mockito.anyString(), Mockito.anyString());
        Assert.notNull(result);
        Assert.notNull(result.getId());
        Assert.notNull(result.getHeadline());
        Assert.notNull(result.getMainText());
        Assert.notNull(result.getTeaserText());
    }

    @Test
    public void testCreateArticleWithAK() {
        CreateArticle dummy = new CreateArticle();
        dummy.setTeaserText("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));

        dummy.getAuthors().add(new Author("f1", "l1"));
        dummy.getAuthors().add(new Author("f2", "l2"));

        dummy.getKeywords().add(new Keyword("hot"));
        dummy.getKeywords().add(new Keyword("stuff"));

        ValueOperations operations = Mockito.mock(ValueOperations.class);
        Mockito.when(template.opsForValue()).thenReturn(operations);


        final ArticleService toTest = new ArticleService(template);
        Article result = toTest.createArticle(dummy);

        Mockito.verify(operations, times(10)).set(Mockito.anyString(), Mockito.anyString());
        Assert.notNull(result);
        Assert.notNull(result.getId());
        Assert.notEmpty(result.getAuthors());
        Assert.notEmpty(result.getKeywords());
    }

    @Test
    public void testUpdateArticle() {
    }

    @Test
    public void testDeleteArticle() {

//        Mockito.when(articleRepository.exists(1L)).thenReturn(false);
//        Mockito.when(articleRepository.exists(2L)).thenReturn(true);

        final ArticleService toTest = new ArticleService(template);

        boolean res = toTest.deleteArticle("1");
        boolean res2 = toTest.deleteArticle("1");

//        verify(articleRepository, times(1)).exists(1L);
//        verify(articleRepository, times(1)).exists(2L);
    }

    @Test
    public void testFindOne() {
//        Mockito.stub(articleRepository.findOne(1L)).toReturn(new Article());

        final ArticleService toTest = new ArticleService(template);
        Article result = toTest.findOne(1L);

        Assert.notNull(result);
//        verify(articleRepository, times(1)).findOne(1L);
    }

}
