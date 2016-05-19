package com.example.andy.hellobmi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Andy on 2016/3/17.
 */
public class FoodListAdapter extends ArrayAdapter<String> {

    public FoodListAdapter(Context context, String[] foodList) {
        super(context, R.layout.custom_row, foodList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater foodsInflater = LayoutInflater.from(getContext());
        View customView = foodsInflater.inflate(R.layout.custom_row, parent, false);

        String singleFoodItem = getItem(position);
        TextView foodText = (TextView)customView.findViewById(R.id.foodText);
        ImageView foodsImage = (ImageView)customView.findViewById(R.id.foodsImage);
        foodText.setText(singleFoodItem);
        foodsImage.setImageResource(R.drawable.apple);
        return customView;

    }
}
