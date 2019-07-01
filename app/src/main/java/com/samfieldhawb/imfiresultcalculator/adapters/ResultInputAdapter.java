package com.samfieldhawb.imfiresultcalculator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.models.Course;

import java.util.List;

public class ResultInputAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Course> mCourses;
    private listerner mListerner;
    private double mGpa = 0;
    public interface listerner {
        void onScoreChange(double gpa);

    }
    public ResultInputAdapter(Context context, List<Course> courses , listerner listerner) {
        mContext = context;
        mCourses = courses;
        mListerner = listerner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CourseHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.single_course_result_input_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Course course = mCourses.get(i);
        CourseHolder holder = (CourseHolder) viewHolder;
        holder.title.setText(course.getTitle());
        holder.code.setText(course.getCode());
        holder.unit.setText(String.valueOf(course.getCredit_unit()));
        holder.grade.setText(course.getGrade());
    }

    @Override
    public int getItemCount() {
        return mCourses !=null?mCourses.size():0;
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView code,title, unit,grade;
        private EditText mScoreField;
        CourseHolder(@NonNull final View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.course_code);
            title = itemView.findViewById(R.id.course_title);
            unit = itemView.findViewById(R.id.credit_unit);
            grade = itemView.findViewById(R.id.grade);
            mScoreField = itemView.findViewById(R.id.score);
            mScoreField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String score = "0";
                    if(!s.toString().isEmpty()){
                        score = s.toString();
                    }

                    mCourses.get(getAdapterPosition()).setScore(Integer.valueOf(score));
                    grade.setText(mCourses.get(getAdapterPosition()).getGrade());
                    mListerner.onScoreChange(calculateGPA());
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        }
    }
    public double calculateGPA(){
        int totalUnit = 0;
        float totalGrade = 0;
        for(Course course:mCourses){
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
