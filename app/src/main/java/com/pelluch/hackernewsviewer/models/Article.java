package com.pelluch.hackernewsviewer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.joda.time.DateTime;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pablo on 9/9/17.
 */


@Entity(indexes = {
        @Index(value = "createdAt DESC")
})
public class Article {
    @SerializedName("created_at")
    @Expose
    @Convert(converter = DateTimeConverter.class, columnType = Long.class)
    private DateTime createdAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("story_title")
    @Expose
    private String storyTitle;
    @SerializedName("story_url")
    @Expose
    private String storyUrl;
    @SerializedName("objectID")
    @Expose
    @Id
    private String objectID;
    private boolean deleted;

    @Generated(hash = 4266244)
    public Article(DateTime createdAt, String title, String url, String author,
            String storyTitle, String storyUrl, String objectID, boolean deleted) {
        this.createdAt = createdAt;
        this.title = title;
        this.url = url;
        this.author = author;
        this.storyTitle = storyTitle;
        this.storyUrl = storyUrl;
        this.objectID = objectID;
        this.deleted = deleted;
    }

    @Generated(hash = 742516792)
    public Article() {
    }

    public String getRealTitle() {
        return storyTitle == null ? title : storyTitle;
    }

    public String getRealUrl() {
        return storyUrl == null ? url : storyUrl;
    }

    public DateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStoryTitle() {
        return this.storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryUrl() {
        return this.storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public String getObjectID() {
        return this.objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    static class DateTimeConverter implements PropertyConverter<DateTime, Long> {

        @Override
        public DateTime convertToEntityProperty(Long millis) {
            return new DateTime(millis);
        }

        @Override
        public Long convertToDatabaseValue(DateTime dateTime) {
            return dateTime.getMillis();
        }
    }

}
