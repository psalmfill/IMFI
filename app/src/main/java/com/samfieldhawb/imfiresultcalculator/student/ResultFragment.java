package com.samfieldhawb.imfiresultcalculator.student;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.ResultDisplayAdapter;
import com.samfieldhawb.imfiresultcalculator.models.Course;
import com.samfieldhawb.imfiresultcalculator.models.Level;
import com.samfieldhawb.imfiresultcalculator.models.Semester;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ResultDisplayAdapter mDisplayAdapter;
    private List<Course> mCourseList;
    private TextView mGpa;
    ValueEventListener valueEventListener;


    Spinner mLevelSp;
    Spinner mSemesterSp;
    List<Level> mLevels;
    List<Semester> mSemesters;
    ArrayAdapter mSemesterAdapter;
    ArrayAdapter mLevelAdapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference getCourse ;
    User user;
    String userId = "";
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            userId = firebaseAuth.getUid();
            if (userId !=null) {
                if (!userId.equals("")) {
                    requestUser();
                }
            } else {
                startActivity(new Intent(getContext(), StudentLoginActivity.class));
            }
        }
    };

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        mAuth.addAuthStateListener(authListener);

        mLevels = new ArrayList<>();
        mSemesters = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.result_rv);
        mGpa = view.findViewById(R.id.gpa);
        mCourseList = new ArrayList<>();
        mLevelSp = view.findViewById(R.id.level_sp);

        mSemesterSp = view.findViewById(R.id.semester_sp);
//        addCourse();
        mDisplayAdapter = new ResultDisplayAdapter(getContext(),mCourseList);
        mRecyclerView.setAdapter(mDisplayAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mGpa.setText(String.format("%.2f",calculateGPA()));
        databaseReference.child("Levels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot lv : dataSnapshot.getChildren()){
                    mLevels.add(lv.getValue(Level.class));
                }

                mLevelAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mLevels);
                mLevelSp.setAdapter(mLevelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child("Semesters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sm : dataSnapshot.getChildren()){
                    mSemesters.add(sm.getValue(Semester.class));
                }
                mSemesterAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mSemesters);
                mSemesterSp.setAdapter(mSemesterAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mLevelSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(user != null){
                    addCourse(user.getFacultyCode().toLowerCase(), user.getDepartmentCode().toLowerCase(),
                            getLevelId(mLevelSp.getSelectedItem().toString()),
                            getSemesterId(mSemesterSp.getSelectedItem().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSemesterSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(user != null){
                    addCourse(user.getFacultyCode().toLowerCase(), user.getDepartmentCode().toLowerCase(),
                            getLevelId(mLevelSp.getSelectedItem().toString()),
                            getSemesterId(mSemesterSp.getSelectedItem().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

//    public void addCourse(){
//        mCourseList.add(new Course("GRE 111","Engineering Drawing",1,80));
//        mCourseList.add(new Course("MTH 111","Mathematics",4,50));
//        mCourseList.add(new Course("GST 111","Use of English",2,70));
//        mCourseList.add(new Course("Gst 112","Philosophy and Human Existence",2,85));
//        mCourseList.add(new Course("CHM 111","Chemistry",3,90));
//        mCourseList.add(new Course("CHM 112","Practical Chemistry",1,53));
//        mCourseList.add(new Course("PHY 111","Physics",3,95));
//        mCourseList.add(new Course("PHY 112","Practical Physics",1,70));
//    }
public void addCourse(String facultyCode,String departmentCode,String level,String semester) {

    valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mCourseList.clear();
            for (DataSnapshot course : dataSnapshot.getChildren())
                mCourseList.add(course.getValue(Course.class));
            if(mCourseList.size() ==0){
                Toast.makeText(getContext(),"No result Available",Toast.LENGTH_LONG).show();
            }
            mDisplayAdapter.setCourses(mCourseList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    if (user != null) {
        getCourse = databaseReference.child("Results").child(
                facultyCode
        )
                .child(
                        departmentCode
                )
                .child(
                        level
                )
                .child(
                        semester
                ).child(userId);
        getCourse.addListenerForSingleValueEvent(valueEventListener);

    }
}

    private void requestUser() {
        databaseReference.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public double calculateGPA(){
        int totalUnit = 0;
        float totalGrade = 0;
        for(Course course:mCourseList){
            totalUnit +=course.getCredit_unit();
            totalGrade +=  getGradeValue(course.getGrade()) * course.getCredit_unit();
        }
        return totalGrade/totalUnit;
    }
    public double getGradeValue(String grade){
        switch (grade){
            case "A":
                return 4;
            case "AB":
                return 3.5;
            case "B":
                return 3.25;
            case "BC":
                return 3;
            case "C":
                return 2.75;
            case "CD":
                return 2.5;
            case "D":
                return 2.25;
            case "E":
                return 2;
            default:
                return 0;
        }
    }

    public String getLevelId(String name)
    {
        for(Level level: mLevels){
            if (level.getName().equals(name)){
                return level.getShort_code().toLowerCase();

            }
        }
        return null;
    }

    public String getSemesterId(String name)
    {
        for(Semester semester: mSemesters){
            if (semester.getName().equals(name)){
                return semester.getShort_code().toLowerCase();
            }
        }
        return null;
    }

}
