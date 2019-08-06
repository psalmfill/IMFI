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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment implements OnClickListener {
    RecyclerView levelRv;
    DepartmentsAdapter LevelAdapter;
    List<Department> LevelList;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    private String faculty, department;
    public LevelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        faculty  = getArguments().getString(LecturerHomeActivity.FACULTY);
        department  = getArguments().getString(LecturerHomeActivity.DEPARTMENT);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_department, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Levels");
        levelRv =  view.findViewById(R.id.department_rv);
        getData();
        LevelList = new ArrayList<>();
        LevelAdapter = new DepartmentsAdapter(getContext(), LevelList, this);
        levelRv.setAdapter(LevelAdapter);
        levelRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    public void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    LevelList.add(d.getValue(Department.class));
                }

                LevelAdapter.setDepartmentList(LevelList);
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
        intent.putExtra(LecturerHomeActivity.DEPARTMENT,department);
        intent.putExtra(LecturerHomeActivity.LEVEL,value);
        startActivity(intent);
    }
}
