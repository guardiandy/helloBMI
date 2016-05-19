package com.example.andy.hellobmi;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBItem {
    //表格名稱
    public static final String TABLE_NAME = "item";
    //編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    //其他表格欄位名稱
    public static final String DATETIME_COLUMN = "datetime";
    public static final String COLOR_COLUMN = "color";
    //資料庫物件
    private SQLiteDatabase db;

    //建構子，在一般的應用都不需要修改
    public DBItem(Context context) {
//        db = MyDbOpenHelper.getDatabase(context);
    }

    //關閉資料庫，在一般的應用都不需要修改
    public void close() {
        db.close();
    }

    //新增參數指定的物件
//    public Item insert(Item item) {
        //建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        //加入ContentValues物件包裝的新增資料
        //第一個參數是欄位名稱，第二個參數是欄位的資料
//        cv.put(DATETIME_COLUMN, item.getDatetime());

        //新增一筆資料並取得編號
        //第一個參數是表格名稱
        //第二個參數是沒有指定欄位值的預設值
        //第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        //設定編號
//        item.setId(id);
        //回傳結果
//        return item;
    }


