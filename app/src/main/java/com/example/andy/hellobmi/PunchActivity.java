package com.example.andy.hellobmi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.hellobmi.database.PunchDbOpenHelper;

import java.text.SimpleDateFormat;

public class PunchActivity extends AppCompatActivity {
    private TextView punchDateTextView;
    private Button punchNameClearButton;
    private Button punchNowButton;
    private Button punchHistoryButton;
    private EditText punchNameText;
    private Spinner punchSelectSpinner;
    private static final String DB_FILE = "punch.db";
    private static final String DB_TABLE = "punch";
    private ArrayAdapter<CharSequence> punchAdapter;
    private String[] punchSelection = {"上班", "下班"};
    private TextView punchHistoryTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);

        punchDateTextView = (TextView) findViewById(R.id.punch_Date_TextView);
        punchNameClearButton = (Button) findViewById(R.id.punch_Name_Clear_Button);
        punchNameText = (EditText) findViewById(R.id.punch_Name_Text);
        punchSelectSpinner = (Spinner) findViewById(R.id.punch_Select_Spinner);
        punchNowButton = (Button) findViewById(R.id.punch_on_duty_button);
        punchHistoryButton = (Button) findViewById(R.id.punch_history_button);
        punchHistoryTextView = (TextView) findViewById(R.id.punch_History_Textview);
        //使用String陣列
        //punchAdapter = new ArrayAdapter(this, R.layout.punch_spinner_item, punchSelection);
        //使用values.arrays.xml
        punchAdapter = ArrayAdapter.createFromResource(this, R.array.punchSelections, R.layout.punch_spinner_item);


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
                                "date TEXT," +
                                "name TEXT NOT NULL," +
                                "type TEXT NOT NULL);");
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

        //上下班選擇Spinner
        punchSelectSpinner.setAdapter(punchAdapter);

        //我要打卡按鈕
        punchNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues newRow = new ContentValues();
                newRow.put("date", punchDateTextView.getText().toString());
                String pNameText = punchNameText.getText().toString();
                //判斷是否有輸入姓名
                if(pNameText.equals(""))
                    newRow.putNull("name");
                else
                    newRow.put("name", pNameText);
                newRow.put("type", punchSelectSpinner.getSelectedItem().toString());
                long rowInserted = punchdb.insert(DB_TABLE, null, newRow);
                if (pNameText.equals("") && rowInserted == -1)
                    Toast.makeText(v.getContext(), "您尚未輸入姓名", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(v.getContext(), "成功新增一筆資料，資料ID: " + rowInserted, Toast.LENGTH_LONG).show();
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
                    punchHistoryTextView.setText(cursor.getString(1) + "  " + cursor.getString(2) + "  " + cursor.getString(3));

                }
                while (cursor.moveToNext()){
                    punchHistoryTextView.append("\n" + cursor.getString(1) + "  " + cursor.getString(2) + "  " + cursor.getString(3));
                }
                cursor.close();
            }
        });
    }

    //姓名輸入清除
    private void clearText() {
        punchNameText.setText("");
    }



}



