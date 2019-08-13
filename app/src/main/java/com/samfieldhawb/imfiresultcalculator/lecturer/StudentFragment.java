package com.samfieldhawb.imfiresultcalculator.lecturer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.DepartmentsAdapter;
import com.samfieldhawb.imfiresultcalculator.adapters.StudentsAdapter;
import com.samfieldhawb.imfiresultcalculator.helpers.OnClickListener;
import com.samfieldhawb.imfiresultcalculator.models.Department;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements OnClickListener {
    RecyclerView studentsRv;
    StudentsAdapter studentsAdapter;
    List<User> studentsList;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    private String faculty, department,level, semester;
    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        faculty  = getArguments().getString(LecturerHomeActivity.FACULTY);
        department  = getArguments().getString(LecturerHomeActivity.DEPARTMENT);
        level  = getArguments().getString(LecturerHomeActivity.LEVEL);
        semester  = getArguments().getString(LecturerHomeActivity.SEMESTER);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_department, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        studentsRv =  view.findViewById(R.id.department_rv);
        getData();
        studentsList = new ArrayList<>();
        studentsAdapter = new StudentsAdapter(getContext(), studentsList, this);
        studentsRv.setAdapter(studentsAdapter);
        studentsRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    public void getData() {
        databaseReference.orderByChild("departmentCode").equalTo(department).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    studentsList.add(d.getValue(User.class));
                }

                studentsAdapter.setStudentsList(studentsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(String value) {
        Intent intent = new Intent(getContext(), RecordScoreActivity.class);
        intent.putExtra(LecturerHomeActivity.FACULTY,faculty);
        intent.putExtra(LecturerHomeActivity.DEPARTMENT,department);
        intent.putExtra(LecturerHomeActivity.LEVEL,level);
        intent.putExtra(LecturerHomeActivity.SEMESTER,semester);
        intent.putExtra(RecordScoreActivity.STUDENT_ID,value);
        startActivity(intent);

    }
}
