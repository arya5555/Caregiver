package com.care.technovation.care;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignUp, btnSignIn, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignUp = (Button) findViewById(R.id.SignUpButton);
        btnSignIn = (Button) findViewById(R.id.SignInButton);
        btnReset = (Button) findViewById(R.id.ResetPassButton);

        auth = FirebaseAuth.getInstance();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.SignInButton) {
            signIn();
        } else if (v.getId() == R.id.SignUpButton) {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        } else if (v.getId() == R.id.ResetPassButton) {
            Toast.makeText(getApplicationContext(), "This doesn't link anywhere yet", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        }
    }

    public void signIn() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}