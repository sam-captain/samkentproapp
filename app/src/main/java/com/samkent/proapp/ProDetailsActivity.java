package com.samkent.proapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProDetailsActivity extends AppCompatActivity {

    TextView txt_name,txt_occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_details);

        txt_name = findViewById(R.id.txt_name);
        txt_occupation = findViewById(R.id.txt_occupation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("NAME")){

            txt_name.setText(getIntent().getStringExtra("NAME"));

        }
        if (getIntent().hasExtra("OCCUPATION")){

            txt_occupation.setText(getIntent().getStringExtra("OCCUPATION"));
        }
    }



}