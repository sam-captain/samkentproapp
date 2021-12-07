package com.samkent.proapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.samkent.proapp.adapters.JobProfessionsAdapter;
import com.samkent.proapp.models.Jobs;
import com.samkent.proapp.models.Profession;
import com.samkent.proapp.utilities.ObjectBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import io.objectbox.Box;

public class JobFormActivity extends AppCompatActivity {

    ImageView imageAttachment;

    final int DOCUMENT_REQUEST_CODE = 172;
    TextInputEditText inputTitle, inputDescription, inputLocation, inputRequirement, inputModeOfWork, inputTimeOfDay, inputSalary, inputWorkingHours;
    Chip tomorrowChip, twoDaysChip, oneWeekChip, customChip;
    String  attachmentUrl;
    int dateDue = 0;
    RecyclerView selectProfessionRecyclerView;
    JobProfessionsAdapter jobProfessionsAdapter;
    Box<Profession>professionBox = ObjectBox.get().boxFor(Profession.class);

    Box<Jobs> jobBox = ObjectBox.get().boxFor(Jobs.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobform);

        Button btnPost = findViewById(R.id.btn_post);

        imageAttachment = findViewById(R.id.image_attachment);
        selectProfessionRecyclerView = findViewById(R.id.selectProfessionRecyclerview);
        selectProfessionRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        selectProfessionRecyclerView.setNestedScrollingEnabled(true);

        jobProfessionsAdapter = new JobProfessionsAdapter(this,professionBox.getAll());
        selectProfessionRecyclerView.setAdapter(jobProfessionsAdapter);


        inputTitle = findViewById(R.id.txt_job_title);
        inputDescription = findViewById(R.id.txt_job_description);
        inputLocation = findViewById(R.id.txt_location);
        inputRequirement =findViewById(R.id.txt_job_requirement);
        inputModeOfWork = findViewById(R.id.txt_mode_work);
        inputTimeOfDay = findViewById(R.id.txt_time_of_day);
        inputSalary = findViewById(R.id.txt_salary);
        inputWorkingHours = findViewById(R.id.txt_working_hours);
        tomorrowChip = findViewById(R.id.chip_tommorow);
        twoDaysChip = findViewById(R.id.chip_two_days);
        oneWeekChip = findViewById(R.id.chip_one_week);
        customChip = findViewById(R.id.chip_custom);


        tomorrowChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDue = 1;
            }
        });
        twoDaysChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDue = 2;
            }
        });
        oneWeekChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDue = 7;
            }
        });
        customChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDue = 14;
                Toast.makeText(JobFormActivity.this, "Date Due:2weeks", Toast.LENGTH_SHORT).show();
            }
        });

        imageAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickAttachment = new Intent(Intent.ACTION_GET_CONTENT);
                pickAttachment.setType("application/*");
                String[] mimeTypes = {
                        "application/xls",
                        "application/pdf",
                        "application/docx",
                        "application/doc",
                };
                pickAttachment.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                pickAttachment.addCategory(Intent.CATEGORY_OPENABLE);


                try {
                    startActivityForResult(pickAttachment, DOCUMENT_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {

                    Toast.makeText(JobFormActivity.this, "Please Attach your File", Toast.LENGTH_SHORT).show();
                }
            }

        });



        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateInputs()){
                    saveJob();
                }
//                Intent goToDetails = new Intent(JobFormActivity.this,JobDetailsActivity.class);
//                startActivity(goToDetails);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == DOCUMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            attachmentUrl= uri.toString();
            Toast.makeText(this, attachmentUrl, Toast.LENGTH_SHORT).show();
            imageAttachment.setImageDrawable(getResources().getDrawable(R.drawable.food));
        }

    }


    public boolean validateInputs() {
        boolean response = false;
        if (inputTitle.getText().toString().isEmpty()) {
            inputTitle.setError("Please Enter the title");


        }
        else if (inputDescription.getText().toString().isEmpty()) {
            inputDescription.setError("Enter your job description");



        }
        else if (inputLocation.getText().toString().isEmpty()){
            inputLocation.setError("please enter you location");
        }
        else if (inputRequirement.getText().toString().isEmpty()){
            inputRequirement.setError("Requirements are a must");
        }

        else if (dateDue == 0) {
            Toast.makeText(this, "please select a date to publish", Toast.LENGTH_SHORT).show();
        }
        else if (inputModeOfWork.getText().toString().isEmpty()){
            inputModeOfWork.setError("enter mode of work");
        }
        else if (inputTimeOfDay.getText().toString().isEmpty()){
            inputTimeOfDay.setError("enter time of the day to work");
        }
        else if (inputSalary.getText().toString().isEmpty()){
            inputSalary.setError("please enter the amount  to be payed");
        }
        else if (inputWorkingHours.getText().toString().isEmpty()){
            inputWorkingHours.setError("please enter the availability time to work");
        }
        else if (jobProfessionsAdapter.profession_id == 0){
            Toast.makeText(this, "please select a profession", Toast.LENGTH_SHORT).show();
        }
        else{
            return true;
        }
        return response;

    }
    public void saveJob(){
            Jobs ourJob = new Jobs();
            ourJob.setTitle(inputTitle.getText().toString().trim());
            ourJob.setLocation(inputLocation.getText().toString().trim());
            ourJob.setDescription(inputDescription.getText().toString().trim());
            ourJob.setRequirements(inputRequirement.getText().toString().trim());
            ourJob.setWorking_hours(Integer.parseInt(inputWorkingHours.getText().toString().trim()));
            ourJob.setSalary(inputSalary.getText().toString().trim());
            ourJob.setMode_of_work(2);
            ourJob.setStatus("Active");
            ourJob.setExpiry_date(String.valueOf(dateDue));
            ourJob.setProfession(jobProfessionsAdapter.profession_id);
            if (attachmentUrl != null){
                ourJob.setAttachment(attachmentUrl);
            }
            long id = jobBox.put(ourJob);
            Intent goToDetails = new Intent(JobFormActivity.this, JobDetailsActivity.class);
            goToDetails.putExtra("ID", id);
            startActivity(goToDetails);
            finish();

        }


}