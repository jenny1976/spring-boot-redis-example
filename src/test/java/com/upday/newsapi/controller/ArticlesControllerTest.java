package com.upday.newsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upday.newsapi.model.Article;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.service.ArticleService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

    @Before
    public void setUp() {
        articleService = Mockito.mock(ArticleService.class);
        mvc = MockMvcBuilders.standaloneSetup(new ArticlesController(articleService)).build();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testCreateArticle() throws Exception {
        System.out.println("----- createArticle");
        CreateArticle article = new CreateArticle(null, "subheadline", "text");

        Article dummy2 = new Article("1", "headline", "subheadline", "text",
                Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)).toString());

        when(articleService.createArticle(article)).thenReturn(dummy2);

        // empty request
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));

        // ivalid request content
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There are invalid arguments in 'newArticle'."));

        // ivalid CreateArticle content
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

        verify(articleService, times(1)).createArticle(article);

    }


    @Test @Ignore
    public void testGetArticleByDateRange() throws Exception {
        System.out.println("----- getArticleByDateRange");

        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/2015-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // invalid date-format
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-122/2015-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/15-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // invalid date-range
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/2012-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("First Date has to be before the second one!"));

    }

    @Test
    public void testUpdateArticle() throws Exception {
        System.out.println("----- updateArticle");
        UpdateArticle toUpdate = new UpdateArticle(null, "subheadline", "text",
                Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)).toString());

        Article dummy = new Article();
        dummy.setId("13");
        dummy.setHeadline("headline");
        dummy.setTeaserText("subheadline");
        dummy.setMainText("text");
        dummy.setPublishedOn(Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)).toString());

        when(articleService.updateArticle(toUpdate, "13")).thenReturn(dummy);

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
                .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There are invalid arguments in 'updateArticle'."));

        toUpdate.setHeadline("headline");

        // valid request
        mvc.perform(MockMvcRequestBuilders.post("/articles/11").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.post("/articles/13").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk());

        verify(articleService, times(1)).updateArticle(toUpdate, "13");

    }

    @Test
    public void testDeleteArticle() throws Exception {
        System.out.println("----- deleteArticle");

        when(articleService.deleteArticle("1")).thenReturn(true);

        // should be valid
        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(articleService, times(1)).deleteArticle("1");


        reset(articleService);
        // valid, but entity not found
        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid articleId!")
                );


    }

    @Test
    public void testGetArticle() throws Exception {
        System.out.println("----- getArticle");

        // valid
        when(articleService.findOne("1")).thenReturn(new Article("1", "h", "d", "m",
                Date.from(Instant.parse("2012-12-12T00:00:00.00Z").minus(1, ChronoUnit.HOURS)).toString()));
        mvc.perform(MockMvcRequestBuilders.get("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).findOne("1");
        reset(articleService);

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // valid request but not found
        mvc.perform(MockMvcRequestBuilders.get("/articles/100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test @Ignore
    public void testGetArticleByAuthor() throws Exception {
        System.out.println("----- getArticleByAuthor");

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/author").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/author/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test @Ignore
    public void testGetArticleByKeyword() throws Exception {
        System.out.println("----- getArticleByKeyword");

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/search/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/search/berlin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
