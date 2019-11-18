package com.example.beenlovememory.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class DatabaseImg extends SQLiteOpenHelper {
    public DatabaseImg(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public void INSERT_ANH(byte[] anh){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql="INSERT INTO AnhNam VALUES(null,?)";
        SQLiteStatement statement=sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1,anh);
        statement.executeInsert();
    }

    public void INSERT_ANH1(byte[] anh){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql="INSERT INTO AnhNu VALUES(null,?)";
        SQLiteStatement statement=sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1,anh);
        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
