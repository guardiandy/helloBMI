package com.example.andy.hellobmi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.andy.hellobmi.PunchActivity3;

import java.text.SimpleDateFormat;

/**
 * Created by Andy on 2016/6/21.
 */
public class ItemDAO {
    //表格名稱
    public static final String DB_TABLE = "punch";
    //表格欄位名稱
    public static final String KEY_ID = "_ID";
    public static final String Date_Column = "date";
    public static final String Time_Column = "time";
    public static final String Name_Column = "name";
    public static final String Type_Column = "type";

    //建立表格的SQL指令
    public static final String Create_Table = "CREATE TABLE " + DB_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            Date_Column + " TEXT, " +
            Time_Column + " TEXT, " +
            Name_Column + " TEXT NOT NULL, " +
            Type_Column + " TEXT NOT NULL)";
    //資料庫物件
    SQLiteDatabase punchdb;

    //建立資料庫
    public ItemDAO(Context context) {
        punchdb = PunchDbOpenHelper.getDatabase(context);
    }

    //關閉資料庫
    public void close() {
        punchdb.close();
    }

    //建立資料表
    public void createPunchTable() {
        Cursor cursor = punchdb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                punchdb.execSQL(Create_Table);
            cursor.close();
        }

    }

    //上班打卡按鈕
    public boolean punchOnDuty() {
        //現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
        final String dateString = sdf.format(date);
        final String timeString = sdft.format(date);

        //帶入PunchActivity3 Class
        PunchActivity3 punchActivity3 = new PunchActivity3();

        //判斷上班是否已經打過卡
        Cursor pcursor = punchdb.rawQuery("SELECT count(*) FROM '" + DB_TABLE + "' where date = '" + dateString + "' AND type = '上班'", null);
        pcursor.moveToFirst();
        int count = pcursor.getInt(0);
        pcursor.close();
        if (count > 0)
            punchActivity3.setSuccess(false);
        else {
            ContentValues newRow = new ContentValues();
            newRow.put("date", dateString);
            newRow.put("time", timeString);
            String pNameText = "andy";
            newRow.put("name", pNameText);
            String pType = "上班";
            newRow.put("type", pType);
            punchdb.insert(DB_TABLE, null, newRow);
        }
        return punchActivity3.isSuccess();
    }

    //下班打卡按鈕
    public boolean punchOffDuty() {
        //現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
        final String dateString = sdf.format(date);
        final String timeString = sdft.format(date);

        //帶入PunchActivity3 Class
        PunchActivity3 punchActivity3 = new PunchActivity3();

        //判斷下班是否已經打過卡
        Cursor pcursor = punchdb.rawQuery("SELECT count(*) FROM '" + DB_TABLE + "' where date = '" + dateString + "' AND type = '下班'", null);
        pcursor.moveToFirst();
        int count = pcursor.getInt(0);
        pcursor.close();
        if (count > 0)
            punchActivity3.setSuccess(false);
        else {
            ContentValues newRow = new ContentValues();
            newRow.put("date", dateString);
            newRow.put("time", timeString);
            String pNameText = "andy";
            newRow.put("name", pNameText);
            String pType = "下班";
            newRow.put("type", pType);
//            punchdb.insert(DB_TABLE, null, newRow);
            punchActivity3.setSuccess(punchdb.insert(DB_TABLE, null, newRow) > 0);
        }

        return punchActivity3.isSuccess();
    }

    public boolean clearPunchTable(){
        punchdb.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        //帶入PunchActivity3 Class
        PunchActivity3 punchActivity3 = new PunchActivity3();

        Cursor cursor = punchdb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor.getCount() > 0)
            punchActivity3.setDropTableSuccess(false);
        else{
        }
        return  punchActivity3.isTableDropSuccess();
    }




}
