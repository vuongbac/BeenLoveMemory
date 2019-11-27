package com.example.beenlovememory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.beenlovememory.model.AvatarBoy;

import java.util.ArrayList;
import java.util.List;

public class AvatarBoyDAO {
    public SQLiteDatabase db;
    public static final String TABLE_NAME_AVATAR_BOY = "AVATAR_BOY";
    private static final int COLUMN_ID = 1;
    private static final String COLUMN_IMAGE = "image";
    public static final String SQL_AVATAR_BOY = "CREATE TABLE " + TABLE_NAME_AVATAR_BOY + " (" +COLUMN_IMAGE + " Blob)";
    public static final String TAG = "AVATAR_BOY";

    public AvatarBoyDAO(Context context) {
        DatabaseManager databaseManager = new DatabaseManager(context);
        db = databaseManager.getWritableDatabase();
    }

    public int insertAvatarBoy(AvatarBoy avtBoy) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE,avtBoy.getAvtBoy());
        try {
            if (db.insert(TABLE_NAME_AVATAR_BOY, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateAvatarBoy(AvatarBoy avtBoy) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE,avtBoy.getAvtBoy());
        int result = db.update(TABLE_NAME_AVATAR_BOY, values, COLUMN_IMAGE+"=?", new String[]{String.valueOf(avtBoy.getAvtBoy())});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public List<AvatarBoy> getAllAvatarBoy() {
        List<AvatarBoy> avtBoy = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME_AVATAR_BOY, null, null, null, null, null, null);
        c.moveToFirst();
        if (c!= null && c.getCount()>0)
            while (c.isAfterLast() == false) {
                AvatarBoy ee = new AvatarBoy();
                ee.setAvtBoy(c.getBlob(0));
                avtBoy.add(ee);
                Log.d("//=====", ee.toString());
                c.moveToNext();
            }
        c.close();
        return avtBoy;
    }

    public int deleteDogbyID(String dog) {
        int result = db.delete(TABLE_NAME_AVATAR_BOY, "id=?", new String[]{dog});
        if (result == 0)
            return -1;
        return 1;
    }
}
