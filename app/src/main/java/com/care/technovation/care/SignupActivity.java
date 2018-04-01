package com.care.technovation.care;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.SignInButton);
        btnSignUp = (Button) findViewById(R.id.SignUpButton);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputName = (EditText) findViewById(R.id.name);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.SignInButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.SignUpButton) {
            createUser();
        }
    }

    protected void createUser() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        final String name = inputName.getText().toString().trim();

        final boolean success = false;

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Name field is blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email field is blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Password field is blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference ref = database.getReference("Users").push();
                            ref.setValue(currentUser.getUid());
                            ref = ref.child("Name");
                            ref.setValue(name);

                            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}