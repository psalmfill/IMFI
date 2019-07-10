package com.samfieldhawb.imfiresultcalculator.student;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.CoursesAdapter;
import com.samfieldhawb.imfiresultcalculator.models.Course;
import com.samfieldhawb.imfiresultcalculator.models.Level;
import com.samfieldhawb.imfiresultcalculator.models.Semester;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCoursesFragment extends Fragment implements CoursesAdapter.listerner {
    RecyclerView mRecyclerView;
    TextView mCourseCount,mUnitCount;
    Spinner mLevelSp;
    Spinner mSemesterSp;
    Button mRegisterCourse;
    CoursesAdapter mAdapter;
    List<Course> mCourses;
    List<Course> selectedCourses;
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

    ValueEventListener valueEventListener;


    public RegisterCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_courses, container, false);
        mAuth.addAuthStateListener(authListener);
        mCourses = new ArrayList<>();
        selectedCourses = new ArrayList<>();
        mLevels = new ArrayList<>();
        mSemesters = new ArrayList<>();
        mCourseCount = view.findViewById(R.id.course_total);
        mUnitCount = view.findViewById(R.id.unit_total);
        mLevelSp = view.findViewById(R.id.level_sp);

        mAdapter = new CoursesAdapter(getContext(),mCourses,this);
        mSemesterSp = view.findViewById(R.id.semester_sp);
        mRecyclerView = view.findViewById(R.id.course_reg_view);
        mRegisterCourse = view.findViewById(R.id.register_courses);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRegisterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegisterCourses();
            }
        });
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
                addCourse(user.getFaculty_code().toLowerCase(), user.getDepartment_code().toLowerCase(),
                        getLevelId(mLevelSp.getSelectedItem().toString()),
                        getSemesterId(mSemesterSp.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSemesterSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addCourse(user.getFaculty_code().toLowerCase(), user.getDepartment_code().toLowerCase(),
                        getLevelId(mLevelSp.getSelectedItem().toString()),
                        getSemesterId(mSemesterSp.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void setupRv(List<Course> courses) {
        mAdapter.setCourses(courses);
        getCourse.removeEventListener(valueEventListener);
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


    public void addCourse(String facultyCode,String departmentCode,String level,String semester){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCourses.clear();
                for(DataSnapshot course : dataSnapshot.getChildren())
                    mCourses.add(course.getValue(Course.class));
                setupRv(mCourses);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if(user !=null){
            getCourse =  databaseReference.child("Courses").child(
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
                    );
          getCourse.addListenerForSingleValueEvent(valueEventListener);

        }

    }

    @Override
    public void onCourseChange(Course course, boolean isChecked) {

        if(isChecked){
            selectedCourses.add(course);
        }else {
            selectedCourses.remove(course);
        }
        updateCount();
    }

    public void updateCount(){
        mCourseCount.setText(String.valueOf(selectedCourses.size()));
        int totalUnit = 0;
        for(Course course: selectedCourses){
            totalUnit += course.getCredit_unit();
        }
        mUnitCount.setText(String.valueOf(totalUnit));
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

    public void doRegisterCourses (){
        DatabaseReference registerCourseRef = databaseReference.child("RegisteredCourses");
        DatabaseReference mCourseRefer = registerCourseRef.child(user.getFaculty_code().toLowerCase())
                .child(user.getDepartment_code().toLowerCase())
                .child(getLevelId(mLevelSp.getSelectedItem().toString()))
                .child(getSemesterId(mSemesterSp.getSelectedItem().toString()))
                .child(userId);
        mCourseRefer.setValue(setUpCoursesForReg()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(),"Courses Registration Successful",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Map setUpCoursesForReg (){

        Map map=new HashMap();  ;

        for (Course course : selectedCourses){
           CourseSpecial courseSpecial = new CourseSpecial(course.getCode(),course.getTitle(),course.getCredit_unit());
           map.put(course.getCode().replace(' ','_').toLowerCase(),courseSpecial);
        }

        return map;
    }

    class CourseSpecial {
        private String code;
        private String title;
        private int credit_unit;

        public CourseSpecial(String code, String title, int credit_unit) {
            this.code = code;
            this.title = title;
            this.credit_unit = credit_unit;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCredit_unit() {
            return credit_unit;
        }

        public void setCredit_unit(int credit_unit) {
            this.credit_unit = credit_unit;
        }
    }
}
