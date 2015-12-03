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
import org.springframework.util.Assert;

/**
 *
 * @author jschulz
 */
public class ArticleServiceTest {


    public ArticleServiceTest() {
    }

    @Before
    public void setUp() {
//        articleRepository = Mockito.mock(ArticleRepository.class);
    }

    @After
    public void tearDown() {
//        reset(articleRepository);
    }

    @Test @Ignore
    public void testCreateArticle() {
        CreateArticle dummy = new CreateArticle();
        dummy.setTeaserText("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));

        Article dummy2 = ModelConverter.convertToNewArticle(dummy);
        dummy2.setId(33L);

        Article dummy3 = ModelConverter.convertToNewArticle(dummy);

//        Mockito.stub(articleRepository.save(dummy3)).toReturn(dummy2);
//        Mockito.stub(articleRepository.findOne(33L)).toReturn(dummy2);

        final ArticleService toTest = new ArticleService(new RedisTemplate());
        Article result = toTest.createArticle(dummy);

//        Mockito.verify(articleRepository, times(1)).save(dummy3);
//        Mockito.verify(articleRepository, times(1)).findOne(33L);
        Assert.isTrue(result.equals(dummy2));
    }

    @Test @Ignore
    public void testCreateArticleWithAK() {
        CreateArticle dummy = new CreateArticle();
        dummy.setTeaserText("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));

        List<Author> authors = new ArrayList<>(2);
        final Author a1 = new Author("f1", "l1");
        authors.add(a1);
        final Author a2 = new Author("f2", "l2");
        authors.add(a2);
        dummy.setAuthors(authors);

        Keyword k1 = new Keyword("hot");
        Keyword k2 = new Keyword("stuff");
        dummy.getKeywords().add(k1);
        dummy.getKeywords().add(k2);


        Article dummyWithoutAuth = new Article();
        dummyWithoutAuth.setTeaserText("dummy description");
        dummyWithoutAuth.setHeadline("dummy headline");
        dummyWithoutAuth.setMainText("dummy text");
        dummyWithoutAuth.setPublishedOn(dummy.getPublishedOn());

        Article dummyWithId = new Article();
        dummyWithId.setId(33L);
        dummyWithId.setTeaserText("dummy description");
        dummyWithId.setHeadline("dummy headline");
        dummyWithId.setMainText("dummy text");
        dummyWithId.setPublishedOn(dummy.getPublishedOn());

        Article expected = new Article();
        expected.setId(33L);
        expected.setTeaserText("dummy description");
        expected.setHeadline("dummy headline");
        expected.setMainText("dummy text");
        expected.setPublishedOn(dummy.getPublishedOn());

        final Author a11 = new Author("f1", "l1");
        a11.setId(4L);
        final Author a21 = new Author("f2", "l2");
        a21.setId(5L);
        expected.getAuthors().add(a21);
        expected.getAuthors().add(a11);

        Keyword k11 = new Keyword("hot");
        k11.setId(9L);
        Keyword k21 = new Keyword("stuff");
        k21.setId(23L);
        expected.getKeywords().add(k11);
        expected.getKeywords().add(k21);

//        Mockito.when(articleRepository.save(Mockito.any(Article.class))).thenReturn(dummyWithId);
//        Mockito.when(articleRepository.getOne(33L)).thenReturn(dummyWithId);
//        Mockito.when(articleRepository.findOne(33L)).thenReturn(expected);
//        Mockito.when(authorRepository.saveAndFlush(a1)).thenReturn(a11);
//        Mockito.when(authorRepository.saveAndFlush(a2)).thenReturn(a21);
//        Mockito.when(keywordRepository.saveAndFlush(k1)).thenReturn(k11);
//        Mockito.when(keywordRepository.saveAndFlush(k2)).thenReturn(k21);

        final ArticleService toTest = new ArticleService(new RedisTemplate());
        Article result = toTest.createArticle(dummy);

//        Mockito.verify(articleRepository, times(1)).save(Mockito.any(Article.class));
//        Mockito.verify(articleRepository, times(2)).saveAndFlush(Mockito.any(Article.class));
//        Mockito.verify(articleRepository, times(2)).getOne(33L);
//        Mockito.verify(articleRepository, times(1)).findOne(33L);

//        Mockito.verify(authorRepository, times(2)).saveAndFlush(Mockito.any());
//        Mockito.verify(keywordRepository, times(2)).saveAndFlush(Mockito.any());
        Assert.notNull(result);

        System.out.println("result: "+result);
        System.out.println("expected: " + expected);
        Assert.isTrue(result==expected);
    }

    @Test
    public void testUpdateArticle() {
    }

    @Test
    public void testDeleteArticle() {

//        Mockito.when(articleRepository.exists(1L)).thenReturn(false);
//        Mockito.when(articleRepository.exists(2L)).thenReturn(true);

        final ArticleService toTest = new ArticleService(new RedisTemplate());

        boolean res = toTest.deleteArticle(1L);
        boolean res2 = toTest.deleteArticle(2L);

//        verify(articleRepository, times(1)).exists(1L);
//        verify(articleRepository, times(1)).exists(2L);
    }

    @Test
    public void testFindOne() {
//        Mockito.stub(articleRepository.findOne(1L)).toReturn(new Article());

        final ArticleService toTest = new ArticleService(new RedisTemplate());
        Article result = toTest.findOne(1L);

        Assert.notNull(result);
//        verify(articleRepository, times(1)).findOne(1L);
    }

}
