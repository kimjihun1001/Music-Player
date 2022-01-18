package com.jihoon.musicplayer.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jihoon.musicplayer.Const.Const;
import com.jihoon.musicplayer.Const.MusicPlayerDBContract;
import com.jihoon.musicplayer.Model.ModelMusic;

public class MusicPlayerDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DBFILE_CONTACT = "musicPlayer.db";

    public MusicPlayerDBHelper(Context context) {
        super(context, DBFILE_CONTACT, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MusicPlayerDBContract.SQL_CREATE_TBL_ALLMUSIC);
        db.execSQL(MusicPlayerDBContract.SQL_CREATE_TBL_PLAYLIST);
        db.execSQL(MusicPlayerDBContract.SQL_CREATE_TBL_MUSICOFPLAYLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
