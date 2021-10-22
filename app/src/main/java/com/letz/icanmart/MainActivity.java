package com.letz.icanmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button calculator;
    Button barcode;
    Button todo;
    Button signup;
    TextView resetLink;
    RecyclerView rv;

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    String userName;List<String> list;
    UsersAdapter adapter;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        calculator = findViewById(R.id.buttonCalc);
        barcode = findViewById(R.id.btnBarcode);
        todo = findViewById(R.id.buttonTodo);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Todo.class);
                startActivity(intent);
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent);
            }
        });
        
        barcode.setOnClickListener(v -> {
            Intent igoBarcode = new Intent(MainActivity.this,Barcode.class);
            startActivity(igoBarcode);
            finish();
        });

        reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.getValue().toString();
                getUsers();
                adapter = new UsersAdapter(list, userName, MainActivity.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.signin_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void getUsers() {
        reference.child("Users").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                if (! key.equals(user.getUid()))
                {
                    list.add(key);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}