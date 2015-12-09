package com.upday.newsapi.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * a REST article representation.
 *
 * @author jschulz
 */
public class Article implements Serializable {

    private static final long serialVersionUID = -4150137843471878449L;

    private String id;
    private String headline;
    private String teaserText;
    private String mainText;

    private Date publishedOn;

    private List<Author> authors = new ArrayList<>();
    private List<Keyword> keywords = new ArrayList<>();

    public Article() {
    }

    public Article(String id, String headline, String teaserText, String mainText, Date publishedOn) {
        this.id = id;
        this.headline = headline;
        this.teaserText = teaserText;
        this.mainText = mainText;
        this.publishedOn = publishedOn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getHeadline() {
        return headline;
    }

    public String getId() {
        return id;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public String getMainText() {
        return mainText;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public String getTeaserText() {
        return teaserText;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public LocalDate publishedOnAsLocalDate() {
        return LocalDateTime.ofInstant(this.publishedOn.toInstant(), ZoneId.systemDefault()).toLocalDate();
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.headline);
        hash = 71 * hash + Objects.hashCode(this.teaserText);
        hash = 71 * hash + Objects.hashCode(this.mainText);
        hash = 71 * hash + Objects.hashCode(this.publishedOn);
        hash = 71 * hash + Objects.hashCode(this.authors);
        hash = 71 * hash + Objects.hashCode(this.keywords);
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
        final Article other = (Article) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
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
