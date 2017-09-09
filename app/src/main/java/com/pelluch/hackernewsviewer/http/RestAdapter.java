package com.pelluch.hackernewsviewer.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pelluch.hackernewsviewer.models.Article;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pablo on 9/9/17.
 */

public final class RestAdapter {
    private static final String API_URL =
            "https://hn.algolia.com";

    private static RestAdapter instance;
    private ApiEndpoint endpoint;

    private RestAdapter() {

        GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Type articlesType = new TypeToken<List<Article>>() {}.getType();
        builder.registerTypeAdapter(articlesType, new ApiAdapter<List<Article>>());
        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endpoint = retrofit.create(ApiEndpoint.class);
    }

    public static ApiEndpoint getArticlesEndpoint() {
        if(instance == null) instance = new RestAdapter();
        return instance.endpoint;
    }
}
