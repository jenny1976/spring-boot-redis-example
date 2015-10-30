package com.upday.service.persistence.domain;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author jschulz
 */
@Entity
@Table(name = "NEWS_ARTICLE")
//@NamedQuery(name = "article.findByKeyword", query = "")
public class Article extends AbstractPersistable<Long>{
    
    private static final long serialVersionUID = 1651369642672635031L;
   
    @Column
    private String headline;
    
    @Column
    private String description;
    
    @Column(name = "maintext")
    private String mainText;
    
    /*
    private Date createdOn;
    private Date updatedOn;
*/
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
/*
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
  */  
}
