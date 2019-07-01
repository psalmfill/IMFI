package com.samfieldhawb.imfiresultcalculator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentHolder> {
    Context mContext;
    List<User> mStudentList;

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StudentHolder(LayoutInflater.from(mContext).inflate(R.layout.student_single,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder studentHolder, int i) {
        studentHolder.name.setText(mStudentList.get(i).getFirstName() + " " + mStudentList.get(i));
        studentHolder.regNum.setText(mStudentList.get(i).getRegistrationNumber());
    }

    @Override
    public int getItemCount() {
        return mStudentList !=null ? mStudentList.size():0;
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        TextView name,regNum;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            regNum = itemView.findViewById(R.id.regNum);
        }
    }
}
