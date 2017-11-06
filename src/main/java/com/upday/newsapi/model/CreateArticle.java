package com.upday.newsapi.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



/**
 *
 * @author jschulz
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateArticle {

    @NonNull
    private String headline;
    @NonNull
    private String teaserText;
    @NonNull
    private String mainText;

    private List<Author> authors = new ArrayList<>();;
    private List<Keyword> keywords = new ArrayList<>();;
}
