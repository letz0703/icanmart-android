package com.letz.icanmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity
{
    EditText userEmail;
    Button reset;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        userEmail = findViewById(R.id.etEmail_Forgot);

        reset = findViewById(R.id.btnReset_Forgot);
        reset.setOnClickListener(v -> {
            String emailTo = userEmail.getText().toString();
            resetPassword(emailTo);
        });
    }

    private void resetPassword(String email) {
        auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email);
        Intent iMainActivity = new Intent(Forgot.this, MainActivity.class);
        startActivity(iMainActivity);
        finish();
    }
}