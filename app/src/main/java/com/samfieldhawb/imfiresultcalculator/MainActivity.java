package com.samfieldhawb.imfiresultcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samfieldhawb.imfiresultcalculator.lecturer.LecturerHomeActivity;
import com.samfieldhawb.imfiresultcalculator.lecturer.LecturerLoginActivity;
import com.samfieldhawb.imfiresultcalculator.lecturer.RecordScoreActivity;
import com.samfieldhawb.imfiresultcalculator.student.StudentActivity;
import com.samfieldhawb.imfiresultcalculator.student.StudentLoginActivity;
import com.samfieldhawb.imfiresultcalculator.student.StudentRegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startStudentReg(View view){
        startAScreen(StudentRegisterActivity.class);
    }

    private void startAScreen(Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    public void startStudentHome(View view){
        startAScreen(HomeActivity.class);

    }

    public void startResultInput(View view){
        startAScreen(RecordScoreActivity.class);

    }
    public void startStudentLogin(View view){
        startAScreen(StudentLoginActivity.class);
    }

    public  void startLecturerLogin(View view){
        startAScreen(LecturerLoginActivity.class);
    }

    public  void startUserChooser(View view){
        startAScreen(ChooseUserActivity.class);
    }
    public  void startLecturerHome(View view){
        startAScreen(LecturerHomeActivity.class);
    }
    public  void startStudent(View view){
        startAScreen(StudentActivity.class);
    }

}
