package com.example.andy.hellobmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.hellobmi.database.ItemDAO;

import java.text.SimpleDateFormat;

public class PunchEditActivity2 extends AppCompatActivity {
    private TextView punchEditCurrentDate;
    private static NumberPicker punchHourNumberpicker;
    private static NumberPicker punchMinuteNumberpicker;
    private static Spinner punchEditTypeSpinner;
    private ArrayAdapter<CharSequence> punchAdapter;
    private Button punchEditButton;
    private ItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_edit2);

        punchEditCurrentDate = (TextView) findViewById(R.id.punch_edit_current_date);
        punchHourNumberpicker = (NumberPicker) findViewById(R.id.punch_hour_numberpicker);
        punchMinuteNumberpicker = (NumberPicker) findViewById(R.id.punch_minute_numberpicker);
        punchEditTypeSpinner = (Spinner) findViewById(R.id.punch_edit_type_spinner);
        punchEditButton = (Button) findViewById(R.id.punch_edit_button);

        itemDAO = new ItemDAO(this);

        //region 設定numberpicker格式
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
        punchMinuteNumberpicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        //endregion

        //PunchEditType使用values.arrays.xml
        punchAdapter = ArrayAdapter.createFromResource(this, R.array.punchSelections, R.layout.punch_spinner_item);

        //上下班選擇Spinner
        punchEditTypeSpinner.setAdapter(punchAdapter);

        //region 顯示現在的日期時間
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
        final String dateString = sdf.format(date);
        final String timeString = sdft.format(date);
        punchEditCurrentDate.setText(dateString);
        //endregion

        //region 修改打卡資料
        punchEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDAO.punchEdit(editPunchTimeFormat(), editPunchTypeFormat());
                    Toast.makeText(v.getContext(), "更改資料成功", Toast.LENGTH_SHORT).show();
                }
        });
        //endregion
    }

    //region 回傳打卡時間
    private String editPunchTimeFormat(){
        int hour = punchHourNumberpicker.getValue();
        int minute = punchMinuteNumberpicker.getValue();
        String stime = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        return stime;
    }
    //endregion

    //region 回傳打卡類型
    public String editPunchTypeFormat(){
        String stype = punchEditTypeSpinner.getSelectedItem().toString();
        return stype;
    }
    //endregion

}
