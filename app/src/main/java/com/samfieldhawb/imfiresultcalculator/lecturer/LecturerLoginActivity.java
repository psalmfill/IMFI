package com.samfieldhawb.imfiresultcalculator.lecturer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LecturerLoginActivity extends AppCompatActivity {
    private EditText mEmailField, mPasswordField;
     Button mLoginButton;
    private TextView mErrorMessage;
    private DatabaseReference mDbRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(firebaseAuth.getCurrentUser() !=null){
                startLecturerHome();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_login);
        mEmailField = findViewById(R.id.email_field);
        mPasswordField = findViewById(R.id.password_field);
        mLoginButton = findViewById(R.id.login_button);
        mErrorMessage =  findViewById(R.id.error_message);
        mDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dologin();
            }
        });
    }

    public void dologin(){
        String email = mEmailField.getText().toString().trim();

        String password = mPasswordField.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mDbRef.child(mAuth.getUid()).child("role").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String role = dataSnapshot.getValue(String.class);
                            if(!role.isEmpty() && role.equals("Lecturer")){
                                startLecturerHome();
                            }else{
                                mErrorMessage.setText("Unrecognized Staff");
                                mErrorMessage.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    mErrorMessage.setText(task.getException().getMessage());
                    mErrorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void startLecturerHome(){
        startActivity(new Intent(getApplicationContext(),LecturerHomeActivity.class));
    }
    public void showLecturerRegister(View view){

        startActivity(new Intent(this,LecturerRegisterActivity.class));
    }
}
