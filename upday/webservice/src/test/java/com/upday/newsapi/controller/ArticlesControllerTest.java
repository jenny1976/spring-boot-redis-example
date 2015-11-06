package com.upday.newsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.service.ArticleService;
import java.sql.Date;
import java.time.LocalDate;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author jschulz
 */

public class ArticlesControllerTest {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private MockMvc mvc;
    private ArticleService articleService;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        articleService = Mockito.mock(ArticleService.class);
        mvc = MockMvcBuilders.standaloneSetup(new ArticlesController(articleService)).build();
    }
    
    @After
    public void tearDown() {
        reset(articleService);
    }

    
    @Test
    public void testCreateArticle() throws Exception {
        System.out.println("----- createArticle");
        CreateArticle article = new CreateArticle(null, "subheadline", "text", Date.valueOf("2014-12-12"));
        
        Article dummy = new Article();
        dummy.setHeadline("headline");
        dummy.setDescription("subheadline");
        dummy.setMainText("text");
        dummy.setPublishedOn(LocalDate.parse("2014-12-12"));
        when(articleService.updateArticle(dummy)).thenReturn(Mockito.mock(Article.class));
        
        // empty request
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
        
        // ivalid request content 
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There are invalid arguments in 'newArticle'."));
        
        // ivalid UpdateArticle content 
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There are invalid arguments in 'newArticle'."));
        
        // valid request
        article.setHeadline("headline");
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(article))
                )
                .andExpect(status().isOk());
        
        verify(articleService, times(1)).createArticle(org.mockito.Matchers.anyObject());
        
    }

    
    @Test
    public void testGetArticleByDateRange() throws Exception {
        System.out.println("----- getArticleByDateRange");
        
        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/2015-12-12").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        // invalid date-format
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-122/2015-12-12").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/15-12-12").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
        
        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        // invalid date-range
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/2012-12-12").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("First Date has to be before the second one!"));
            
    }

    @Test
    public void testUpdateArticle() throws Exception {
        System.out.println("----- updateArticle");
        UpdateArticle article = new UpdateArticle(null, "subheadline", "text", Date.valueOf("2014-12-12"));
        
        Article dummy = new Article();
        dummy.setId(13L);
        dummy.setHeadline("headline");
        dummy.setDescription("subheadline");
        dummy.setMainText("text");
        dummy.setPublishedOn(LocalDate.parse("2014-12-12"));
        when(articleService.updateArticle(dummy)).thenReturn(Mockito.mock(Article.class));
        
        // empty request
        mvc.perform(MockMvcRequestBuilders.post("/articles/12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
        
        // ivalid request content 
        mvc.perform(MockMvcRequestBuilders.post("/articles/12").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There are invalid arguments in 'updateArticle'."));
        
        // ivalid UpdateArticle content 
        mvc.perform(MockMvcRequestBuilders.post("/articles/12").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There are invalid arguments in 'updateArticle'."));
        
        article.setHeadline("headline");
        
        // valid request
        mvc.perform(MockMvcRequestBuilders.post("/articles/12").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""));
        
        mvc.perform(MockMvcRequestBuilders.post("/articles/13").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk());
        
        verify(articleService, times(2)).updateArticle(org.mockito.Matchers.anyObject());
        
    }

    @Test
    public void testDeleteArticle() throws Exception {
        System.out.println("----- deleteArticle");
        
        when(articleService.deleteArticle(1L)).thenReturn(true);
        
        // should be valid
        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(articleService, times(1)).deleteArticle(1L);
        
        
        reset(articleService);
        // valid, but entity not found
        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid articleId!")
                );
        
        
    }

    @Test
    public void testGetArticle() throws Exception {
        System.out.println("----- getArticle");
        
        // valid
        when(articleService.findOne(1L)).thenReturn(new Article());
        mvc.perform(MockMvcRequestBuilders.get("/articles/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).findOne(1L);
        reset(articleService);
        
        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        // valid request but not found
        mvc.perform(MockMvcRequestBuilders.get("/articles/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
         
    }

    @Test
    public void testGetArticleByAuthor() throws Exception {
        System.out.println("----- getArticleByAuthor");
        
        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/author").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/author/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetArticleByKeyword() throws Exception {
        System.out.println("----- getArticleByKeyword");
        
        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/search/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/search/berlin").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
