package com.letz.icanmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Todo extends AppCompatActivity {

    EditText item;
    Button add;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    // adapter to connect array list and list view
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        item = findViewById(R.id.editText);
        add = findViewById(R.id.buttonAdd);
        listView = findViewById(R.id.list);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}