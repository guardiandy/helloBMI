package com.example.andy.hellobmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FoodSuggest extends AppCompatActivity {
    ListView foodListView;
    String[] foodList = {"香蕉\n很甜", "蘋果\n普通甜", "鳳梨\n普通甜", "西瓜\n非常甜", "柳丁\n非常甜"};
    ArrayAdapter<String> foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_suggest);

        foodListView = (ListView) findViewById(R.id.food_list_view);
        foodListAdapter = new FoodListAdapter(this, foodList);
        foodListView.setAdapter(foodListAdapter);

        foodListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(FoodSuggest.this, food, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
