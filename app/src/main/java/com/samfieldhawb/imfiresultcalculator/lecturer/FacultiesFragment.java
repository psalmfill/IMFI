package com.samfieldhawb.imfiresultcalculator.lecturer;


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
import com.samfieldhawb.imfiresultcalculator.adapters.FacultiesAdapter;
import com.samfieldhawb.imfiresultcalculator.models.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacultiesFragment extends Fragment {

    RecyclerView facultyRv;
    FacultiesAdapter facultiesAdapter;
    List<Faculty> facultyList;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;


    public FacultiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculties, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Faculties");
        facultyRv =  view.findViewById(R.id.faculty_rv);
        getData();
        facultyList = new ArrayList<>();
        facultiesAdapter = new FacultiesAdapter(getContext(), facultyList);
        facultyRv.setAdapter(facultiesAdapter);
        facultyRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    facultyList.add(d.getValue(Faculty.class));
                }

                facultiesAdapter.setmFaculties(facultyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
