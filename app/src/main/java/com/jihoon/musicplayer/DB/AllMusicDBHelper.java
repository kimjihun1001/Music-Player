package com.jihoon.musicplayer.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jihoon.musicplayer.Model.ModelMusic;

public class AllMusicDBHelper extends SQLiteOpenHelper {
    public AllMusicDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists allMusic ("
                + "id integer primary key autoincrement,"
                + "title text);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists mytable";

        db.execSQL(sql);
        onCreate(db);
    }

    public void InsertDB(SQLiteDatabase db, ModelMusic) {
        ContentValues values = new ContentValues();
        values.put("title", "Lucky you");
        db.insert("allMusic", null, values);
    }

    public void DeleteDB(SQLiteDatabase db) {
        db.delete("allMusic", "txt")
    }
}
