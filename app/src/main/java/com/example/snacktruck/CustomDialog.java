package com.example.snacktruck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;

    public CustomDialog(Activity activity)
    {
        super(activity);

        this.c = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        Button saveFood = findViewById(R.id.submit_btn);
        Button cancelFood = findViewById(R.id.cancel_btn);
        saveFood.setOnClickListener(this);
        cancelFood.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.submit_btn:
                Intent intent = new Intent(getContext(), MainActivity.class);
                Bundle newFood = new Bundle();
                EditText foodName = findViewById(R.id.food_txt);
                Switch foodType = findViewById(R.id.food_switch);
                newFood.putString("FOOD_NAME", foodName.getText().toString());
                if(foodType.isChecked())
                {
                    newFood.putString("TYPE_OF_FOOD", foodType.getTextOn().toString());
                }else
                {
                    newFood.putString("TYPE_OF_FOOD", foodType.getTextOff().toString());
                }
                intent.putExtras(newFood);
                c.startActivity(intent);
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
