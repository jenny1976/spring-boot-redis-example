package com.upday.newsapi.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author jschulz
 */
@Data
public class Author {

    @NonNull
    private String firstname;
    @NonNull
    private String lastname;

}
