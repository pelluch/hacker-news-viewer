package com.pelluch.hackernewsviewer.http;

import com.pelluch.hackernewsviewer.models.Article;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by pablo on 9/9/17.
 */

public interface ApiEndpoint {
    @GET
    List<Article> getArticles();
}
