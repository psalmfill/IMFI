package com.samfieldhawb.imfiresultcalculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.samfieldhawb.imfiresultcalculator.student.RegisterCoursesFragment;
import com.samfieldhawb.imfiresultcalculator.student.ResultFragment;

public class HomeActivity extends AppCompatActivity {
    private int mFragementContainerid;
    Spinner mLevel,mSemester;
    Fragment fragment;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFragementContainerid = R.id.fragment_container;
         fm = getSupportFragmentManager();
        fragment= fm.findFragmentById(R.id.fragment_container);


    }
    public void registerCourses(View view){
//        if (fragment == null){
            fragment = new RegisterCoursesFragment();
            fm.beginTransaction().replace(mFragementContainerid,fragment).commit();
//        }
    }
    public void checkResult(View view){
//        if (fragment == null){
            fragment = new ResultFragment();
            fm.beginTransaction().replace(mFragementContainerid,fragment).commit();
//        }
    }
}
