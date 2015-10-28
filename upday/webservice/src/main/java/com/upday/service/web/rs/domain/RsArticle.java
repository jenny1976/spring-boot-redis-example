package com.upday.service.web.rs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jschulz
 */
//@XmlRootElement
public class RsArticle implements Serializable {
    
    private static final long serialVersionUID = -4150137843471878449L;
    
    private Long id;
    private String header;
    private String shortDescription;
    private String mainText;
    
    private Date publishedOn;
    
    private List<RsAuthor> authors;
    private List<RsKeyword> keywords;

    public List<RsAuthor> getAuthors() {
        return authors;
    }

    public String getHeader() {
        return header;
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

    public Date getPublishedOn() {
        return publishedOn;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setAuthors(List<RsAuthor> authors) {
        this.authors = authors;
    }

    public void setHeader(String header) {
        this.header = header;
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
        return super.toString();
    }
}
