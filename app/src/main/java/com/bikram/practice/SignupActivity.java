package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    TextView tnc, redirect, guest;
    EditText email, fullname, mobile, password, confirmpass;
    Button signup;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //find view by ids
        {
            tnc = findViewById(R.id.tnc);
            redirect = findViewById(R.id.redirect);
            signup = findViewById(R.id.signup_continue);
            email = findViewById(R.id.txt_email);
            fullname = findViewById(R.id.txt_fname);
            mobile = findViewById(R.id.txt_mobileno);
            password = findViewById(R.id.txt_password);
            guest = findViewById(R.id.guest);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String text2 = "<font color=#00000>By Signing up, you're agreeing to our </font> <font color=#FF0066>Terms & Conditions </font><font color=#00000>and </font> <font color=#FF0066>Privacy Policy</font>";
        tnc.setText(Html.fromHtml(text2));
        String text3 = "<font color=#00000>Joined Before?</font> <font color=#FF0066> Login</font>";
        redirect.setText(Html.fromHtml(text3));
        mAuth = FirebaseAuth.getInstance();
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emailempty = false;
                boolean emailvalid = true;
                boolean fullnameempty = false;
                boolean mobileempty = false;
                boolean phonevalid = true;
                boolean passvalid = true;

                if (email.getText().toString().equals("")) {
                    email.setError("Email address empty field", AppCompatResources.getDrawable(SignupActivity.this,
                            R.drawable.ic_baseline_error_24));
                    email.requestFocus();
                    emailempty = true;
                    return;
//                    signup.setEnabled(false);
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Please provide valid email", AppCompatResources.getDrawable(SignupActivity.this,
                            R.drawable.ic_baseline_error_24));
                    email.requestFocus();
                    emailvalid = false;
                    return;
                }

                if (fullname.getText().toString().isEmpty()) {
                    fullname.setError("Please enter name", AppCompatResources.getDrawable(SignupActivity.this
                            , R.drawable.ic_baseline_error_24));
                    fullname.requestFocus();
                    fullnameempty = true;
                    return;
                }
                if (mobile.getText().toString().isEmpty()) {
                    mobile.setError("Please enter mobile number", AppCompatResources.getDrawable(SignupActivity.this
                            , R.drawable.ic_baseline_error_24));
                    mobile.requestFocus();
                    mobileempty = true;
//                    signup.setEnabled(false);
                    return;
                }
                if (!Pattern.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", mobile.getText().toString())) {
                    mobile.setError("Please enter valid phone number", AppCompatResources.getDrawable(SignupActivity.this
                            , R.drawable.ic_baseline_error_24));
                    mobile.requestFocus();
                    phonevalid = false;
                    return;
//                    signup.setEnabled(false);
                }

                if (!Pattern.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$", password.getText().toString())) {
                    password.setError("The password policy is:\n" +
                            "1.At least 8 chars\n" +
                            "2.Contains at least one digit\n" +
                            "3.Contains at least one lower alpha char and one upper alpha char\n" +
                            "4.Contains at least one char within a set of special chars (@#%$^ etc.)\n" +
                            "5.Does not contain space, tab, etc.");
                    password.requestFocus();
                    passvalid = false;
                    return;
                }
                if (!emailempty && emailvalid && !fullnameempty && !mobileempty && phonevalid && passvalid) {
                    boolean isnew = checkEmailIfExist(email.getText().toString());
                    if (isnew) {
                        Toast.makeText(SignupActivity.this, "already esist", Toast.LENGTH_SHORT).show();
                    } else {
                        createUser(email.getText().toString(), password.getText().toString());
                    }
                }
            }
        });
        //guest sign in
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Signed in as guest\nAutomatic logout if app is closed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private boolean checkEmailIfExist(String email) {
        final boolean[] isNewUser = {false};
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                isNewUser[0] = task.getResult().getSignInMethods().isEmpty();
                if (isNewUser[0]) {
                    Log.e("TAG", "Is New User!");
                } else {
                    Log.e("TAG", "Is Old User!");
                }
            }
        });
        return isNewUser[0];
    }
    private void createUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                }, 10000);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finishAffinity();
                                Toast.makeText(getApplicationContext(), "Check mail inside spam folder", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Email", email);
                    hashMap.put("Name", fullname.getText().toString());
                    hashMap.put("PhoneNo", mobile.getText().toString());
                    hashMap.put("Photo","");
                    databaseReference.setValue(hashMap);
                } else {
                    Toast.makeText(SignupActivity.this, String.valueOf(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}