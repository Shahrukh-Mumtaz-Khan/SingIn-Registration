package com.example.registration;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class SignIn extends AppCompatActivity {

    EditText mEmail,mpassword;
    Button mLoginBtn;
    RelativeLayout signGoogle;
    TextView mCreateBtn,forgetTextLink;
    FirebaseAuth fAuth;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        mEmail=findViewById(R.id.mEmail);
        mpassword=findViewById(R.id.mpassword);
        mLoginBtn=findViewById(R.id.mLoginBtn);
        mCreateBtn=findViewById(R.id.mCreateBtn);
        forgetTextLink=findViewById(R.id.forgetTextLink);

        signGoogle=findViewById(R.id.signGoogle);

        signGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });












        fAuth= FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
        }

        mLoginBtn.setOnClickListener(v -> {



            String email=mEmail.getText().toString();
            String password=mpassword.getText().toString();


            if (TextUtils.isEmpty(email)){
                mEmail.setError("Email is Required");
                return;
            }
            else if (TextUtils.isEmpty(password)){
                mpassword.setError("Password is Required");
                return;
            }
            else if (password.length()<6) {
                mpassword.setError("Password Must b >= 6 Characters");
            }

                else{

                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){

                               // Send Verification Mail
                            ////////////////////////////////////////////////////

                            if (fAuth.getCurrentUser().isEmailVerified()){

                                startActivity(new Intent(SignIn.this,MainActivity.class));

                            }else {
                                Toast.makeText(this, "Check Your Mail Box", Toast.LENGTH_SHORT).show();
                            }

                                startActivitySecound();
                                Toast.makeText(SignIn.this, "Successfully", Toast.LENGTH_SHORT).show();



                                ////////////////////////////////////////////////////////////////////////////


                        }else {
                            Toast.makeText(SignIn.this, "Check Your Email & Password ", Toast.LENGTH_SHORT).show();

                        }

                    });
                }


        });
        //forget passsword Button
        forgetTextLink.setOnClickListener(v -> {
            final EditText resetmail=new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());

            passwordResetDialog.setTitle("Reset Password");
            passwordResetDialog.setMessage("Enter Your Email Received");
            passwordResetDialog.setView(resetmail);



            passwordResetDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
              //Extract the Email and Send Reset Link
                    String mail=resetmail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignIn.this, "Reset Link", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            passwordResetDialog.setNegativeButton("Close", (dialog, which) -> {
                //Close the Dialog


            });
            passwordResetDialog.create().show();
        });


        //New Acccount Registration Button
        mCreateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Registration.class)));

    }
    private void startActivitySecound(){
        Intent intent=new Intent(SignIn.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
