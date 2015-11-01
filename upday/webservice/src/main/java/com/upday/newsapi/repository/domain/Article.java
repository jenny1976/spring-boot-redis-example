package com.upday.newsapi.repository.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Entity
@Table(name = "NEWS_ARTICLE")
public class Article implements Persistable<Long> {
    
    private static final long serialVersionUID = 1651369642672635031L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "HEADLINE")
    private String headline;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "TEXT")
    private String mainText;
    
    @Column(name = "CREATED_ON")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime createdOn;
    
    @Column(name = "UPDATED_ON")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime updatedOn;
    
    @Column(name = "PUBLISHED_ON")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
    private LocalDate publishedOn;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name="NEWS_ARTICLE_AUTHOR",
      joinColumns={
          @JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")
      },
      inverseJoinColumns={
          @JoinColumn(name="ARTICLE_ID", referencedColumnName="ID")
      }
    )
    private Set<Author> authors;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name="NEWS_ARTICLE_KEYWORD",
      joinColumns={
          @JoinColumn(name="KEYWORD_ID", referencedColumnName="ID")
      },
      inverseJoinColumns={
          @JoinColumn(name="ARTICLE_ID", referencedColumnName="ID")
      }
    )
    private Set<Keyword> keywords;

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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    @Transient
    public boolean isNew() {
            return null == getId();
    }
    
    /*
     * convenience methods.
     */
    public void addKeyword(final Keyword keyword) {
        if(CollectionUtils.isEmpty(keywords)) {
            keywords = new HashSet<>();
        }
        keywords.add(keyword);
    }
    public void addAuthor(final Author author) {
        if(CollectionUtils.isEmpty(authors)) {
            authors = new HashSet<>();
        }
        authors.add(author);
    }
    /**/

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.headline);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.mainText);
        hash = 17 * hash + Objects.hashCode(this.createdOn);
        hash = 17 * hash + Objects.hashCode(this.updatedOn);
        hash = 17 * hash + Objects.hashCode(this.publishedOn);
        hash = 17 * hash + Objects.hashCode(this.authors);
        hash = 17 * hash + Objects.hashCode(this.keywords);
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
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.keywords, other.keywords)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", headline=" + headline + ", description=" 
                + description + ", mainText=" + mainText + ", createdOn=" + createdOn 
                + ", updatedOn=" + updatedOn + ", publishedOn=" + publishedOn + ", authors=" 
                + authors + ", keywords=" + keywords + '}';
    }
  
}
