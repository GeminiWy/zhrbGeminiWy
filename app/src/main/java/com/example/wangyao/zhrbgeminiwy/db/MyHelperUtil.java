package com.example.wangyao.zhrbgeminiwy.db;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wangyao.zhrbgeminiwy.gson.ArticleContent;
import com.example.wangyao.zhrbgeminiwy.gson.DBContent;

/**
 * Created by wangyao on 2017/8/11.
 */

public class MyHelperUtil {
    private Context context;
    private static final String DbName = "NewsContent.db";
    private static final int VERSION = 4;
    private static MyHelperUtil myHelperUtil;
    private final SQLiteDatabase db;

    private MyHelperUtil(Context context){
        MyHelper myHelper = new MyHelper(context,DbName,null,VERSION);
        db = myHelper.getWritableDatabase();
    };
    public static MyHelperUtil getInstence(Context context){
        if (myHelperUtil == null){
            myHelperUtil = new MyHelperUtil(context);
        }
        return myHelperUtil;
    }

    public void saveContent(ArticleContent articleContent){
        ContentValues values = new ContentValues();
        values.put("news_id",articleContent.getId());
        values.put("news_body",articleContent.getBody());
        values.put("news_title",articleContent.getTitle());
        values.put("news_images",articleContent.getImages().get(0));
        db.insert("Content",null,values);
    }

    public boolean isQueryContent(int id){
        Cursor cursor = db.query("Content",null,"news_id=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor.getCount()!=0)
            return true;
        else
            return false;
    }
    public void queryContent(int id){
        Cursor cursor = db.query("Content",null,"news_id = ?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                DBContent.body = cursor.getString(cursor.getColumnIndex("news_body"));
                DBContent.images = cursor.getString(cursor.getColumnIndex("news_images"));
                DBContent.title = cursor.getString(cursor.getColumnIndex("news_title"));
            }while (cursor.moveToNext());
        }cursor.close();

    }
}
