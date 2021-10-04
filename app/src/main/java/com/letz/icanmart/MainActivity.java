package com.letz.icanmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button calculator;
    Button barcode;
    Button todo;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = findViewById(R.id.buttonCalc);
        barcode = findViewById(R.id.btnBarcode);
        todo = findViewById(R.id.buttonTodo);
        signup = findViewById(R.id.btnSignup_Main);
        signup = findViewById(R.id.btnSignup_Main);
        signup.setOnClickListener(v -> {
            Intent igoSignup = new Intent(MainActivity.this,Signup.class);
            startActivity(igoSignup);
        });

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
    }
}