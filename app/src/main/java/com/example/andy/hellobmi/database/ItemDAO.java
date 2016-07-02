package com.example.andy.hellobmi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.hellobmi.PunchEditActivity2;

import java.text.SimpleDateFormat;

/**
 * Created by Andy on 2016/6/21.
 */
public class ItemDAO {
    //表格名稱
    public static final String DB_TABLE = "punch";
    //表格欄位名稱
    public static final String KEY_ID = "_ID";
    public static final String DATE_COLUMN = "date";
    public static final String TIME_COLUMN = "time";
    public static final String NAME_COLUMN = "name";
    public static final String TYPE_COLUMN = "type";
    private PunchEditActivity2 punchEditActivity2;

    //現在的日期時間
    long date = System.currentTimeMillis();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
    final String dateString = sdf.format(date);
    final String timeString = sdft.format(date);

    //region 建立表格的SQL指令
    public static final String Create_Table = "CREATE TABLE " + DB_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            DATE_COLUMN + " TEXT, " +
            TIME_COLUMN + " TEXT, " +
            NAME_COLUMN + " TEXT NOT NULL, " +
            TYPE_COLUMN + " TEXT NOT NULL)";
    //endregion

    //資料庫物件
    SQLiteDatabase punchdb;

    //region 建立資料庫
    public ItemDAO(Context context) {
        punchdb = PunchDbOpenHelper.getDatabase(context);
    }
    //endregion

    //region 關閉資料庫
    public void close() {
        punchdb.close();
    }
    //endregion

    //region 建立資料表
    public void createPunchTable() {
        Cursor cursor = punchdb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                punchdb.execSQL(Create_Table);
            cursor.close();
        }
    }
    //endregion

    //region 上班打卡
    public boolean punchOnDuty() {
        //判斷上班是否已經打過卡
        Cursor pcursor = punchdb.rawQuery("SELECT count(*) FROM '" + DB_TABLE + "' where date = '" + dateString + "' AND type = '上班'", null);
        pcursor.moveToFirst();
        int count = pcursor.getInt(0);
        pcursor.close();
        if (count > 0) {
            return true;
        }
        ContentValues newRow = new ContentValues();
        newRow.put("date", dateString);
        newRow.put("time", timeString);
        String pNameText = "andy";
        newRow.put("name", pNameText);
        String pType = "上班";
        newRow.put("type", pType);
        punchdb.insert(DB_TABLE, null, newRow);
        return false;
    }
    //endregion

    //region 下班打卡
    public boolean punchOffDuty() {
        //判斷下班是否已經打過卡
        Cursor pcursor = punchdb.rawQuery("SELECT count(*) FROM '" + DB_TABLE + "' where date = '" + dateString + "' AND type = '下班'", null);
        pcursor.moveToFirst();
        int count = pcursor.getInt(0);
        pcursor.close();
        if (count > 0) {
            return true;
        }
        ContentValues newRow = new ContentValues();
        newRow.put("date", dateString);
        newRow.put("time", timeString);
        String pNameText = "andy";
        newRow.put("name", pNameText);
        String pType = "下班";
        newRow.put("type", pType);
        punchdb.insert(DB_TABLE, null, newRow);
        return false;
    }
    //endregion

    //region 清除打卡資料
    public boolean clearPunchTable() {
        punchdb.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        Cursor cursor = punchdb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            return true;
        } else {
        }
        return false;
    }
    //endregion

    //region 歷史資料查詢
    public String getHistory() {
        Cursor cursor = punchdb.rawQuery("select * from punch", null);
        StringBuilder resultData = new StringBuilder();
        while (cursor.moveToNext()) {
            String date = cursor.getString(1);
            String time = cursor.getString(2);
            String name = cursor.getString(3);
            String type = cursor.getString(4);
            resultData.append(date).append(", ");
            resultData.append(time).append(", ");
            resultData.append(name).append(", ");
            resultData.append(type).append("\n");
        }

        cursor.close();
        return resultData.toString();
    }
    //endregion

    public void punchEdit(){
        ContentValues cv = new ContentValues();
//        PunchEditActivity2 punchEditActivity2 = new PunchEditActivity2();
//        String time = PunchEditActivity2.editPunchTimeFormat();
//        String type = PunchEditActivity2.editPunchTypeFormat();
        cv.put("time", punchEditActivity2.editPunchTimeFormat());
        punchdb.update(DB_TABLE, cv, "date= '" + dateString + "'" + " AND type='" + punchEditActivity2.editPunchTypeFormat() + "'", null);
    }

}
