package com.samkent.proapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.samkent.proapp.utilities.PreferenceStorage;

public class MainActivity extends AppCompatActivity {
int count = 0;
    PreferenceStorage preferenceStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceStorage = new PreferenceStorage(this);

        Button  btnMain  = findViewById(R.id.btn_main);
    Button  btnCreateProf = findViewById(R.id.btn_create_prof);

        if (preferenceStorage.isLoggedIn()){
//            btnCreateProf.setVisibility(View.GONE);
        }

    btnMain.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent goToHome  = new Intent(MainActivity.this,ExploreActivity.class);


            startActivity(goToHome);
        }
    });


    btnCreateProf.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent goToAuth  = new Intent(MainActivity.this,JobFormActivity.class);


            startActivity(goToAuth);
        }
    });

    }

}