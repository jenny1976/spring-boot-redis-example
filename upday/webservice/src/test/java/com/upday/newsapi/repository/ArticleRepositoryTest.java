package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jschulz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = DBConfig.class)
public class ArticleRepositoryTest {
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private KeywordRepository keywordRepository;
    
    public ArticleRepositoryTest() {
    }
    
    @Before
    public void emptyDB() {
        articleRepository.deleteAll();
        authorRepository.deleteAll();
        keywordRepository.deleteAll();
    }
    
    @Test
    public void testInsert() {
        
        Article a = createOne();
        
        articleRepository.save(a);
        
        Assert.assertEquals(1, articleRepository.count());
        Assert.assertEquals(2, a.getKeywords().size());
        Assert.assertEquals(2, a.getAuthors().size());
        Assert.assertNotNull(a.getId());
        
    }
    
    @Test
    public void testUpdate() {
        
        Article a = createOne();
        
        articleRepository.save(a);
        
        a = articleRepository.findOne(a.getId());
        
        a.setHeadline("changed headline");
        
        a = articleRepository.save(a);
        
        Assert.assertEquals("changed headline", a.getHeadline());
        
    }

    
    @Test
    public void testDelete() {
        Article a = createOne();
        
        articleRepository.save(a);
        
        Assert.assertEquals(1, articleRepository.count());
        
        Long id = a.getId();
        articleRepository.delete(a);
        
        Assert.assertFalse(articleRepository.exists(id));
    }

    @Test
    public void testFindByAuthorsId() {
        Article a = createOne();

        articleRepository.save(a);
        Assert.assertEquals(1, articleRepository.count());
        
        Long authorId = a.getAuthors().get(0).getId();
        
        List<Article> articles = articleRepository.findByAuthorsId(authorId);
        
        Assert.assertNotNull(articles);
        Assert.assertThat(articles.size(), equalTo(1));
        Assert.assertThat(articles.get(0).getAuthors().size(), equalTo(2));
    }

    @Test
    public void testFindByKeywordsNameIgnoreCase() {
        createArticleList();
        
        List<Article> articles = articleRepository.findByKeywordsNameIgnoreCase("test");
        
        Assert.assertNotNull(articles);
        Assert.assertThat(keywordRepository.count(), equalTo(4L));
        Assert.assertThat(articles.size(), equalTo(2));
        
        articles = articleRepository.findByKeywordsNameIgnoreCase("Test");
        Assert.assertThat(articles.size(), equalTo(2));
        
        articles = articleRepository.findByKeywordsNameContainsIgnoreCase("test");
        Assert.assertThat(articles.size(), equalTo(4));
    }

    @Test
    public void testFindByPublishedOnBetween() {
        createArticleList();
        
        List<Article> articles = articleRepository.findByPublishedOnBetween(LocalDate.parse("2012-12-12"), LocalDate.now());
        Assert.assertThat(articles.size(), equalTo(2));
        
        articles = articleRepository.findByPublishedOnBetween(LocalDate.parse("2012-12-12"), LocalDate.parse("2014-12-12"));
        Assert.assertThat(articles.size(), equalTo(0));
    }

    
    private Article createOne() {
        Article a = new Article();
        a.setHeadline("test headline");
        a.setDescription("test description");
        a.setMainText("test main text");
        a.setPublishedOn(LocalDate.now());
        a.addAuthor(authorRepository.save(new Author("first1", "last1")));
        a.addAuthor(authorRepository.save(new Author("first2", "last2")));
        a.addKeyword(keywordRepository.save(new Keyword("test")));
        a.addKeyword(keywordRepository.save(new Keyword("test kw2")));
        
        return a;
    }
    
    private void createArticleList() {
        Set<Article> list = new HashSet<>();
        list.add(articleRepository.save(createOne()));
        list.add(articleRepository.save(createOne()));
        
    }
}
