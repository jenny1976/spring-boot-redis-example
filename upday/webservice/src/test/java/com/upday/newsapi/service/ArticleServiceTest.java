package com.upday.newsapi.service;

import com.upday.newsapi.repository.ArticleRepository;
import com.upday.newsapi.repository.AuthorRepository;
import com.upday.newsapi.repository.KeywordRepository;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.util.Assert;

/**
 *
 * @author jschulz
 */
public class ArticleServiceTest {
    
    private ArticleRepository articleRepository;
    private KeywordRepository keywordRepository;
    private AuthorRepository authorRepository;
    
    public ArticleServiceTest() {
    }
    
    @Before
    public void setUp() {
        articleRepository = Mockito.mock(ArticleRepository.class);
        keywordRepository = Mockito.mock(KeywordRepository.class);
        authorRepository = Mockito.mock(AuthorRepository.class);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateArticle() {
        Article dummy = new Article();
        dummy.setDescription("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(LocalDate.now());
        
        Article dummy2 = dummy;
        dummy2.setId(33L);
        
        Mockito.stub(articleRepository.save(dummy)).toReturn(dummy2);
        Mockito.stub(articleRepository.findOne(33L)).toReturn(dummy2);
        
        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);
        Article result = toTest.createArticle(dummy);
        
        Mockito.verify(articleRepository, times(1)).save(dummy);
        Mockito.verify(articleRepository, times(1)).findOne(33L);
        Assert.isTrue(result.equals(dummy2));
    }
    
    @Test @Ignore
    public void testCreateArticleWithAK() {
        Article dummy = new Article();
        dummy.setDescription("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(LocalDate.now());
        
        List<Author> authors = new ArrayList<>(2);
        final Author a1 = new Author("f1", "l1");
        authors.add(a1);
        final Author a2 = new Author("f2", "l2");
        authors.add(a2);
        dummy.setAuthors(authors);
        
        Article dummyWithoutAuth = new Article();
        dummyWithoutAuth.setDescription("dummy description");
        dummyWithoutAuth.setHeadline("dummy headline");
        dummyWithoutAuth.setMainText("dummy text");
        dummyWithoutAuth.setPublishedOn(dummy.getPublishedOn());
        dummyWithoutAuth.setAuthors(new ArrayList<>(2));
        
        Article dummyWithId = new Article();
        dummyWithId.setId(33L);
        dummyWithId.setDescription("dummy description");
        dummyWithId.setHeadline("dummy headline");
        dummyWithId.setMainText("dummy text");
        dummyWithId.setPublishedOn(dummy.getPublishedOn());
        dummyWithId.setAuthors(new ArrayList<>(2));
        
//        Article expected = dummy;
//        expected.setId(33L);
//        expected.setAuthors(new ArrayList<>(2));
//        
        final Author a11 = new Author("f1", "l1");
        a11.setId(4L);
        final Author a21 = new Author("f2", "l2");
        a21.setId(5L);
//        expected.addAuthor(a21);
//        expected.addAuthor(a11);
        
        Mockito.when(articleRepository.save(dummyWithoutAuth)).thenReturn(dummyWithId);
        Mockito.when(articleRepository.findOne(Mockito.any())).thenReturn(dummyWithId);
        Mockito.when(authorRepository.saveAndFlush(a1)).thenReturn(a11);
        Mockito.when(authorRepository.saveAndFlush(a2)).thenReturn(a21);
        
        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);
        Article result = toTest.createArticle(dummy);
        
        Mockito.verify(articleRepository, times(1)).save(Mockito.any(Article.class));
        Mockito.verify(articleRepository, times(1)).saveAndFlush(Mockito.any(Article.class));
        Mockito.verify(articleRepository, times(2)).findOne(Mockito.any());
        Mockito.verify(authorRepository, times(2)).saveAndFlush(Mockito.any());
//        Assert.isTrue(result.equals(expected));
    }

    @Test
    public void testUpdateArticle() {
    }

    @Test
    public void testDeleteArticle() {
    }

    @Test
    public void testFindOne() {
        Mockito.stub(articleRepository.findOne(1L)).toReturn(new Article());
        
        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);
        Article result = toTest.findOne(1L);
        
        Assert.notNull(result);
        verify(articleRepository, times(1)).findOne(1L);
    }
    
}
