package com.upday.newsapi.service;

/**
 * @author Jennifer Schulz, jennykroete.gmx.de
 */
abstract class KeyUtils {

    static final String AID = "aid:";
    static final String AuID = "auid:";
    static final String KID = "kid:";


    static String articleId(String articleId) {
        return AID + articleId;
    }

    static String keywords(String articleId) {
        return AID + articleId + ":keywords";
    }

    static String authors(String articleId) {
        return AID + articleId + ":authors";
    }

    static String keyword(String articleId, String kid) {
        return AID + articleId + ":keyword:"+kid;
    }

    static String firstname(String articleId, String auId) {
        return AID + articleId + ":author:"+auId+":firstname";
    }

    static String lastname(String articleId, String auId) {
        return AID + articleId + ":author:"+auId+":lastname";
    }

    static String globalArticleId() {
        return "global:aid";
    }

    static String globalAuthorId() {
        return "global:auid";
    }

    static String globalKeywordId() {
        return "global:kid";
    }

}
