package com.upday.service.persistence.domain;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author jschulz
 */
@Entity
@Table(name = "NEWS_ARTICLE")
public class Article extends AbstractPersistable<Long> {
    
    private static final long serialVersionUID = 1651369642672635031L;
   
    @Column(name = "HEADLINE")
    private String headline;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "TEXT")
    private String mainText;
    
    @Column(name = "CREATED_ON")
    private Date createdOn;
    
    @Column(name = "UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "PUBLISHED_ON")
    private Date publishedOn;
    
    @ManyToMany(
            fetch = FetchType.EAGER,
            targetEntity = Author.class
    )
    @JoinTable(
      name="NEWS_ARTICLE_AUTHOR",
      joinColumns={
          @JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")
      },
      inverseJoinColumns={
          @JoinColumn(name="ARTICLE_ID", referencedColumnName="ID")
      }
    )
    private List<Author> authors;
    
    @ManyToMany(
            fetch = FetchType.EAGER,
            targetEntity = Keyword.class
    )
    @JoinTable(
      name="NEWS_ARTICLE_KEYWORD",
      joinColumns={
          @JoinColumn(name="KEYWORD_ID", referencedColumnName="ID")
      },
      inverseJoinColumns={
          @JoinColumn(name="ARTICLE_ID", referencedColumnName="ID")
      }
    )
    private List<Keyword> keywords;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.headline);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.mainText);
        hash = 71 * hash + Objects.hashCode(this.createdOn);
        hash = 71 * hash + Objects.hashCode(this.updatedOn);
        hash = 71 * hash + Objects.hashCode(this.publishedOn);
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
        if (!Objects.equals(this.headline, other.headline)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.mainText, other.mainText)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.updatedOn, other.updatedOn)) {
            return false;
        }
        if (!Objects.equals(this.publishedOn, other.publishedOn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article{" + "headline=" + headline + ", description=" + description 
                + ", mainText=" + mainText + ", createdOn=" + createdOn 
                + ", updatedOn=" + updatedOn + ", publishedOn=" + publishedOn 
                + ", authors=" + authors + ", keywords=" + keywords + '}';
    }
  
}
