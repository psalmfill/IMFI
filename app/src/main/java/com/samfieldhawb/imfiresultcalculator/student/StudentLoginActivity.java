package com.samfieldhawb.imfiresultcalculator.student;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText mEmailField, mPasswordField;
    private TextView mErrorMessage;
    Button mLoginButton;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    private DatabaseReference mDbRef;

    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(firebaseAuth.getCurrentUser() !=null){
                startStudentHome();
            }
        }
    };

    private void startStudentHome() {
        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        mEmailField = findViewById(R.id.email_field);
        mPasswordField = findViewById(R.id.password_field);
        mErrorMessage = findViewById(R.id.error_message);
        mLoginButton = findViewById(R.id.login_button);
        mProgressBar = findViewById(R.id.progressBar);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);
        mDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void doLogin(){
        mProgressBar.setVisibility(View.VISIBLE);
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final String[] role = new String[1];

                    mDbRef.child(mAuth.getUid()).child("role").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            role[0] = dataSnapshot.getValue(String.class);
                            if(role[0] !=null ){
                                if(!role[0].isEmpty() && role[0].equals("student")){
                                    startStudentHome();

                                }
                                else {
                                    mErrorMessage.setText("You are not a Registered Student");
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    mProgressBar.setVisibility(View.GONE);

                }else {
                    mErrorMessage.setText(task.getException().getMessage());
                    mProgressBar.setVisibility(View.GONE);

                }
            }
        });


    }
}
