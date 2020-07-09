package com.example.finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.SQLInput;
import java.util.ArrayList;

public class NewsDB {
    private DBHelper dbHelper;
    private Context ctx;

    public NewsDB(Context ctx){
        this.ctx = ctx;
        dbHelper = new DBHelper(ctx);
    }

    public int getItemCount(){
        String COUNT_QUERY = "SELECT * FROM " + DBHelper.TABLE_NEWS;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(COUNT_QUERY,null);
        return cursor.getCount();
    }

    public boolean isExist(String newsTitle){
        String EXIST_QUERY = "SELECT * FROM " + DBHelper.TABLE_NEWS + " WHERE title = \"" + newsTitle + "\"";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(EXIST_QUERY,null);
        if(cursor.getCount() == 1) return true;
        return false;
    }


    public ArrayList<News> getAllData(){
        ArrayList<News> newsList = new ArrayList<News>();
        String QUERY_GET_ALL_DATA = "SELECT * FROM " + DBHelper.TABLE_NEWS;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL_DATA,null);
        if(cursor.moveToFirst()){
            do{
                News news = new News(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_NEWS_TITLE)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_NEWS_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_NEWS_CONTENT)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_NEWS_URL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_NEWS_IMAGE)));
                newsList.add(news);
            }
            while(cursor.moveToNext());
        }
        return newsList;
    }

    public void insertNews(News news){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_NEWS_TITLE, news.newsTitle);
        cv.put(DBHelper.FIELD_NEWS_DESCRIPTION, news.newsDescription);
        cv.put(DBHelper.FIELD_NEWS_CONTENT, news.newsContent);
        cv.put(DBHelper.FIELD_NEWS_URL, news.newsURL);
        cv.put(DBHelper.FIELD_NEWS_IMAGE, news.thumbnail);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DBHelper.TABLE_NEWS,null,cv);
        db.close();
    }

    public void deleteNews(News news){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NEWS,"title = ?", new String[]{(news.newsTitle)});
        db.close();
    }

}
