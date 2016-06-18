package com.example.andy.hellobmi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class PunchEdit extends AppCompatActivity {
    private TextView punchEditCurrentDate;
    private NumberPicker punchHourNumberpicker;
    private NumberPicker punchMinuteNumberpicker;
    private Spinner punchEditTypeSpinner;
    private ArrayAdapter<CharSequence> punchAdapter;
    private Button punchEditButton;
    private static final String DB_FILE = "punch.db";
    private static final String DB_TABLE = "punch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_edit);

        punchEditCurrentDate = (TextView) findViewById(R.id.punch_edit_current_date);
        punchHourNumberpicker = (NumberPicker) findViewById(R.id.punch_hour_numberpicker);
        punchMinuteNumberpicker = (NumberPicker) findViewById(R.id.punch_minute_numberpicker);
        punchEditTypeSpinner = (Spinner) findViewById(R.id.punch_edit_type_spinner);
        punchEditButton = (Button) findViewById(R.id.punch_edit_button);

        PunchDbOpenHelper punchDbOpenHelper = new PunchDbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        final SQLiteDatabase punchdb = punchDbOpenHelper.getWritableDatabase();

        //設定numberpicker
        punchHourNumberpicker.setMinValue(01);
        punchHourNumberpicker.setMaxValue(12);
        punchHourNumberpicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        punchMinuteNumberpicker.setMinValue(00);
        punchMinuteNumberpicker.setMaxValue(59);
        punchMinuteNumberpicker.setFormatter(new Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });


        //PunchEditType使用values.arrays.xml
        punchAdapter = ArrayAdapter.createFromResource(this, R.array.punchSelections, R.layout.punch_spinner_item);

        //上下班選擇Spinner
        punchEditTypeSpinner.setAdapter(punchAdapter);

        //顯示現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String dateString = sdf.format(date);
        punchEditCurrentDate.setText(dateString);


        //打卡修改送出按鈕
        punchEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = punchHourNumberpicker.getValue();
                int minute = punchMinuteNumberpicker.getValue();
                String sHour = String.format("%02d", hour);
                String sMin = String.format("%02d", minute);
                final String sTime = sHour + ":" + sMin;
                final String sType = punchEditTypeSpinner.getSelectedItem().toString();
                ContentValues cv = new ContentValues();
                cv.put("time", sTime);
                punchdb.update(DB_TABLE, cv, "date= '" + dateString + "'" + " AND type='" + sType + "'", null);
                Toast.makeText(v.getContext(), "修改完成", Toast.LENGTH_LONG).show();
            }
        });
    }
}
