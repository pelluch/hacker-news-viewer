package com.pelluch.hackernewsviewer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pablo on 9/9/17.
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
