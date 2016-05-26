package com.example.andy.hellobmi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PunchActivity extends AppCompatActivity {
    private TextView punchDateTextView;
    private Button punchNameClearButton;
    private Button punchNowButton;
    private EditText punchNameText;
    private static final String DB_FILE = "punch.db";
    private static final String DB_TABLE = "punch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);

        punchDateTextView = (TextView) findViewById(R.id.punch_Date_TextView);
        punchNameClearButton = (Button) findViewById(R.id.punch_Name_Clear_Button);
        punchNameText = (EditText) findViewById(R.id.punch_Name_Text);
        punchNowButton = (Button) findViewById(R.id.punch_now_button);

        PunchDbOpenHelper punchDbOpenHelper = new PunchDbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
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
                                "name TEXT NOT NULL," +
                                "date TEXT);");
            cursor.close();
        }

        //顯示現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = sdf.format(date);
        punchDateTextView.setText(dataString);

        //姓名輸入清除按鈕
        punchNameClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });

        //我要打卡按鈕
        punchNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues newRow = new ContentValues();
                newRow.put("date", punchDateTextView.getText().toString());
                newRow.put("name", punchNameText.getText().toString());
                long rowInserted = punchdb.insert(DB_TABLE, null, newRow);
                if(rowInserted != -1)
                    Toast.makeText(v.getContext(), "成功新增一筆資料，資料ID: " + rowInserted, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(v.getContext(), "新增資料失敗", Toast.LENGTH_LONG).show();
            }
        });
    }

    //姓名輸入清除
    private void clearText() {
        punchNameText.setText("");
    }


}



