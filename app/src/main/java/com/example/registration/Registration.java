package com.example.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {


     ActivityRegistrationBinding mBinding;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegistrationBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());
        fAuth = FirebaseAuth.getInstance();
        initClickListener();



    }

    private void initClickListener() {


        mBinding.mRegistrationBtn.setOnClickListener(v -> {

            String email = mBinding.mEmail.getText().toString();
            String password = mBinding.mPassword.getText().toString();
            String cPassword = mBinding.mConfirmPass.getText().toString();
            String fullname= mBinding.mFullName.getText().toString();




            Log.d("TAG",email+" "+password);
            if(!email.isEmpty() && !password.isEmpty() && !cPassword.isEmpty()){

                fAuth.createUserWithEmailAndPassword(email,password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){

                                   Toast.makeText(Registration.this, "User Registration Successfully ", Toast.LENGTH_SHORT).show();
                              mBinding.mEmail.setText("");
                              mBinding.mPassword.setText("");

                               }
                               else {
                                   Toast.makeText(Registration.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                               }
                                }
                            });



                            Log.d("TAG","Success");
                           startActivity(new Intent(Registration.this,SignIn.class));
                            finish();
                        }

                    }
                }).addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();


                }).addOnFailureListener(e -> {
                    Log.d("TAG",e.toString());
                    Toast.makeText(this, "All Ready Registered", Toast.LENGTH_SHORT).show();


                });


            }
            else{

                Toast.makeText(this, "Please enter Your Details", Toast.LENGTH_SHORT).show();

            }



                    });



mBinding.mLoginBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(Registration.this,SignIn.class));
        finish();
    }
});
    }

}

