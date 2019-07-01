package com.samfieldhawb.imfiresultcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samfieldhawb.imfiresultcalculator.lecturer.LecturerLoginActivity;
import com.samfieldhawb.imfiresultcalculator.student.StudentLoginActivity;

public class ChooseUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
    }

    public void startStudentLogin(View view){
        startActivity(new Intent(this, StudentLoginActivity.class));
    }
    public void startLecturerLogin(View view){
        startActivity(new Intent(this, LecturerLoginActivity.class));
    }
}
