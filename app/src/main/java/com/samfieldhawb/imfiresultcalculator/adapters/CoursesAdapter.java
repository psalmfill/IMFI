package com.samfieldhawb.imfiresultcalculator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.models.Course;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Course> mCourses;
    private listerner mListerner;
    public interface listerner {
        void onCourseChange(Course course,boolean isChecked);
//        void onClicked();

    }
    public CoursesAdapter(Context context, List<Course> courses,listerner listerner) {
        mContext = context;
        mCourses = courses;
        mListerner = listerner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CourseHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.single_course_reg_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Course course = mCourses.get(i);
        CourseHolder holder = (CourseHolder) viewHolder;
        holder.title.setText(course.getTitle());
        holder.code.setText(course.getCode());
        holder.unit.setText(String.valueOf(course.getCredit_unit()));
    }

    @Override
    public int getItemCount() {
        return mCourses !=null?mCourses.size():0;
    }

    public void setCourses(List<Course> courses){
        mCourses.clear();
        mCourses = courses;
notifyDataSetChanged();
//        for(Course course : courses){
//            addCourse(course);
//        }
    }

    public void addCourse(Course course){
        mCourses.add(course);
        notifyItemInserted(mCourses.size()-1);
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView code,title, unit;
        private Switch mSwitch;
        CourseHolder(@NonNull final View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.course_code);
            title = itemView.findViewById(R.id.course_title);
            unit = itemView.findViewById(R.id.credit_unit);
            mSwitch = itemView.findViewById(R.id.switch_course);
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //Todo create action when button is checked
                    if(buttonView.isChecked()){
                        itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        code.setTextColor(mContext.getResources().getColor(android.R.color.white));
                        title.setTextColor(mContext.getResources().getColor(android.R.color.white));
                        unit.setTextColor(mContext.getResources().getColor(android.R.color.white));
                    }else {
                        itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhitish));
                        code.setTextColor(mContext.getResources().getColor(android.R.color.black));
                        title.setTextColor(mContext.getResources().getColor(android.R.color.black));
                        unit.setTextColor(mContext.getResources().getColor(android.R.color.black));

                    }

                    mListerner.onCourseChange(mCourses.get(getAdapterPosition()),isChecked);
                }
            });
        }
    }
}
