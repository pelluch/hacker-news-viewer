package com.pelluch.hackernewsviewer.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 9/9/17.
 */

public class ArticleResponse {
    @Expose
    @SerializedName("hits")
    @NonNull
    private List<Article> hits = new ArrayList<>();

    public List<Article> getArticles() {
        return hits;
    }
}
