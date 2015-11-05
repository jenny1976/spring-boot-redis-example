package com.upday.newsapi.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * a REST article representation.
 * 
 * @author jschulz
 */
public class RsArticle implements Serializable {
    
    private static final long serialVersionUID = -4150137843471878449L;
    
    private Long id;
    private String headline;
    private String teaserText;
    private String mainText;
    
    private LocalDate publishedOn;
    
    private List<RsAuthor> authors;
    private List<RsKeyword> keywords;

    public List<RsAuthor> getAuthors() {
        return authors;
    }

    public String getHeadline() {
        return headline;
    }

    public Long getId() {
        return id;
    }

    public List<RsKeyword> getKeywords() {
        return keywords;
    }

    public String getMainText() {
        return mainText;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public String getTeaserText() {
        return teaserText;
    }

    public void setAuthors(List<RsAuthor> authors) {
        this.authors = authors;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKeywords(List<RsKeyword> keywords) {
        this.keywords = keywords;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setTeaserText(String teaserText) {
        this.teaserText = teaserText;
    }

    @Override
    public String toString() {
        return "RsArticle{" + "id=" + id + ", header=" + headline + ", shortDescription=" 
                + teaserText + ", mainText=" + mainText + ", publishedOn=" + publishedOn 
                + ", authors=" + authors + ", keywords=" + keywords + '}';
    }

}
