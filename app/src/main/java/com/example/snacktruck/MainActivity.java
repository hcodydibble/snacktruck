package com.example.snacktruck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    String[] veggies = {"French fries", "Veggieburger", "Carrots", "Apple", "Banana", "Milkshake"};
    String[] nonVeggies = {"Cheeseburger", "Hamburger", "Hot dog"};
    ArrayList<CheckBox> checked = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> foods = new ArrayList<>(Arrays.asList(veggies));
        foods.addAll(Arrays.asList(nonVeggies));

        LinearLayout ll = findViewById(R.id.food_list);

        for (int i = 0; i < foods.size(); i++){
            CheckBox foodCheck = new CheckBox(MainActivity.this);
            foodCheck.setText(foods.get(i));
            foodCheck.setTag(foods.get(i));

            if(Arrays.asList(veggies).contains(foods.get(i))){
                foodCheck.setTextColor(Color.GREEN);
            }else{
                foodCheck.setTextColor(Color.RED);
            }

            foodCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    if(checkBox.isChecked()){
                        checkBox.setChecked(true);
                        checked.add(checkBox);
                    }else{
                        checkBox.setChecked(false);
                        checked.remove(checkBox);

                    }
                }
            });

            ll.addView(foodCheck);
        }


    }

    public void submitOrder(View view){
        alertDialog(turnListToString());
    }

    private String turnListToString()
    {
        StringBuilder sb = new StringBuilder(checked.size());
        for (int i = 0; i < checked.size(); i++)
        {
            sb.append(checked.get(i).getText());
            sb.append("\n");
        }
        return sb.toString();
    }

    private void alertDialog(String items){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Is your order correct?");
        dialog.setMessage(items);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendOrder();
                for(CheckBox cb : checked)
                {
                    cb.setChecked(false);
                }
                checked.clear();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendOrder(){
        // This would be used to send the order along after the user confirmed it was correct.
    }
}
