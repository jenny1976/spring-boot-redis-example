package com.upday.newsapi.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

/**
 * a REST article representation.
 *
 * @author jschulz
 */
@Data
public class Article {

    @NonNull
    String id;
    @NonNull
    String headline;
    @NonNull
    String teaserText;
    @NonNull
    String mainText;
    @NonNull
    String publishedOn;

    List<Author> authors = new ArrayList<>();
    List<Keyword> keywords = new ArrayList<>();

    public String publishedOnFormatted() {
        return "null";
//        return LocalDateTime.ofInstant(this.publishedOn.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }
}
