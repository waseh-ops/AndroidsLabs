package com.example.androidslabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private EditText emailET;
    private EditText passET;
    private Button loginBtn;
    private Button goToChat;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public final static String SP_NAME = "SharePreference_File";
    public final static String EMAIL_SP_KEY = "email_sp_key";
    public final static String EMAIL_INTENT_KEY = "EMAIL_INTENT_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.emailInput);
        passET = findViewById(R.id.passInput);
        sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        emailET.setText(sp.getString(EMAIL_SP_KEY,""));

        loginBtn= findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(MainActivity.this,ProfileActivity.class);
                goToProfile.putExtra(EMAIL_INTENT_KEY,emailET.getText().toString());
                startActivity(goToProfile);
            }
        });

        goToChat = findViewById(R.id.goToChat);
        goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToChatRoom = new Intent(MainActivity.this,ChatRoomActivity.class);
                startActivity(goToChatRoom);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        String emailAddr = emailET.getText().toString();
        editor.putString(EMAIL_SP_KEY,emailAddr);
        editor.commit();
//        emailET.setText("");

    }
}
