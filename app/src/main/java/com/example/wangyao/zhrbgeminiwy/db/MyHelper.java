package com.example.wangyao.zhrbgeminiwy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangyao.zhrbgeminiwy.application.MyApplication;

/**
 * Created by wangyao on 2017/8/11.
 */

public class MyHelper extends SQLiteOpenHelper {
    private static final String CREATE_CONTENT = "create table Content("+"news_id integer,"+"news_body text,"
                                                       +"news_title text,"+"news_images text)";
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Content");
        onCreate(db);

    }
}
