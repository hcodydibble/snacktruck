package com.example.snacktruck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] veggies = {"French fries", "Veggieburger", "Carrots", "Apple", "Banana", "Milkshake"};
    String[] nonVeggies = {"Cheeseburger", "Hamburger", "Hot dog"};
    List<CheckBox> checked = new ArrayList<>();
    List<CheckBox> foodCheckboxes = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.addFood)
        {
            CustomDialog dialog = new CustomDialog(this);
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle foodToAdd = getIntent().getExtras();
        if (foodToAdd != null)
        {
            String foodName = foodToAdd.getString("FOOD_NAME");
            String foodType = foodToAdd.getString("TYPE_OF_FOOD");
            if (foodType.equals("Veggie"))
            {
                int currentSize = veggies.length;
                int newSize = currentSize + 1;
                String[] temp = new String [newSize];
                for (int i = 0; i < currentSize; i++)
                {
                    temp[i] = veggies[i];
                }

                temp[newSize-1] = foodName.substring(0,1).toUpperCase() + foodName.substring(1).toLowerCase();
                veggies = temp;
            }else
            {
                int currentSize = nonVeggies.length;
                int newSize = currentSize + 1;
                String[] temp = new String [newSize];
                for (int i = 0; i < currentSize; i++)
                {
                    temp[i] = nonVeggies[i];
                }

                temp[newSize-1] = foodName.substring(0,1).toUpperCase() + foodName.substring(1).toLowerCase();
                nonVeggies = temp;
            }
        }

        LinearLayout ll = findViewById(R.id.food_list);
        ArrayList<String> foods = new ArrayList<>(Arrays.asList(veggies));
        foods.addAll(Arrays.asList(nonVeggies));
        CheckBox veg = findViewById(R.id.veggie);
        CheckBox nonVeg = findViewById(R.id.non_veggie);

        // Create a checkbox for each food item from the combined list
        for (int i = 0; i < foods.size(); i++){
            CheckBox foodCheck = new CheckBox(MainActivity.this);
            foodCheck.setText(foods.get(i));

            // Set the text color and tag of the food item depending on which list it is in, veggie or non-veggie
            if(Arrays.asList(veggies).contains(foods.get(i))){
                foodCheck.setTextColor(Color.GREEN);
                foodCheck.setTag("veggie");
            }else{
                foodCheck.setTextColor(Color.RED);
                foodCheck.setTag("non-veggie");
            }

            // Add an onClickListener to each created checkbox to track if the food item has been checked or not
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

            foodCheckboxes.add(foodCheck);
            ll.addView(foodCheck);
        }

        // Add an onClickListener to the Veggie checkbox to filter out foods if un-checked or add them back in if checked
        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if(checkBox.isChecked()){
                    checkBox.setChecked(true);
                    for (int i = 0; i < foodCheckboxes.size(); i++)
                    {
                        CheckBox currentFood = foodCheckboxes.get(i);
                        if (currentFood.getTag() == "veggie")
                        {
                            currentFood.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    checkBox.setChecked(false);
                    for (int i = 0; i < foodCheckboxes.size(); i++)
                    {
                        CheckBox currentFood = foodCheckboxes.get(i);
                        if (currentFood.getTag() == "veggie")
                        {
                            currentFood.setChecked(false);
                            currentFood.setVisibility(View.GONE);
                            checked.remove(currentFood);
                        }
                    }

                }
            }
        });

        // Add an onClickListener to the Non-Veggie checkbox to filter out foods if un-checked or add them back in if checked
        nonVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if(checkBox.isChecked()){
                    checkBox.setChecked(true);
                    for (int i = 0; i < foodCheckboxes.size(); i++)
                    {
                        CheckBox currentFood = foodCheckboxes.get(i);
                        if (currentFood.getTag() == "non-veggie")
                        {
                            currentFood.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    checkBox.setChecked(false);
                    for (int i = 0; i < foodCheckboxes.size(); i++)
                    {
                        CheckBox currentFood = foodCheckboxes.get(i);
                        if (currentFood.getTag() == "non-veggie")
                        {
                            currentFood.setChecked(false);
                            currentFood.setVisibility(View.GONE);
                            checked.remove(currentFood);
                        }
                    }

                }
            }
        });




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
                // This is where sendOrder() would be called.
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
