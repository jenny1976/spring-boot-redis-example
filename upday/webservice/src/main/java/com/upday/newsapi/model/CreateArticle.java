package com.upday.newsapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author jschulz
 */
public class CreateArticle implements Serializable {

    private static final long serialVersionUID = -2308642645711065760L;

    @NotBlank
    private String headline;

    @NotBlank
    private String teaserText;

    @NotBlank
    private String mainText;

    @NotNull
    private Date publishedOn;

    private List<Author> authors = new ArrayList<>();;
    private List<Keyword> keywords = new ArrayList<>();;


    public CreateArticle() {
        // default
    }

    public CreateArticle(String headline, String teaserText, String mainText, Date publishedOn) {
        this.headline = headline;
        this.teaserText = teaserText;
        this.mainText = mainText;
        this.publishedOn = publishedOn;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getTeaserText() {
        return teaserText;
    }

    public void setTeaserText(String teaserText) {
        this.teaserText = teaserText;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public LocalDate publishedOnAsLocalDate() {
        return LocalDateTime.ofInstant(this.publishedOn.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "CreateArticle{" + "headline=" + headline + ", teaserText=" + teaserText
                + ", mainText=" + mainText + ", publishedOn=" + publishedOn + ", authors="
                + authors + ", keywords=" + keywords + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.headline);
        hash = 47 * hash + Objects.hashCode(this.teaserText);
        hash = 47 * hash + Objects.hashCode(this.mainText);
        hash = 47 * hash + Objects.hashCode(this.publishedOn);
        hash = 47 * hash + Objects.hashCode(this.authors);
        hash = 47 * hash + Objects.hashCode(this.keywords);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CreateArticle other = (CreateArticle) obj;
        if (!Objects.equals(this.headline, other.headline)) {
            return false;
        }
        if (!Objects.equals(this.teaserText, other.teaserText)) {
            return false;
        }
        if (!Objects.equals(this.mainText, other.mainText)) {
            return false;
        }
        if (!Objects.equals(this.publishedOn, other.publishedOn)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.keywords, other.keywords)) {
            return false;
        }
        return true;
    }


}
