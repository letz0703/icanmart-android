package com.letz.icanmart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity_Retrofit extends AppCompatActivity {

    TextView tvA, tvB, tvC, tvD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvA = findViewById(R.id.tvA_Main);
        tvB = findViewById(R.id.tvB_Main);
        tvC = findViewById(R.id.tvC_Main);
        tvD = findViewById(R.id.tvD_Main);
        //define retrofit base
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

