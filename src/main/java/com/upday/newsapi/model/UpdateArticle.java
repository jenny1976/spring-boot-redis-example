package com.upday.newsapi.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jschulz
 */
@Data
public class UpdateArticle {

    @NotNull
    private final String headline;
    @NotNull
    private final String teaserText;
    @NotNull
    private final String mainText;
    @NotNull
    private final String publishedOn;

    private List<Author> authors = new ArrayList<>();
    private List<Keyword> keywords = new ArrayList<>();

//    public LocalDate publishedOnAsLocalDate() {
//        return LocalDateTime.ofInstant(this.publishedOn.toInstant(), ZoneId.systemDefault()).toLocalDate();
//    }

}
