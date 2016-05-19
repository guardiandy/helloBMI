package com.example.andy.hellobmi.dbframework;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andy.hellobmi.database.PunchTable;



public class MyDbOpenHelper extends SQLiteOpenHelper {
    //資料庫名稱
//    private static final String DB_NAME = "helloBMI.db";
    //資料庫版本，資料結構改變時要更改這個數字，通常是加一
    public static final int VERSION = 1;
    //資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;

    public MyDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        if (database == null || !database.isOpen()) {
            database = getWritableDatabase();
        }
    }

    public static SQLiteDatabase getDatabase(){
        return database;
    }



    //需要資料庫的元件呼叫這個方法，在一般的應用都不需要修改
//    public static SQLiteDatabase getDatabase(Context context){
//        if (database == null || !database.isOpen()){
//            database = new MyDbOpenHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
//        }
//        return database;
//    }

    //建立表格
    @Override
    public void onCreate(SQLiteDatabase db) {
       database = db;
        new PunchTable(db, "打卡資料");
    }

    //刪除表格
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        database = db;
//        PunchTable punchTable = new PunchTable(db, "打卡資料");
//        punchTable.dropTable();
//        punchTable.CreateTable();
    }
}
