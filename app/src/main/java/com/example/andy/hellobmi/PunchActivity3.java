package com.example.andy.hellobmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.hellobmi.database.ItemDAO;
import com.example.andy.hellobmi.database.PunchDbOpenHelper;

import java.text.SimpleDateFormat;

public class PunchActivity3 extends AppCompatActivity {
    private TextView punchDateTextView;
    private Button punchOnDutyButton;
    private Button punchOffDutyButton;
    private Button punchHistoryButton;
    private Button punchDropTableButton;
    private Button punchEditButton;
    private TextView punchHistoryTextView;
    private ItemDAO itemDAO;
    private boolean success;
    private long rowInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch3);

        punchDateTextView = (TextView) findViewById(R.id.punch_Date_TextView);
        punchOnDutyButton = (Button) findViewById(R.id.punch_on_duty_button);
        punchOffDutyButton = (Button) findViewById(R.id.punch_off_duty_button);
        punchHistoryButton = (Button) findViewById(R.id.punch_history_button);
        punchDropTableButton = (Button) findViewById(R.id.punch_drop_table_button);
        punchEditButton = (Button) findViewById(R.id.punch_edit_button);
        punchHistoryTextView = (TextView) findViewById(R.id.punch_History_Textview);


        //建立資料庫
        itemDAO = new ItemDAO(getApplicationContext());

        //建立資料表
        itemDAO.createPunchTable();


        //顯示現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
        final String dateString = sdf.format(date);
        final String timeString = sdft.format(date);
        punchDateTextView.setText(dateString + " " + timeString);

        //上班打卡按鈕
        punchOnDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDAO.punchOnDuty();
                if (isSuccess()){
                    Toast.makeText(v.getContext(), "新增資料成功", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(v.getContext(), "你今天已經打過卡囉", Toast.LENGTH_LONG).show();
                }
            }
        });

        //下班打卡按鈕
        punchOffDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDAO.punchOffDuty();
                if (isSuccess()){
                    Toast.makeText(v.getContext(), "新增資料成功", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(v.getContext(), "你今天已經打過卡囉", Toast.LENGTH_LONG).show();
                }
            }
        });

        //資料表清除按鈕
        punchDropTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDAO.clearPunchTable();
                if(isTableDropSuccess()){
                    Toast.makeText(v.getContext(), "刪除資料庫成功", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(v.getContext(), "刪除資料庫失敗", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    //接收DAO回傳值
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setDropTableSuccess(boolean success){
        this.success = success;
    }

    public boolean isTableDropSuccess(){
        return success;
    }



}







