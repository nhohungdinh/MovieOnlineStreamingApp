package com.example.movieonlinestreamingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.movieonlinestreamingapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    Button btnSignout;
    private FirebaseAuth mAuth;
    private Intent loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnSignout = findViewById(R.id.btn_signout);
        mAuth = FirebaseAuth.getInstance();
        loginActivity = new Intent(this, LoginActivity.class);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(loginActivity);
                finish();
            }
        });
    }
}