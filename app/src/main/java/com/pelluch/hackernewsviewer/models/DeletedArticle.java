package com.pelluch.hackernewsviewer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * A separate entity is used to store deleted article ids
 * This is mostly due to the fact that we clear the article table
 * on each successful load, and also allows us to store less data
 * as only id is needed here
 */
@Entity
public class DeletedArticle {
    @Id
    private String objectID;

    @Generated(hash = 64494860)
    public DeletedArticle(String objectID) {
        this.objectID = objectID;
    }

    @Generated(hash = 743249589)
    public DeletedArticle() {
    }

    public String getObjectID() {
        return this.objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    @Override
    public String toString() {
        return objectID;
    }
}
