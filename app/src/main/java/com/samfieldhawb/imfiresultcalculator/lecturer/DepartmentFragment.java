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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.DepartmentsAdapter;
import com.samfieldhawb.imfiresultcalculator.helpers.OnClickListener;
import com.samfieldhawb.imfiresultcalculator.models.Department;
import com.samfieldhawb.imfiresultcalculator.models.Faculty;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment implements OnClickListener {
    RecyclerView departmmentsRv;
    DepartmentsAdapter departmentsAdapter;
    List<Department> departmentList;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    private String faculty, department;
    public DepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        faculty  = getArguments().getString(LecturerHomeActivity.FACULTY);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_department, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Departments").child(faculty);
        departmmentsRv =  view.findViewById(R.id.department_rv);
        getData();
        departmentList = new ArrayList<>();
        departmentsAdapter = new DepartmentsAdapter(getContext(), departmentList, this);
        departmmentsRv.setAdapter(departmentsAdapter);
        departmmentsRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    public void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    departmentList.add(d.getValue(Department.class));
                }

                departmentsAdapter.setDepartmentList(departmentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(String value) {
        Intent intent = new Intent(getContext(), LecturerHomeActivity.class);
        intent.putExtra(LecturerHomeActivity.FACULTY,faculty);
        intent.putExtra(LecturerHomeActivity.DEPARTMENT,value);
        startActivity(intent);
    }
}
