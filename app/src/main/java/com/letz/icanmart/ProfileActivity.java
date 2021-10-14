package com.letz.icanmart;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
{
    ImageView profileImage;

    ImageView ivCircledProfileImage;
    TextView textTap;
    EditText etUserName;
    Button btnUpdateProfile;

    FirebaseDatabase database;
    DatabaseReference fdbref;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    Uri imageUri;
    Boolean imageControl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ivCircledProfileImage = findViewById(R.id.ivCircle_ProfileActivity);
        etUserName = findViewById(R.id.etUserName_ProfileActivity);

        btnUpdateProfile = findViewById(R.id.btnUpdate_ProfileActivity);
        btnUpdateProfile.setOnClickListener(v -> {
            updateProfile();
        });


        database = FirebaseDatabase.getInstance();
        fdbref = database.getReference("");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getUserInfo();

        ivCircledProfileImage.setOnClickListener(v -> imageChooser());
    }

    public void updateProfile() {
        String userName = etUserName.getText().toString();
        fdbref.child("Users").child(firebaseUser.getUid()).setValue(userName);

        if (imageControl) {
            UUID randomID = UUID.randomUUID();
            String imageName = "image/" + randomID + ".jpg";
            storageReference.child(imageName).putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            StorageReference storageReference_imageName = firebaseStorage.getReference(imageName);
                            storageReference_imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                            {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String filePath = uri.toString();
                                    fdbref.child("Users").child(auth.getUid()).child("image")
                                            .setValue(filePath)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(ProfileActivity.this, "Write to database is successful", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfileActivity.this, "Write to database was not successful", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                        }
                    });
        } else {
            fdbref.child("Users").child(auth.getUid()).child("image").setValue("null");
        }

        Intent igoMain = new Intent(ProfileActivity.this, MainActivity.class);
        igoMain.putExtra("userName", userName);
        startActivity(igoMain);
        finish();

    }


    public void getUserInfo() {
        fdbref.child("Users").child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("userName").getValue().toString();
                        String image = snapshot.child("image").getValue().toString();
                        etUserName.setText(name);

                        if (image.equals("null")) {
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

    public void imageChooser() {
        Intent iSaveImage = new Intent();
        iSaveImage.setType("image/*");
        iSaveImage.setAction(Intent.ACTION_GET_CONTENT);
        launchLoadImage.launch(iSaveImage);
        finish();
    }

    ActivityResultLauncher<Intent> launchLoadImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Picasso.get().load(imageUri).into(profileImage);
                        imageControl = true;
                    } else {
                        imageControl = false;
                    }
                }
            }
    );
}