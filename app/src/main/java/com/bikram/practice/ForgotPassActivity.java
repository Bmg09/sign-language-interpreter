package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText forgotPass;
    Button submitinfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotPass = findViewById(R.id.forgotpass_inactivity);
        submitinfor = findViewById(R.id.submit_email);
        submitinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emailempty =false, emavalid = true;
                if (forgotPass.getText().toString().equals("")) {
                    forgotPass.setError("Email address empty field", AppCompatResources.getDrawable(ForgotPassActivity.this,
                            R.drawable.ic_baseline_error_24));
                    forgotPass.requestFocus();
                    emailempty = true;
                    return;
//                    signup.setEnabled(false);
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(forgotPass.getText().toString()).matches()) {
                    forgotPass.setError("Please provide valid email", AppCompatResources.getDrawable(ForgotPassActivity.this,
                            R.drawable.ic_baseline_error_24));
                    forgotPass.requestFocus();
                    emavalid = false;
                    return;
                }
                if(!emailempty && emavalid){
                    resetPass();
                }
            }
        });

    }
    public void resetPass(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(forgotPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassActivity.this, "Email sent please check spam folder if not in inbox.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPassActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}