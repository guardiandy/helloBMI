package com.example.andy.hellobmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BMISuggest extends AppCompatActivity {
    TextView BMISuggestTextColumn1;
    TextView BMISuggestTextColumn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmisuggest);
        BMISuggestTextColumn1 = (TextView)this.findViewById(R.id.BMI_Suggest_Text＿Column1);
        BMISuggestTextColumn2= (TextView)this.findViewById(R.id.BMI_Suggest_Text＿Column2);
        String ManBMI = "男生\n一般體重18.5～25\n理想體重24\n超重25～30\n嚴重超重30～40\n極度超重40以上";
        String WomanBMI = "女生\n一般體重18.5～25\n理想體重22\n超重25～30\n嚴重超重30～40\n極度超重40以上";
        BMISuggestTextColumn1.setText(ManBMI);
        BMISuggestTextColumn2.setText(WomanBMI);
    }
}
