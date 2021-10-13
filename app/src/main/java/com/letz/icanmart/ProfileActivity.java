package com.letz.icanmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity
{
    ImageView ivCircledProfileImage;
    TextView textTap;
    EditText etUserName;
    Button updateProfile;

    FirebaseDatabase database;
    DatabaseReference fdbref;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivCircledProfileImage = findViewById(R.id.ivCircle_ProfileActivity);
        etUserName = findViewById(R.id.etUserName_ProfileActivity);
        updateProfile = findViewById(R.id.btnUpdate_ProfileActivity);

        database = FirebaseDatabase.getInstance();
        fdbref= database.getReference("");
        auth= FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        getUserInfo();

    }

    public void updateProfile() {
        String userName = etUserName.getText().toString();
        fdbref.child("Users").child(firebaseUser.getUid()).setValue(userName);
    }


    public void getUserInfo() {
        fdbref.child("Users").child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("userName").getValue().toString();
                        String image = snapshot.child("image").getValue().toString() ;
                        etUserName.setText(name);

                        if (image.equals("null"))
                        {
                            ivCircledProfileImage.setImageResource(R.drawable.user_icon);

                        } else {
                            Picasso.get().load(image).into(ivCircledProfileImage);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}