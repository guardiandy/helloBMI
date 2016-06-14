package com.example.andy.hellobmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText personHeightText;
    private EditText personWeightText;
    private TextView personBMIText;
    private Button personBMIButton;
    private Button personBMISuggestButton;
    private Button personBMIAboutButton;
    private Button personPunchButton;
    private Button personSQLButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personHeightText = (EditText) findViewById(R.id.main_height_text);
        personWeightText = (EditText) findViewById(R.id.ｍain_ｗeight_text);
        personBMIText = (TextView) findViewById(R.id.main_bmiValue_text);
        personBMIButton = (Button) findViewById(R.id.ｍain_BMI_button);
        personBMISuggestButton = (Button) findViewById(R.id.main_BMI_sugget_button);
        personBMIAboutButton = (Button) findViewById(R.id.main_BMI_about_button);
        personPunchButton = (Button) findViewById(R.id.main_punch_button);
        personSQLButton = (Button) findViewById(R.id.main_SQL_button);

        /**
         * 按鈕計算BMI
         */
        personBMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberFormat nf = NumberFormat.getInstance();
                double Height = Double.parseDouble(personHeightText.getText().toString()) / 100;
                double Weight = Double.parseDouble(personWeightText.getText().toString());
                double BMI = Weight / (Height * Height);
                if (BMI < 24)
                    personBMIText.setText(nf.format(BMI) + "您的體重正常");
                else if (BMI < 30)
                    personBMIText.setText(nf.format(BMI) + "您的體重超重");
                else if (BMI < 40)
                    personBMIText.setText(nf.format(BMI) + "您的體重嚴重超重");
                else
                    personBMIText.setText(nf.format(BMI) + "您的BMI值異常");

            }

        });


        /**
         * 呼叫BMI建議Activity
         */
        personBMISuggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BMISuggest.class);
                startActivity(intent);
            }
        });
        /**
         * 呼叫關於Activity
         */
//        personBMIAboutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, BMIAbout.class);
//                startActivity(intent);
//            }
//        });
        /**
         * 呼叫FoodSuggest Listview
         */
        personBMIAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSuggest.class);
                startActivity(intent);
            }
        });
        /**
         * 呼叫打卡Activity
         */
        personPunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PunchActivity2.class);
                startActivity(intent);
            }
        });
        /**
        * 呼叫SQL Activity
        */
        personSQLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SQLActivity.class);
                startActivity(intent);
            }
        });
    }
}