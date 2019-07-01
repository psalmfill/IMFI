package com.samfieldhawb.imfiresultcalculator.lecturer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.adapters.ResultInputAdapter;
import com.samfieldhawb.imfiresultcalculator.models.Course;

import java.util.ArrayList;
import java.util.List;

public class RecordScoreActivity extends AppCompatActivity  implements ResultInputAdapter.listerner{
    List<Course> mCourses;
    private ResultInputAdapter mInputAdapter;
    private RecyclerView mRecyclerView;
    private TextView mGpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_score);

        mCourses = new ArrayList<>();

        mInputAdapter = new ResultInputAdapter(this,mCourses,this);
        mRecyclerView = findViewById(R.id.result_input_rv);
        mGpa = findViewById(R.id.gpa);
        mRecyclerView.setAdapter(mInputAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public void onScoreChange(double gpa) {
        mGpa.setText(String.format("%.2f",gpa));
    }
}
