package com.example.androidslabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MESSAGE_DB";
    public static final int DEFAULT_VERSION =1;
    public static final String MESSAGE_TABLE_NAME = "message";
    public static final String MESSAGE_TABLE_COLUMN_ID = "ID";
    public static final String MESSAGE_TABLE_COLUMN_CONTENT = "content";
    public static final String MESSAGE_TABLE_COLUMN_ISSENT = "isSent";
    private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE "+MESSAGE_TABLE_NAME+" ("
                                                        +MESSAGE_TABLE_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                        +MESSAGE_TABLE_COLUMN_CONTENT+" TEXT ,"
                                                        +MESSAGE_TABLE_COLUMN_ISSENT+" INTEGER )";


    public MySqliteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DEFAULT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MESSAGE_TABLE_NAME);
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    public long insertMessage(Message message){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MESSAGE_TABLE_COLUMN_CONTENT, message.getContent());
        if(message.isSent()) {
            cv.put(MESSAGE_TABLE_COLUMN_ISSENT, 1);
        }else{
            cv.put(MESSAGE_TABLE_COLUMN_ISSENT,0);
        }
        long id = db.insert(MESSAGE_TABLE_NAME,null,cv);
        return id;
    }

    public ArrayList<Message> getAllMessage() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Message> messages = new ArrayList<>();
        Cursor cursor =
                db.query(MESSAGE_TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex(MESSAGE_TABLE_COLUMN_ID));
            String content = cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_COLUMN_CONTENT));
            int isSendInt = cursor.getInt(cursor.getColumnIndex(MESSAGE_TABLE_COLUMN_ISSENT));
            boolean isSend = true;
            if (isSendInt == 0) {
                isSend = false;
            }
            Message msg = new Message(id, content, isSend);
            messages.add(msg);

        }
        return messages;
    }
}
