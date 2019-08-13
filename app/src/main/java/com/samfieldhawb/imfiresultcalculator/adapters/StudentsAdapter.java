package com.samfieldhawb.imfiresultcalculator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.helpers.OnClickListener;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentHolder> {
    Context mContext;
    List<User> mStudentList;
    OnClickListener listener;


    public StudentsAdapter(Context mContext, List<User> mStudentList, OnClickListener listener) {
        this.mContext = mContext;
        this.mStudentList = mStudentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StudentHolder(LayoutInflater.from(mContext).inflate(R.layout.single_student,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder studentHolder, int i) {
        User student = mStudentList.get(i);
        studentHolder.name.setText(student.getFirstName() + " " + student.getLastName());
        studentHolder.regNum.setText(student.getRegistrationNumber());
        studentHolder.faculty.setText(student.getFacultyCode().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return mStudentList !=null ? mStudentList.size():0;
    }

    public void setStudentsList(List<User> studentsList){
        mStudentList = studentsList;
        notifyDataSetChanged();
    }
    class StudentHolder extends RecyclerView.ViewHolder {
        TextView name,regNum,faculty;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            regNum = itemView.findViewById(R.id.regNum);
            faculty = itemView.findViewById(R.id.faculty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(mStudentList.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
