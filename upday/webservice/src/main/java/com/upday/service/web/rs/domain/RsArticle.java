package com.upday.service.web.rs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jschulz
 */
public class RsArticle implements Serializable {
    
    private static final long serialVersionUID = -4150137843471878449L;
    
    private Long id;
    private String headline;
    private String shortDescription;
    private String mainText;
    
    private Date publishedOn;
    
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

//    @JsonFormat
    public Date getPublishedOn() {
        return publishedOn;
    }

    public String getShortDescription() {
        return shortDescription;
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

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String toString() {
        return "RsArticle{" + "id=" + id + ", header=" + headline + ", shortDescription=" 
                + shortDescription + ", mainText=" + mainText + ", publishedOn=" + publishedOn 
                + ", authors=" + authors + ", keywords=" + keywords + '}';
    }

}
