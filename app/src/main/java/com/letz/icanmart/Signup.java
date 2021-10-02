package com.letz.icanmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity
{

    EditText email;
    EditText password;
    Button btnSignup;
    ProgressBar progressBar;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbReference = database.getReference("");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.etEmail_SignUp);
        password = findViewById(R.id.etPassword_SignUp);
        btnSignup = findViewById(R.id.btnSignUp_Signup);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        btnSignup.setOnClickListener(v -> {
            btnSignup.setClickable(false);
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            signUpFirebase(userEmail, userPassword);
        });
    }

    public void signUpFirebase(String userEmail, String userPassword)
    {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            dbReference.child("Users").child(auth.getUid()).child("userEmail").setValue(userEmail);
                            Toast.makeText(Signup.this, "Your Account is created", Toast.LENGTH_SHORT).show();
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);

                            Intent igoMain = new Intent(Signup.this,MainActivity.class);
                            igoMain.putExtra("userEmail",userEmail);
                            startActivity(igoMain);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Signup.this, "There is a problem. Try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}