package com.letz.icanmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Barcode extends AppCompatActivity
{
    TextView barcode;
    Button  save;
    Button search;
    TextView itemName;
    String barcodeItemName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference fdbrefItem = database.getReference("").child("item");


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        barcode = findViewById(R.id.tvBarcode_Barcode);
        save = findViewById(R.id.btnSendBarcode_Barcode);
        itemName = findViewById(R.id.tvItemName_Barcode);
        search = findViewById(R.id.btnSearch_Barcode);

        search.setOnClickListener(v -> {
            readBarcdoe();
        });

        barcode.setOnClickListener(v -> {

        });

        save.setOnClickListener(v -> {

        });
    }

    public void readBarcdoe()
    {
        //read from db
        fdbrefItem.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                barcodeItemName = snapshot.child("name").getValue().toString();
                itemName.setText(barcodeItemName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}