package com.example.androidslabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    Button receiveBtn;
    Button sendBtn;
    EditText inputBox;
    ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        receiveBtn = findViewById(R.id.receiveBtn);
        sendBtn = findViewById(R.id.sendBtn);
        inputBox = findViewById(R.id.inputBox);

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}