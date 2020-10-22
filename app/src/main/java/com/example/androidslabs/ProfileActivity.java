package com.example.androidslabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton imgBtn;
    EditText emailET;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button goToChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        sp = getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
        Intent mainActiivty = getIntent();
        String email = mainActiivty.getStringExtra(MainActivity.EMAIL_INTENT_KEY);
        emailET=findViewById(R.id.emailInputProfile);
        emailET.setText(email);

        imgBtn = findViewById(R.id.takePictureBtn);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

            }
        });

        goToChat = findViewById(R.id.goToChat);
        goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToChatRoom = new Intent(ProfileActivity.this,ChatRoomActivity.class);
                startActivity(goToChatRoom);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("ProfileActivity", "In function:" +"onActivityResult");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgBtn.setImageBitmap(imageBitmap);
        }

    }

    @Override
    protected void onStart() {
        Log.e("ProfileActivity", "In function:" +"onActivityResult");
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}