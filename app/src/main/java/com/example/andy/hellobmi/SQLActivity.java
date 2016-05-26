package com.example.andy.hellobmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andy.hellobmi.database.Punch;
import com.example.andy.hellobmi.database.PunchTable;

public class SQLActivity extends AppCompatActivity {

    private Button SQLCreateButton;
    private Button SQLDeleteButton;
    private Button SQLInsertButton;
    private Button SQLQueryButton;
    private Button SQLCreateTableButton;
    private Button SQLDeleteTableButton;
    private Button SQLClearButton;
    private EditText 日期;
    private EditText 姓名;
//    private MyDbOpenHelper dbHelper;
    private Punch punch;
    private PunchTable punchTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        SQLCreateButton = (Button) findViewById(R.id.SQL_create_button);
        SQLDeleteButton = (Button) findViewById(R.id.SQL_delete_button);
        SQLInsertButton = (Button) findViewById(R.id.SQL_insert_button);
        SQLQueryButton = (Button) findViewById(R.id.SQL_query_button);
        SQLCreateTableButton = (Button) findViewById(R.id.SQL_createtable_button);
        SQLDeleteTableButton = (Button) findViewById(R.id.SQL_deletetable_button);
        SQLClearButton = (Button) findViewById(R.id.SQL_clear_button);
        日期 = (EditText) findViewById(R.id.日期);
        姓名 = (EditText) findViewById(R.id.姓名);
//        dbHelper = new MyDbOpenHelper(this, "打卡資料庫.db", null, MyDbOpenHelper.VERSION);
//        punchTable = new PunchTable(dbHelper.getDatabase(), "打卡資料");

        //資料庫產生按鈕
        SQLCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //創建一個DatabaseHelper對象
//                MyDbOpenHelper mHelper = new MyDbOpenHelper(SQLActivity.this);
                //調用DatabaseHelper對象的getReadableDatabase方法或是getWritableDatabase方法
//                SQLiteDatabase db = mHelper.getReadableDatabase();
                //呼叫dbHelper的getDatabase method
//                dbHelper.getDatabase();
                //當使用者按下按鈕時顯示Toast
                //Toast.LENGTH_LONG表示顯示時間較長，Toast.LENGTH_SHORT則表示顯示時間較短
                Toast.makeText(v.getContext(), "SQL資料庫已建立", Toast.LENGTH_LONG).show();
            }
        });
        //資料庫刪除按鈕
        SQLDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean drop;
                drop = deleteDatabase("打卡資料庫.db");
                if (drop) {
                    Toast.makeText(v.getContext(), "刪除資料庫成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "刪除資料庫失敗", Toast.LENGTH_LONG).show();
                }
            }
        });
        //資料表產生按鈕
        SQLCreateTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (punchTable.isTableExists()) {
                    Toast.makeText(v.getContext(), "資料表已經存在", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean ok = punchTable.CreateTable();
                if (ok) {
                    Toast.makeText(v.getContext(), "建立資料表成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "建立資料表失敗", Toast.LENGTH_LONG).show();
                }
            }
        });
        //資料表刪除按鈕
        SQLDeleteTableButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        punchTable.dropTable();

                    }
                }
        );

        //資料庫Insert按鈕
        SQLInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punch = new Punch(punchTable);
                punch.日期(日期.getText().toString());
                punch.姓名(姓名.getText().toString());
                punchTable.insert(punch);
                if (punch.isSuccess()) {
                    Toast.makeText(v.getContext(), "新增成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "新增失敗", Toast.LENGTH_LONG).show();
                }


                //創建一個DatabaseHelper對象
//                MyDbOpenHelper mHelper = new MyDbOpenHelper(SQLActivity.this);
                //調用DatabaseHelper對象的getReadableDatabase方法或是getWritableDatabase方法
//                SQLiteDatabase db = mHelper.getWritableDatabase();
//                db.execSQL("INSERT INTO MyTable (_DATA,_DATETIME)VALUES('Hello World', datetime('now'))");
//                db.close();
            }
        });

        //資料庫Query按鈕
        SQLQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //創建一個DatabaseHelper對象
//                MyDbOpenHelper mHelper = new MyDbOpenHelper(SQLActivity.this);
                //調用DatabaseHelper對象的getReadableDatabase方法或是getWritableDatabase方法
//                SQLiteDatabase db = mHelper.getWritableDatabase();
//                Cursor cursor = db.rawQuery("SELECT _DATA, _DATETIME FROM MyTable", null);
//                cursor.moveToFirst();
//                Toast.makeText(v.getContext(), cursor.toString(), Toast.LENGTH_LONG).show();
//                cursor.close();
//                db.close();
            }
        });

        //輸入清空按鈕
        SQLClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });
    }

    private void clearText() {
        日期.setText("");
        姓名.setText("");
    }
}

