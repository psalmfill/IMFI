package com.samfieldhawb.imfiresultcalculator.student;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.ResultDisplayAdapter;
import com.samfieldhawb.imfiresultcalculator.models.Course;

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
    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        mRecyclerView = view.findViewById(R.id.result_rv);
        mGpa = view.findViewById(R.id.gpa);
        mCourseList = new ArrayList<>();
//        addCourse();
        mDisplayAdapter = new ResultDisplayAdapter(getContext(),mCourseList);
        mRecyclerView.setAdapter(mDisplayAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mGpa.setText(String.format("%.2f",calculateGPA()));
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
}
