package com.samfieldhawb.imfiresultcalculator.lecturer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.ResultInputAdapter;
import com.samfieldhawb.imfiresultcalculator.models.Course;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.ArrayList;
import java.util.List;

public class RecordScoreActivity extends AppCompatActivity  implements ResultInputAdapter.listerner{
    List<Course> mCourses;
    private ResultInputAdapter mInputAdapter;
    private RecyclerView mRecyclerView;
    private TextView mGpa,studentName, studentDepartment, studentFaculty,studentReg;
    private Button upload;
    private String faculty, department, level, semester,student;
    public static String STUDENT_ID = "student_id";
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_score);

        Intent intent = getIntent();
        mCourses = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mInputAdapter = new ResultInputAdapter(this,mCourses,this);

        if(intent != null){
            faculty = intent.getStringExtra(LecturerHomeActivity.FACULTY);
            department = intent.getStringExtra(LecturerHomeActivity.DEPARTMENT);
            level = intent.getStringExtra(LecturerHomeActivity.LEVEL);
            semester = intent.getStringExtra(LecturerHomeActivity.SEMESTER);
            student = intent.getStringExtra(STUDENT_ID);
            checkResult();
        }
        mRecyclerView = findViewById(R.id.result_input_rv);
        upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpload();
            }
        });
        mGpa = findViewById(R.id.gpa);
        studentName = findViewById(R.id.name);
        studentDepartment = findViewById(R.id.department);
        studentFaculty = findViewById(R.id.faculty);
        studentReg = findViewById(R.id.regNum);
        mRecyclerView.setAdapter(mInputAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        User studentObj = getStudent();
        if(studentObj != null) {
            setUpDetail(studentObj);
        }
    }

    private void setUpDetail(User studentObj) {
        if(studentObj == null) return;
        studentName.setText(String.format("%s %s%s", studentObj.getFirstName(), studentObj.getMiddleName(), studentObj.getLastName()));
        studentFaculty.setText(studentObj.getFacultyCode().toUpperCase());
        studentDepartment.setText(studentObj.getDepartmentCode().toUpperCase());
        studentReg.setText(studentObj.getRegistrationNumber());
    }


    public void fetchCourses(){
        databaseReference.child("RegisteredCourses")
                .child(faculty)
                .child(department)
                .child(level)
                .child(semester)
                .child(student)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d: dataSnapshot.getChildren()){
                            mCourses.add(d.getValue(Course.class));
                        }

                        mInputAdapter.setCourses(mCourses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public User getStudent(){
        final User[] studentObj = {null};
        databaseReference.child("Users")
                .child(student).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentObj[0] = dataSnapshot.getValue(User.class);
                setUpDetail(studentObj[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return studentObj[0];
    }

    public void checkResult(){
        databaseReference.child("Results")
                .child(faculty)
                .child(department)
                .child(level)
                .child(semester)
                .child(student)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot d: dataSnapshot.getChildren()){
                            mCourses.add(d.getValue(Course.class));
                        }
                        if(dataSnapshot.exists()){
                        mInputAdapter.setCourses(mCourses);}
                        else {
                            fetchCourses();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onScoreChange(double gpa) {
        mGpa.setText(String.format("%.2f",gpa));
    }

    public void doUpload(){
        databaseReference.child("Results")
                .child(faculty)
                .child(department)
                .child(level)
                .child(semester)
                .child(student)
                .setValue(mCourses).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Result Uploaded",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
