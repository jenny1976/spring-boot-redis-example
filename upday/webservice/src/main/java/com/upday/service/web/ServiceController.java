package com.upday.service.web;

import com.upday.service.persistence.api.MemoryService;
import com.upday.service.web.rs.domain.RsArticle;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles Requests for the News-Article Service.
 *
 * @author jschulz
 */
@RestController
@RequestMapping(value = "/article")
public class ServiceController {

    @Autowired
    private MemoryService memoryService;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.PUT
    )
    public @ResponseBody
    RsArticle createArticle(@RequestBody RsArticle rsArticle) {

        RsArticle article = new RsArticle();
        article.setId(0L);
        article.setHeader("test headline");
        article.setMainText("test maintext");
        article.setPublishedOn(Date.valueOf("2012-23-01"));
        article.setShortDescription("test description");
        return article;
    }

    @RequestMapping(
            value = "/update/{articleId}",
            method = RequestMethod.POST
    )
    public @ResponseBody
    RsArticle updateArticle(@PathVariable("articleId") Long articleId) {

        RsArticle article = new RsArticle();
        article.setId(articleId);
        article.setHeader("test headline");
        article.setMainText("test maintext");
//        article.setPublishedOn(new java.util.Date("2012-23-01"));
        article.setShortDescription("test description");
        return article;
    }

    @RequestMapping(
            value = "/delete/{articleId}",
            method = RequestMethod.DELETE
    )
    public void deleteArticle(@PathVariable("articleId") Long articleId) {
        //
    }

    /**
     *
     * @param author
     * @return
     */
    @RequestMapping(
            value = "/author/{author}",
            method = RequestMethod.GET
    )
    public @ResponseBody
    List<RsArticle> findArticleByAuthor(@PathVariable("author") String author) {

        return null;
    }

    @RequestMapping(
            value = "/date/{from}/{to}",
            method = RequestMethod.GET
    )
    public @ResponseBody
    List<RsArticle> findArticleByDate(@PathVariable("from") String from,
            @PathVariable("to") String to) {
        return null;
    }

    @RequestMapping(
            value = "/search/{searchKeyword}",
            method = RequestMethod.GET
    )
    public @ResponseBody
    List<RsArticle> findArticleByKeyword(
            @PathVariable("searchKeyword") String searchKeyword) {
        return null;
    }

}
