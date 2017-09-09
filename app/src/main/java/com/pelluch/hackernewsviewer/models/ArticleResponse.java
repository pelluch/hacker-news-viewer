package com.pelluch.hackernewsviewer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pablo on 9/9/17.
 */

public class ArticleResponse {
    @Expose
    @SerializedName("hits")
    private List<Article> hits;

    public List<Article> getArticles() {
        return hits;
    }
}
