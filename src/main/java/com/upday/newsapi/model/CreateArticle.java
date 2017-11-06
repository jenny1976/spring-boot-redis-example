package com.upday.newsapi.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author jschulz
 */
@Data
public class CreateArticle {

    @NotNull
    private final String headline;
    @NotNull
    private final String teaserText;
    @NotNull
    private final String mainText;

    private List<Author> authors = new ArrayList<>();;
    private List<Keyword> keywords = new ArrayList<>();;
}
