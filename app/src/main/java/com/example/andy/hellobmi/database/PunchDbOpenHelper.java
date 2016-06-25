package com.example.andy.hellobmi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


public class PunchDbOpenHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase punchdb;
    //定義資料表名稱
    private static final String DB_TABLE = "punch";
    //定義資料庫名稱
    private static final String DB_FILE = "punch.db";

    public PunchDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //產生資料庫元件
    public static SQLiteDatabase getDatabase(Context context){
        if (punchdb == null || !punchdb.isOpen()){
            punchdb = new PunchDbOpenHelper(context, DB_FILE, null, 1).getWritableDatabase();
        }
        return punchdb;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

