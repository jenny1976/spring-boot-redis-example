package com.upday.newsapi.service;

import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.Author;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.Keyword;
import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author jschulz
 */
@ContextConfiguration(classes = {RedisConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

    @Inject
    private StringRedisTemplate template;

    private RedisAtomicLong articleIdCounter;
    private RedisAtomicLong authorIdCounter;


    public ArticleServiceTest() {
    }

    @Before
    public void setUp() {
//        template = Mockito.mock(StringRedisTemplate.class);
//        Mockito.when(template.getConnectionFactory()).thenReturn(Mockito.mock(JedisConnectionFactory.class));
        articleIdCounter = new RedisAtomicLong(KeyUtils.globalArticleId(), template.getConnectionFactory());
        authorIdCounter = new RedisAtomicLong(KeyUtils.globalAuthorId(), template.getConnectionFactory());
    }

    @After
    public void tearDown() {
//        reset(template);
    }

    @Test
    public void testCreateArticle() {
        CreateArticle dummy = new CreateArticle("dummy headline", "dummy description", "dummy text", null, null);
//        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));

        final ArticleService toTest = new ArticleService(template);
        Article result = toTest.createArticle(dummy);

        Assert.notNull(result);
        Assert.notNull(result.getId());
        Assert.notNull(result.getHeadline());
        Assert.notNull(result.getMainText());
        Assert.notNull(result.getTeaserText());
    }

    @Test
    public void testCreateArticleWithAK() {
        CreateArticle dummy = new CreateArticle("dummy headline", "dummy description", "dummy text", null, null);
//        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)));

        dummy.getAuthors().add(new Author("f1", "l1"));
        dummy.getAuthors().add(new Author("f2", "l2"));

        dummy.getKeywords().add(new Keyword("hot"));
        dummy.getKeywords().add(new Keyword("stuff"));


        final ArticleService toTest = new ArticleService(template);
        Article result = toTest.createArticle(dummy);

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


        final ArticleService toTest = new ArticleService(template);

        boolean res = toTest.deleteArticle("1");
        boolean res2 = toTest.deleteArticle("1");

    }

    @Test
    public void testFindOne() {

        final ArticleService toTest = new ArticleService(template);
        Article result = toTest.findOne("1");

        Assert.notNull(result);
    }

}
