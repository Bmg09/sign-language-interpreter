package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    TextView redirecttosignup, forgotpass;
    EditText email, password;
    Button login, google_login;
    FirebaseAuth mAuth;
    public static GoogleSignInClient googleSignInClient;
    static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotpass = findViewById(R.id.forgotpass);
        redirecttosignup = findViewById(R.id.redirect_to_signup);
        email = findViewById(R.id.txt_email_login);
        password = findViewById(R.id.txt_password_login);
        login = findViewById(R.id.login_btn);
        google_login = findViewById(R.id.button4);
        mAuth = FirebaseAuth.getInstance();
        String text3 = "<font color=#00000>New to app</font> <font color=#FF0066> Register</font>";
        redirecttosignup.setText(Html.fromHtml(text3));
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
        redirecttosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finishAffinity();
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emailempty = false, passempty = false;

                if (email.getText().toString().equals("")) {
                    email.setError("Email address empty field", AppCompatResources.getDrawable(LoginActivity.this,
                            R.drawable.ic_baseline_error_24));
                    email.requestFocus();
                    emailempty = true;
                    return;
                }

                if (password.getText().toString().isEmpty()) {
                    password.setError("Password field empty", AppCompatResources.getDrawable(LoginActivity.this,
                            R.drawable.ic_baseline_error_24));
                    password.requestFocus();
                    passempty = true;
                    return;
                }
                if (!emailempty && !passempty) {
                    String emailstr = email.getText().toString();
                    String passstr = password.getText().toString();
                    mAuth.signInWithEmailAndPassword(emailstr, passstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finishAffinity();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                account.getPhotoUrl();
                fireBaseAuthWithGoogleAccount(account);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fireBaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String uid = firebaseUser.getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GoogleUser").child(uid);
                HashMap<String, String> hashMap = new HashMap<>();
                for (UserInfo userInfo : firebaseUser.getProviderData()) {
                    if (userInfo.getProviderId().equals("google.com")) {
                        Log.d("TAG", "User is signed in with Google");
                        hashMap.put("Email", userInfo.getEmail());
                        hashMap.put("Name", userInfo.getDisplayName());
                        hashMap.put("Photo", String.valueOf(userInfo.getPhotoUrl()));
                        hashMap.put("Phoneno", userInfo.getPhoneNumber());

                    }
                }
                databaseReference.setValue(hashMap);
                if (authResult.getAdditionalUserInfo().isNewUser()) {
                    Toast.makeText(LoginActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "existing user", Toast.LENGTH_SHORT).show();
                    //existing user
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finishAffinity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser m = mAuth.getCurrentUser();
        if (m != null && m.isEmailVerified()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finishAffinity();
        }
    }
}
