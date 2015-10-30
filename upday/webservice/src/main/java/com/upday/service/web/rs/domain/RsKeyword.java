package com.upday.service.web.rs.domain;

import java.io.Serializable;

/**
 *
 * @author jschulz
 */
public class RsKeyword implements Serializable {
    
    private static final long serialVersionUID = -8346511848327080751L;
    
    private Long id;
    private String name;

    public RsKeyword() {
        // default constructor
    }

    public RsKeyword(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RsKeyword{" + "id=" + id + ", name=" + name + '}';
    }
}
