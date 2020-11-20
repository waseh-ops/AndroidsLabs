package com.example.androidslabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity  {

    Button receiveBtn;
    Button sendBtn;
    EditText inputBox;
    ArrayList<Message> messages;
    ListView messageList;
    MySqliteHelper sqliteHelper;
    FrameLayout detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        sqliteHelper = new MySqliteHelper(getApplicationContext());
        messages = sqliteHelper.getAllMessage() ;

        receiveBtn = findViewById(R.id.receiveBtn);
        sendBtn = findViewById(R.id.sendBtn);
        inputBox = findViewById(R.id.inputBox);
        messageList = findViewById(R.id.messageList);

        MyAdapter myAdapter = new MyAdapter();
        messageList.setAdapter(myAdapter);

        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ChatRoomActivity.this);
                builder.setMessage("Do you want to delete this message? the selected row is " + position + " and the database ID:" + messages.get(position).getId())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                messages.remove(position);
                                Message messageToBeDeleted = messages.get(position);
                                sqliteHelper.deleteMessage(messageToBeDeleted);
                                messages = sqliteHelper.getAllMessage();
                                myAdapter.notifyDataSetChanged();

                            }
                        });
                builder.create().show();
                return true;
            }
        });

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputBox.getText().toString();
                Message msg = new Message();
                msg.setSent(false);
                msg.setContent(content);
//                messages.add(msg);
                sqliteHelper.insertMessage(msg);
                messages = sqliteHelper.getAllMessage();
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
//                messages.add(msg);
                sqliteHelper.insertMessage(msg);
                messages = sqliteHelper.getAllMessage();
                myAdapter.notifyDataSetChanged();
            }
        });

        detailFragment = findViewById(R.id.detailFragment);
        boolean isTablet = detailFragment!=null;
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isTablet){
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle dataToPass = new Bundle();
                    Message msg = (Message)myAdapter.getItem(position);
                    long msgId = msg.getId();
                    boolean isSent = msg.isSent();
                    dataToPass.putLong("message_id",msgId);
                    dataToPass.putBoolean("isSent",isSent);
                    detailFragment.setArguments(dataToPass);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.detailFragment,detailFragment);
                    transaction.commit();
                }else{
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle dataToPass = new Bundle();
                    Message msg = (Message)myAdapter.getItem(position);
                    long msgId = msg.getId();
                    boolean isSent = msg.isSent();
                    dataToPass.putLong("message_id",msgId);
                    dataToPass.putBoolean("isSent",isSent);
                    detailFragment.setArguments(dataToPass);
                    Intent intent =new Intent(ChatRoomActivity.this, EmptyActivity.class);
                    startActivity(intent);
                }
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
            View view = convertView;

                LayoutInflater inflater =getLayoutInflater();
                Message message = messages.get(position);
                if(message.isSent()){
                    view = inflater.inflate(R.layout.sender_layout, null);
                    TextView sendMsgTv = view.findViewById(R.id.sendMsg);
                    sendMsgTv.setText(message.getContent());
                }else{
                    view = inflater.inflate(R.layout.receiver_layout, null);
                    TextView receiveMsgTv = view.findViewById(R.id.receiveMsg);
                    receiveMsgTv.setText(message.getContent());
                }

            return view;
        }
    }
}