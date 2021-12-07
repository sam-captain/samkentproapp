package com.samkent.proapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.samkent.proapp.adapters.ProfessionAdapter;
import com.samkent.proapp.adapters.ProfessionalAdapter;
import com.samkent.proapp.models.Profession;
import com.samkent.proapp.models.Professional;
import com.samkent.proapp.utilities.ObjectBox;
import com.samkent.proapp.utilities.PreferenceStorage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class HomeActivity extends AppCompatActivity {

    List<Professional> professionals = new ArrayList<>();
    ProfessionalAdapter professionalAdapter;


    List<Profession> professions = new ArrayList<>();
    ProfessionAdapter professionAdapter;

    TextView txthello;
    PreferenceStorage preferenceStorage;
    Box<Profession>professionBox = ObjectBox.get().boxFor(Profession.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    @Override
    protected void onResume() {
        super.onResume();


        String name = preferenceStorage.getUserName();
        txthello.setText("Hello "+name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menu_item = item.getItemId();

        switch (menu_item) {

            case R.id.action_settings:
                Intent intent = new Intent(HomeActivity.this, ExploreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
               showAlert();
                return true;
            case R.id.action_add:
                Intent i = new Intent(HomeActivity.this, JobFormActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Don't Leave Me")
                .setMessage("Are You Sure You Want To Go?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        preferenceStorage.setUserLoggedIn(false);
                        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    public void addProfessionDialog(){
        final BottomSheetDialog addProfession = new BottomSheetDialog(this);
        addProfession.setContentView(R.layout.new_profession_layout);
        TextInputEditText txtViewProfession = addProfession.findViewById(R.id.input_profession_name);
        Button btnAddProfession = addProfession.findViewById(R.id.btn_add_profession);
        btnAddProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profession = txtViewProfession.getText().toString().trim();
                if (profession.isEmpty()){
                    txtViewProfession.setError("Please Add New Profession");
                }
                else{
                    saveProfession(profession);
                    professionAdapter.notifyDataSetChanged();
                    addProfession.dismiss();

                }
            }
        });
        addProfession.show();
    }
    public void saveProfession(String name){
        Profession profession = new Profession(name);
        long id = professionBox.put(profession);
        profession.setId(id);
        professions.add(profession);
    }

}