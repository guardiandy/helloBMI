package com.example.andy.hellobmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PunchActivity extends AppCompatActivity {
    private TextView punchDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);

        punchDateTextView = (TextView)findViewById(R.id.punch_Date_TextView);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM  dd, a h:mm ");
        String dataString = sdf.format(date);
        punchDateTextView.setText(dataString);

    }
}
