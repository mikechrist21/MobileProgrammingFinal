package com.example.finalexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "saveNewsDB";
    private static final int DB_VERSION = 1;

    //title, image, content, newsURL
    public static final String TABLE_NEWS = "news";
    public static final String FIELD_NEWS_ID = "id";
    public static final String FIELD_NEWS_TITLE = "title";
    public static final String FIELD_NEWS_IMAGE = "image";
    public static final String FIELD_NEWS_CONTENT = "content";
    public static final String FIELD_NEWS_DESCRIPTION = "description";
    public static final String FIELD_NEWS_URL = "url";

    //Create Table
    private static final String CREATE_TABLE_NEWS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NEWS + "(" +
            FIELD_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FIELD_NEWS_TITLE + " TEXT, " +
            FIELD_NEWS_IMAGE + " TEXT, " +
            FIELD_NEWS_CONTENT + " TEXT, " +
            FIELD_NEWS_DESCRIPTION + " TEXT, " +
            FIELD_NEWS_URL + " TEXT);";

    private static final String DROP_TABLE_NEWS = "DROP TABLE IF EXISTS " + TABLE_NEWS + ";";

    public DBHelper(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_NEWS);
        onCreate(db);
    }
}
