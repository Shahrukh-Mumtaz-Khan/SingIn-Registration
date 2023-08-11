package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.registration.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private FirebaseAuth mAuth;

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        String email = mAuth.getCurrentUser().getEmail().toString();

        mBinding.emailTv.setText(email);
        initClickListener();




    }

    private void initClickListener() {
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
                Toast.makeText(MainActivity.this, "LogOut", Toast.LENGTH_SHORT).show();
            }
        });


    }
}