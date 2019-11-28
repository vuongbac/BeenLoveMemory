package com.example.beenlovememory.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.beenlovememory.model.AvatarBoy;


public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "love";
    public static final int VERSION = 3;


    public DatabaseManager( Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.SQL_USER);
        db.execSQL(AvatarBoyDAO.SQL_AVATAR_BOY);
        db.execSQL(AvatarGirlDAO.SQL_AVATAR_GIRL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + UserDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + AvatarBoyDAO.TABLE_NAME_AVATAR_BOY);
        db.execSQL("Drop table if exists " + AvatarGirlDAO.TABLE_NAME_AVATAR_GIRL);
        onCreate(db);

    }
}
