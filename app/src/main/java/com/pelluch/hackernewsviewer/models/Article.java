package com.pelluch.hackernewsviewer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by pablo on 9/9/17.
 */

public class Article {
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("story_id")
    @Expose
    public Integer storyId;
    @SerializedName("story_title")
    @Expose
    private String storyTitle;
    @SerializedName("story_url")
    @Expose
    private String storyUrl;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("created_at_i")
    @Expose
    private int createdAtI;
    @SerializedName("objectID")
    @Expose
    private String objectID;

    private boolean deleted;
    
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title != null ? title : storyTitle;
    }

    public String getUrl() {
        return storyUrl != null ? storyUrl : url;
    }

    public int getCreatedAtI() {
        return createdAtI;
    }
}
