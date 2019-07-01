package com.samfieldhawb.imfiresultcalculator.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.lecturer.LecturerHomeActivity;
import com.samfieldhawb.imfiresultcalculator.models.Faculty;

import java.util.List;

public class FacultiesAdapter  extends RecyclerView.Adapter<FacultiesAdapter.FacultyHolder> {

    Context mContext;
    List<Faculty> mFaculties;

    public FacultiesAdapter(Context mContext, List<Faculty> mFaculties) {
        this.mContext = mContext;
        this.mFaculties = mFaculties;
    }



    @NonNull
    @Override
    public FacultyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new FacultyHolder(LayoutInflater.from(mContext).inflate(R.layout.faculty_group,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyHolder facultyHolder, int i) {
        facultyHolder.name.setText(mFaculties.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mFaculties !=null?mFaculties.size():0;
    }

    public void setmFaculties(List<Faculty> faculties){
        mFaculties = faculties;
        notifyDataSetChanged();
    }

    class FacultyHolder  extends RecyclerView.ViewHolder {
    TextView name;
     public FacultyHolder(@NonNull View itemView) {
         super(itemView);
         name = itemView.findViewById(R.id.expandable_group);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(mContext.getApplicationContext(), LecturerHomeActivity.class);
                 intent.putExtra(LecturerHomeActivity.DEPARTMENT,mFaculties.get(getAdapterPosition()).getShort_code().toLowerCase());
                 mContext.startActivity(intent);
             }
         });
     }
 }
}