package com.care.technovation.care;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp, btnInfo;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Log.d("debug1", "User signed in");
            startActivity(new Intent(this, HomeActivity.class));
       }

        btnSignIn = (Button) findViewById(R.id.SignInButton);
        btnSignUp = (Button) findViewById(R.id.SignUpButton);
        btnInfo  = (Button) findViewById(R.id.InfoButton);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.InfoButton) {
            Toast.makeText(getApplicationContext(), "This doesn't link anywhere yet", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.SignInButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.SignUpButton) {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
    }
}
