package com.upday.service.web;

import com.upday.service.web.rs.domain.RsArticle;
import java.util.List;
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
@RestController(value = "/article")
public class ServiceController {
    
    
    @RequestMapping(
            value="/create", 
            method = RequestMethod.PUT,
            consumes = "application/xml",
            produces = "application/xml"
    )
    public @ResponseBody RsArticle createArticle(@RequestBody RsArticle rsArticle) {
        return new RsArticle();
    }
    
    @RequestMapping(
            value="/update/{articleId}", 
            method = RequestMethod.POST,
            produces = "application/xml"
    )
    public @ResponseBody RsArticle updateArticle(@PathVariable("articleId") Long articleId) {
        return new RsArticle();
    }
    
    @RequestMapping(
            value="/delete/{articleId}", 
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
            value="/author/{author}", 
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> findArticleByAuthor(@PathVariable("author") String author) {
        
        return null;
    }
    
    @RequestMapping(
            value="/date/{from}/{to}", 
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> findArticleByDate(@PathVariable("from") String from, 
            @PathVariable("to") String to) {
        return null;
    }
    
    @RequestMapping(
            value="/search/{searchKeyword}", 
            method = RequestMethod.GET
    )
    public @ResponseBody List<RsArticle> findArticleByKeyword(
            @PathVariable("searchKeyword") String searchKeyword) {
        return null;
    }
    
}
