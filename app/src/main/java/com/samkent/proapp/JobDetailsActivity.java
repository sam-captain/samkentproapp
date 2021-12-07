package com.samkent.proapp;

import android.content.Intent;
import android.os.Bundle;

import com.samkent.proapp.models.Jobs;
import com.samkent.proapp.utilities.ObjectBox;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samkent.proapp.databinding.ActivityJobDetailsBinding;

public class JobDetailsActivity extends AppCompatActivity {

    private ActivityJobDetailsBinding binding;
    TextView description, requirement,salary,mode,location, applyBefore;
    Button applyJob;
    CollapsingToolbarLayout toolBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        description = findViewById(R.id.txt_description);
        requirement = findViewById(R.id.txt_requirement);
        salary = findViewById(R.id.chip_salary);
        mode = findViewById(R.id.chip_mode);
        location =findViewById(R.id.chip_location);
        applyBefore = findViewById(R.id.txt_job_apply);
        applyJob = findViewById(R.id.btn_apply);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_job_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int menu_item = item.getItemId();

        switch (menu_item){

            case R.id.action_settings:
                Toast.makeText(JobDetailsActivity.this, "You reached settings.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                Toast.makeText(JobDetailsActivity.this, "I lOVE you too.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_mail:
                Intent sendmail = new Intent(Intent.ACTION_SEND);
                sendmail.putExtra(Intent.EXTRA_SUBJECT,"I Need a Job");
                sendmail.setType("message/rfc822");
                String [] addresses = {"kenya@bolt.eu"};
                sendmail.putExtra(Intent.EXTRA_EMAIL, addresses);
                startActivity(sendmail);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("ID")){
            Jobs ourJob = ObjectBox.get().boxFor(Jobs.class).get(getIntent().getLongExtra("ID", 0));
            description.setText(ourJob.getDescription());
            toolBarLayout.setTitle(ourJob.getTitle());
            requirement.setText(ourJob.getRequirements());
            salary.setText(ourJob.getSalary());
            mode.setText("Full Time");
            location.setText(ourJob.getLocation());
            applyBefore.setText(ourJob.getExpiry_date());


        }
    }
}