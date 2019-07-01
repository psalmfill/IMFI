package com.samfieldhawb.imfiresultcalculator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.models.Course;

import java.util.List;

public class ResultDisplayAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Course> mCourses;

    public ResultDisplayAdapter(Context context, List<Course> courses) {
        mContext = context;
        mCourses = courses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_course_result_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Course course = mCourses.get(i);
        ((ViewHolder) viewHolder).title.setText(course.getTitle());
        ((ViewHolder) viewHolder).code.setText(course.getCode());
        ((ViewHolder) viewHolder).unit.setText(String.valueOf(course.getCredit_unit()));
        ((ViewHolder) viewHolder).score.setText(String.valueOf(course.getScore()));
        ((ViewHolder) viewHolder).grade.setText(course.getGrade());
    }

    @Override
    public int getItemCount() {
        return mCourses != null?mCourses.size():0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView code,title, unit,score,grade;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.course_code);
            title = itemView.findViewById(R.id.course_title);
            unit = itemView.findViewById(R.id.credit_unit);
            grade = itemView.findViewById(R.id.grade);
            score = itemView.findViewById(R.id.score);
        }
    }
}
