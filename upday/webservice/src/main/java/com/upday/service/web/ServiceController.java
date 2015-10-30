package com.upday.service.web;

import com.upday.service.persistence.api.ArticleRepository;
import com.upday.service.persistence.domain.Article;
import com.upday.service.persistence.domain.Author;
import com.upday.service.web.rs.domain.RsArticle;
import com.upday.service.web.rs.domain.RsAuthor;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
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
@RequestMapping(value = "/articles")
public class ServiceController {
    
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(
            value = "/",
            method = RequestMethod.PUT
    )
    public @ResponseBody RsArticle createArticle(@RequestBody RsArticle rsArticle) {

        RsArticle article = new RsArticle();
        article.setId(0L);
        article.setHeadline("test headline");
        article.setMainText("test maintext");
        article.setShortDescription("test description");
        article.setPublishedOn(new DateTime().toDate());
        
        articleRepository.save(new Article());
        
        return article;
    }

    @RequestMapping(
            value = "/{articleId}",
            method = RequestMethod.POST
    )
    public @ResponseBody RsArticle updateArticle(@PathVariable("articleId") Long articleId) {

        RsArticle article = new RsArticle();
        article.setId(articleId);
        article.setHeadline("test headline");
        article.setMainText("test maintext");
        article.setShortDescription("test description");
        article.setPublishedOn(new DateTime().toDate());
        
        RsAuthor author = new RsAuthor(1L, "Test", "Author1");
        RsAuthor author2 = new RsAuthor(2L, "Test", "Author2");
        RsAuthor author3 = new RsAuthor(3L, "Test", "Author3");
        List<RsAuthor> authors = new ArrayList<>(3);
        authors.add(author);
        authors.add(author2);
        authors.add(author3);
        
        article.setAuthors(authors);
        return article;
    }

    @RequestMapping(
            value = "/{articleId}",
            method = RequestMethod.DELETE
    )
    public void deleteArticle(@PathVariable("articleId") Long articleId) {
        //
    }
    
    @RequestMapping(
            value = "/{articleId}",
            method = RequestMethod.GET
    )
    public RsArticle getArticle(@PathVariable("articleId") Long articleId) {
        final Article dbArticle = articleRepository.findOne(articleId);
        
        return convert(dbArticle);
    }

    /**
     *
     * @param authorId
     * @return
     */
    @RequestMapping(
            value = "/author/{authorId}",
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> getArticleByAuthor(@PathVariable("authorId") Long authorId) {

        return null;
    }

    @RequestMapping(
            value = "/date/{from}/{to}",
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> getArticleByDateRange(@PathVariable("from") String from,
            @PathVariable("to") String to) {
        return null;
    }

    @RequestMapping(
            value = "/search/{searchKeyword}",
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> getArticleByKeyword(
            @PathVariable("searchKeyword") String searchKeyword) {
        return null;
    }

    private RsArticle convert(final Article dbArticle) {
        final RsArticle article = new RsArticle();
        article.setId(dbArticle.getId());
        article.setHeadline(dbArticle.getHeadline());
        article.setShortDescription(dbArticle.getDescription());
        article.setMainText(dbArticle.getMainText());
        article.setAuthors(convert(dbArticle.getAuthors()));
        article.setPublishedOn(dbArticle.getPublishedOn());
        
        return article;
    }

    private List<RsAuthor> convert(final List<Author> dbAuthors) {
        final List<RsAuthor> rsAuthors = new ArrayList<>();
        for (Author dbAuthor : dbAuthors) {
            rsAuthors.add(convert(dbAuthor));
        }
        return rsAuthors;
    }
    
    private RsAuthor convert(final Author dbAuthor) {
        final RsAuthor author = new RsAuthor(dbAuthor.getId(), dbAuthor.getFirstname(), 
                dbAuthor.getLastname());
        return author;
    }
}
