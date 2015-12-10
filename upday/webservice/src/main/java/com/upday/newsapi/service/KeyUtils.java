package com.upday.newsapi.service;

/**
 * @author Jennifer Schulz, jennykroete.gmx.de
 */
abstract class KeyUtils {

    static final String AID = "aid:";

    static String keywords(String articleId) {
        return AID + articleId + ":keywords";
    }

    static String authors(String articleId) {
        return AID + articleId + ":authors";
    }

    static String articleId(String articleId) {
        return AID + articleId;
    }

    static String globalAid() {
        return "global:aid";
    }

}
