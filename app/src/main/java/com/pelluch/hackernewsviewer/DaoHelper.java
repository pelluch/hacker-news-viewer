package com.pelluch.hackernewsviewer;

import android.content.Context;

import com.pelluch.hackernewsviewer.models.DaoMaster;
import com.pelluch.hackernewsviewer.models.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by pablo on 9/9/17.
 */

public class DaoHelper {
    private static DaoHelper instance;
    private Database db;
    private DaoSession session;
    private DaoHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "news.db");
        db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        this.session = master.newSession();
    }
    public static DaoHelper getInstance(Context context) {
        if(instance == null) instance = new DaoHelper(context);
        return instance;
    }

    public DaoSession getSession() {
        return session;
    }
}
