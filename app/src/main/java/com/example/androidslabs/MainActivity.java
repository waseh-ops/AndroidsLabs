package com.example.androidslabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Switch btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);


        btn = findViewById(R.id.button3);
        btn.setOnClickListener(v -> {

            Toast.makeText(MainActivity.this, this.getString(R.string.toast_message), Toast.LENGTH_LONG ).show();


        });

        btn1 = findViewById(R.id.switch1);

        btn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged (CompoundButton cb, boolean b){
               if(b == true){
                   Snackbar.make(btn1, (R.string.switch_on), Snackbar.LENGTH_SHORT)
                           .setAction((R.string.undo), click -> cb.setChecked(!b))
                           .show();

               }else{
                   //snackbar.make(btn1, "The Switch is now Off, Snackbar.LENGTH_SHORT).show(); if i do this it doesn't show up in french
                   //thats why i did the following
                   Snackbar.make(btn1, (R.string.switch_off), Snackbar.LENGTH_SHORT)
                           .setAction((R.string.undo), click -> cb.setChecked(!b))
                           .show();
               }

            }

            
        });








    }


}
