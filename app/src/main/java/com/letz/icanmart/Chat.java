package com.letz.icanmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity
{
    private ImageView ivBack;
    private TextView tvChat;
    private EditText etChat;
    private FloatingActionButton fabChat;
    private RecyclerView rvChat;

    String userName, fdbName;

    FirebaseDatabase database;
    DatabaseReference fdbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ivBack = findViewById(R.id.ivBack_Chat);
        tvChat = findViewById(R.id.tv_Chat);
        etChat = findViewById(R.id.etml_Chat);
        fabChat = findViewById(R.id.fab_Chat);
        rvChat = findViewById(R.id.rv_Chat);

        database = FirebaseDatabase.getInstance();
        fdbref = database.getReference("");

        userName = getIntent().getStringExtra("userName");
        fdbName = getIntent().getStringExtra("fdbName");

        tvChat.setText(fdbName);

        ivBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent igoMainActivity = new Intent(Chat.this, MainActivity.class);
                startActivity(igoMainActivity);
            }
        });

        fabChat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String message = etChat.getText().toString();
                if (!message.equals("")) {
                    sendMessage(message);
                    etChat.setText("");
                }
            }
        });
    }

    public void sendMessage(String message) {
        final String key = fdbref.child("Message").child(userName).child(fdbName).push().getKey();
        final Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("message", message);
        messageMap.put("from", userName);
        fdbref.child("Message").child(userName).child(fdbName).child(key).setValue(messageMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            fdbref.child("Message").child(fdbName).child(userName)
                                    .child(key).setValue(messageMap);
                        }
                    }
                });
    }
}