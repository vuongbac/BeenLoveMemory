package com.example.beenlovememory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.beenlovememory.model.AvatarGirl;

import java.util.ArrayList;
import java.util.List;

public class AvatarGirlDAO {
    public SQLiteDatabase db;
    public static final String TABLE_NAME_AVATAR_GIRL = "AVATAR_GIRL";
    private static final int COLUMN_ID = 1;
    private static final String COLUMN_IMAGE = "image";
    public static final String SQL_AVATAR_GIRL = "CREATE TABLE " + TABLE_NAME_AVATAR_GIRL + " (" +COLUMN_IMAGE + " Blob)";
    public static final String TAG = "AVATAR_GIRL";

    public AvatarGirlDAO(Context context) {
        DatabaseManager databaseManager = new DatabaseManager(context);
        db = databaseManager.getWritableDatabase();
    }

    public int insertAvatarGIRL(AvatarGirl avtGirl) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE,avtGirl.getAvt_girl());
        try {
            if (db.insert(TABLE_NAME_AVATAR_GIRL, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateAvatarGirl(AvatarGirl avtGirl) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE,avtGirl.getAvt_girl());
        int result = db.update(TABLE_NAME_AVATAR_GIRL, values, COLUMN_IMAGE+"=?", new String[]{String.valueOf(avtGirl.getAvt_girl())});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public List<AvatarGirl> getAllAvatarGirl() {
        List<AvatarGirl> avtGirl = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME_AVATAR_GIRL, null, null, null, null, null, null);
        c.moveToFirst();
        if (c!= null && c.getCount()>0)
            while (c.isAfterLast() == false) {
                AvatarGirl ee = new AvatarGirl();
                ee.setAvt_girl(c.getBlob(0));
                avtGirl.add(ee);
                Log.d("//=====", ee.toString());
                c.moveToNext();
            }
        c.close();
        return avtGirl;
    }

    public int deleteDogbyID(String dog) {
        int result = db.delete(TABLE_NAME_AVATAR_GIRL, "id=?", new String[]{dog});
        if (result == 0)
            return -1;
        return 1;
    }
}
