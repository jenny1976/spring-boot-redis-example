package com.upday.newsapi.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


/**
 * @author jschulz
 */
@Data
@AllArgsConstructor
public class UpdateArticle {

    @NonNull
    private String headline;
    @NonNull
    private String teaserText;
    @NonNull
    private String mainText;
    private String publishedOn;

    private List<Author> authors = new ArrayList<>();
    private List<Keyword> keywords = new ArrayList<>();

//    public LocalDate publishedOnAsLocalDate() {
//        return LocalDateTime.ofInstant(this.publishedOn.toInstant(), ZoneId.systemDefault()).toLocalDate();
//    }

}
