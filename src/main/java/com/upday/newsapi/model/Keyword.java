package com.upday.newsapi.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author jschulz
 */
public class Keyword implements Serializable {

    private static final long serialVersionUID = -8346511848327080751L;

    private String name;

    public Keyword() {
        // default constructor
    }

    public Keyword(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RsKeyword{ name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Keyword other = (Keyword) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
