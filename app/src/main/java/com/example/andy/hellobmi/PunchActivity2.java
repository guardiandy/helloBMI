package com.example.andy.hellobmi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class PunchActivity2 extends AppCompatActivity {
    private TextView punchDateTextView;
    private Button punchOnDutyButton;
    private Button punchHistoryButton;
    private Button punchDropTableButton;
    private Spinner punchSelectSpinner;
    private static final String DB_FILE = "punch.db";
    private static final String DB_TABLE = "punch";
    private ArrayAdapter<CharSequence> punchAdapter;
    private TextView punchHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch2);

        punchDateTextView = (TextView) findViewById(R.id.punch_Date_TextView);
        punchSelectSpinner = (Spinner) findViewById(R.id.punch_Select_Spinner);
        punchOnDutyButton = (Button) findViewById(R.id.punch_on_duty_button);
        punchHistoryButton = (Button) findViewById(R.id.punch_history_button);
        punchDropTableButton = (Button) findViewById(R.id.punch_drop_table_button);
        punchHistoryTextView = (TextView) findViewById(R.id.punch_History_Textview);


        final PunchDbOpenHelper punchDbOpenHelper = new PunchDbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        final SQLiteDatabase punchdb = punchDbOpenHelper.getWritableDatabase();
        //檢查資料表是否已存在，如果不存在，就建立一個
        Cursor cursor = punchdb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                //沒有資料表，要建立一個資料表
                punchdb.execSQL(
                        "CREATE TABLE " + DB_TABLE + " (" +
                                "_id INTEGER PRIMARY KEY," +
                                "date TEXT," +
                                "time TEXT," +
                                "name TEXT NOT NULL," +
                                "type TEXT NOT NULL);");
            cursor.close();
        }

        //顯示現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
        final String dateString = sdf.format(date);
        final String timeString = sdft.format(date);
        punchDateTextView.setText(dateString + " " + timeString);

        //上班打卡按鈕
        punchOnDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                Cursor pcursor = punchdb.rawQuery("SELECT count(*) FROM '" + DB_TABLE + "' where date = '" + dateString + "' AND type = '上班'", null);
                while (pcursor.moveToFirst()) {
                    count = pcursor.getInt(0);
                }
                return count;
//                int cnt = pcursor.getCount();
//                if (cnt > 0)
//                    Toast.makeText(v.getContext(), "今天上班已經打過卡囉", Toast.LENGTH_LONG).show();
//                else {
//                    ContentValues newRow = new ContentValues();
//                    newRow.put("date", dateString);
//                    newRow.put("time", timeString);
//                    String pNameText = "andy";
//                    newRow.put("name", pNameText);
//                    String pType = "andy";
//                    newRow.put("type", pType);
//                    long rowInserted = punchdb.insert(DB_TABLE, null, newRow);
//                    Toast.makeText(v.getContext(), "打卡成功，資料ID: " + rowInserted, Toast.LENGTH_LONG).show();
//                }

            }
        });

        //打卡歷史查詢Button
        punchHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = punchdb.rawQuery("select * from punch", null);
                if (cursor == null)
                    return;
                else {
                    cursor.moveToFirst();
                    punchHistoryTextView.setText(cursor.getString(1) + "  " + cursor.getString(2) + "  " + cursor.getString(3) + "  " + cursor.getString(4));

                }
                while (cursor.moveToNext()) {
                    punchHistoryTextView.append("\n" + cursor.getString(1) + "  " + cursor.getString(2) + "  " + cursor.getString(3) + "  " + cursor.getString(4));
                }
                cursor.close();
            }
        });

        //資料表清除Button
        punchDropTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punchDbOpenHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
                Toast.makeText(v.getContext(), "清除打卡資料成功", Toast.LENGTH_LONG).show();
            }
        });
    }
}
