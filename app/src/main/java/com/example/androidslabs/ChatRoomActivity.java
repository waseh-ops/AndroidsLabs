package com.example.androidslabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity  {

    Button receiveBtn;
    Button sendBtn;
    EditText inputBox;
    ArrayList<Message> messages;
    ListView messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        messages = new ArrayList<>();

        receiveBtn = findViewById(R.id.receiveBtn);
        sendBtn = findViewById(R.id.sendBtn);
        inputBox = findViewById(R.id.inputBox);
        messageList = findViewById(R.id.messageList);

        MyAdapter myAdapter = new MyAdapter();
        messageList.setAdapter(myAdapter);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ChatRoomActivity.this);
                builder.setMessage("Do you want to delete this? the selected row is "+position)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                messages.remove(position);
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                builder.create().show();
            }
        });

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputBox.getText().toString();
                Message msg = new Message();
                msg.setSent(false);
                msg.setContent(content);
                messages.add(msg);
                myAdapter.notifyDataSetChanged();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputBox.getText().toString();
                Message msg = new Message();
                msg.setSent(true);
                msg.setContent(content);
                messages.add(msg);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater =getLayoutInflater();
                Message message = messages.get(position);
                if(message.isSent()){
                    convertView = inflater.inflate(R.layout.sender_layout, null);
                    TextView sendMsgTv = convertView.findViewById(R.id.sendMsg);
                    sendMsgTv.setText(message.getContent());
                }else{
                    convertView = inflater.inflate(R.layout.receiver_layout, null);
                    TextView receiveMsgTv = convertView.findViewById(R.id.receiveMsg);
                    receiveMsgTv.setText(message.getContent());
                }
            }
            return convertView;
        }
    }
}