package com.samfieldhawb.imfiresultcalculator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.lecturer.LecturerHomeActivity;
import com.samfieldhawb.imfiresultcalculator.lecturer.LecturerLoginActivity;
import com.samfieldhawb.imfiresultcalculator.student.StudentActivity;
import com.samfieldhawb.imfiresultcalculator.student.StudentLoginActivity;

public class ChooseUserActivity extends AppCompatActivity {
    public static final String ROLE = "role";
    public static final String STUDENT = "Student";
    public static final String LECTURER = "LECTURER";
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null){
                    if(getCurrentUserRole().equals(STUDENT)){
                        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                    }else {
                        startActivity(new Intent(getApplicationContext(), LecturerHomeActivity.class));
                    }

                }
            }
        };
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void startStudentLogin(View view){
        startActivity(new Intent(this, StudentLoginActivity.class));
    }
    public void startLecturerLogin(View view){
        startActivity(new Intent(this, LecturerLoginActivity.class));
    }

    public String getCurrentUserRole(){
        final String[] role = {""};
        databaseReference.child("Users").child(ROLE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        role[0] = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return role[0];

    }
}
