package com.samfieldhawb.imfiresultcalculator.lecturer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.StudentsAdapter;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {
    RecyclerView studentsRv;
    StudentsAdapter studentsAdapter;
    List<User> studentList;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_student, container, false);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("Users");
//        databaseReference.orderByChild("department/short_code").equalTo();
    }

}
