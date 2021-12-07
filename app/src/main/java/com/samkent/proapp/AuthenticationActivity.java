package com.samkent.proapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.samkent.proapp.utilities.PreferenceStorage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

public class AuthenticationActivity extends AppCompatActivity {
    TextInputEditText inputemail, inputconfirm, inputpassword, inputnumber, inputname;
    ImageView imgProfile;

    final int GALLERY_REQUEST_CODE =177;

    PreferenceStorage preferenceStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        preferenceStorage = new PreferenceStorage(this);


        Button btnCreateAccount, btn_login;
        btnCreateAccount = findViewById(R.id.btn_create_account);
        btn_login = findViewById(R.id.btn_login);
        imgProfile = findViewById(R.id.Imgprofile);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPermission();
            }
        });

        inputconfirm = findViewById(R.id.inputconfirm);
        inputnumber = findViewById(R.id.inputnumber);
        inputname = findViewById(R.id.inputname);
        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateInputs()) {

                    String name = inputname.getText().toString();


                    Intent goToHome = new Intent(AuthenticationActivity.this, HomeActivity.class);

                    preferenceStorage.saveUserData(
                            name,
                            inputemail.getText().toString(),
                            inputnumber.getText().toString(),
                            inputpassword.getText().toString()
                    );

                    preferenceStorage.setUserLoggedIn(true);

                    startActivity(goToHome);
                     finish();

                }
//
//
            }
        });
    }

    private void showLoginDialog() {
        final BottomSheetDialog loginDialog = new BottomSheetDialog(this);
        loginDialog.setContentView(R.layout.login_dialog);
        TextInputEditText inputName = loginDialog.findViewById(R.id.input_login_username);
        TextInputEditText inputPassword = loginDialog.findViewById(R.id.input_login_password);
        Button loginBtn = loginDialog.findViewById(R.id.btn_login2);
        Button btnReset = loginDialog.findViewById(R.id.btn_reset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginDialog.dismiss();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (validateLoginInputs(inputName,inputPassword)){

                    if (preferenceStorage.authenticate(name,password)) {
                        preferenceStorage.setUserLoggedIn(true);

                        Intent intent = new Intent(AuthenticationActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(AuthenticationActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        loginDialog.show();
    }

    private boolean validateLoginInputs(TextInputEditText inputName, TextInputEditText inputPassword) {
        boolean result = true;
        if (inputName.getText().toString().trim().isEmpty()){
            inputName.setError("Please Enter A Name");
            result = false;
        }
        else if (inputPassword.getText().toString().trim().isEmpty()){
            inputPassword.setError("Please Enter A Password");
            result = false;
        }
        else {
            result = true;
        }
        return result;
    }


    public boolean validateInputs() {

        boolean response = false;

        if (inputname.getText().toString().trim().isEmpty()) {

            inputname.setError("Please Enter Your Name");
        } else if (inputemail.getText().toString().trim().isEmpty()) {
            inputemail.setError("Please Enter Your Email");
        } else if (!inputemail.getText().toString().trim().contains("@")) {
            inputemail.setError("Your Email Should Start With @");
        } else if (inputnumber.getText().toString().trim().isEmpty()) {
            inputnumber.setError("Please Enter Your Phone Number");
        } else if (inputpassword.getText().toString().isEmpty()) {
            inputpassword.setError("Please Enter Your Password");
        } else if (inputconfirm.getText().toString().isEmpty()) {
            inputconfirm.setError("Please Confirm Your Password");
        } else if (!inputpassword.getText().toString().trim().contentEquals(inputconfirm.getText().toString().trim())) {
            inputconfirm.setError("Your Passwords Do Not Match");
        } else {
            response = true;
        }

        return response;
    }

    public void verifyPermission() {

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (
                ContextCompat.checkSelfPermission(
                        this.getApplicationContext(), permissions[0])
                        == PackageManager.PERMISSION_GRANTED
        ) {
            pickImageFromGallery();
        }

       else{
            ActivityCompat.requestPermissions(
                    this, permissions, 134);
        }
    }

    private void pickImageFromGallery() {

        Intent pickFromGallery = new Intent(Intent.ACTION_PICK);
        pickFromGallery.setType("image/*");
        String[] mimeTypes = {
                "image/jpeg",
                "image/png",
                "image/svg",
        };

        try {
            startActivityForResult(pickFromGallery,GALLERY_REQUEST_CODE);
        }

        catch (ActivityNotFoundException e){
            //Emptiness
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            imgProfile.setImageURI(uri);
        }
    }
}