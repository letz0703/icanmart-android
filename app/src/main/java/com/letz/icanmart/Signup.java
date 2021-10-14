package com.letz.icanmart;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Signup extends AppCompatActivity
{
    ImageView profileImage;

    EditText email;
    EditText password;
    Button btnSignup;
    ProgressBar progressBar;
    boolean imageControl = false;
    TextView forgotLink;


    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.etEmail_SignUp);
        password = findViewById(R.id.etPassword_SignUp);
        btnSignup = findViewById(R.id.btnSignUp_Signup);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference();
        profileImage = findViewById(R.id.iv_Signup);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        progressBar.setVisibility(View.INVISIBLE);

        forgotLink = findViewById(R.id.tvForgot_Signup);

        btnSignup.setOnClickListener(v -> {
            btnSignup.setClickable(false);
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            signup(userEmail, userPassword);
            finish();
        });

        forgotLink.setOnClickListener(v -> {
            Intent iForgot = new Intent(Signup.this,Forgot.class);
            startActivity(iForgot);
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

    public void signup(String userEmail, String userPassword) {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            dbReference.child("Users").child(auth.getUid()).child("userEmail").setValue(userEmail);

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
                                                        dbReference.child("Users").child(auth.getUid()).child("image")
                                                                .setValue(filePath)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>()
                                                        {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(Signup.this, "Write to database is successful", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener()
                                                        {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(Signup.this, "Write to database was not successful", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });

                                            }
                                        });
                            } else {
                                dbReference.child("Users").child(auth.getUid()).child("image").setValue("null");
                            }

                            Intent igoMain = new Intent(Signup.this, MainActivity.class);
                            igoMain.putExtra("userEmail", userEmail);
                            startActivity(igoMain);
                            finish();

                            Toast.makeText(Signup.this, "Your Account is created", Toast.LENGTH_SHORT).show();
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
//                        else {
//                            Toast.makeText(Signup.this, "There is a problem. Try again later", Toast.LENGTH_SHORT).show();
//                        }

                });
    }
}


