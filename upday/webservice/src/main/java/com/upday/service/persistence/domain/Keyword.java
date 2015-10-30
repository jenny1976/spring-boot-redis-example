package com.upday.service.persistence.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jschulz
 */
@Entity
@Table(name = "NEWS_KEYWORD")
public class Keyword implements Serializable {
    
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
