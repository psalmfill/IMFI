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
import com.samfieldhawb.imfiresultcalculator.models.Level;
import com.samfieldhawb.imfiresultcalculator.models.Semester;

import java.util.List;

public class SemestersAdapter extends RecyclerView.Adapter<SemestersAdapter.SemesterHolder> {
    Context mContext;
    List<Semester> semesterList;

    OnClickListener listener;

    public SemestersAdapter(Context mContext, List<Semester> semesterList, OnClickListener listener) {
        this.mContext = mContext;
        this.semesterList = semesterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SemesterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new SemesterHolder(LayoutInflater.from(mContext).inflate(R.layout.department_single,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterHolder semesterHolder, int i) {
        semesterHolder.name.setText(semesterList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return semesterList !=null ? semesterList.size():0;
    }

    public void setSemesterList(List<Semester> semesterList){
        this.semesterList = semesterList;
        notifyDataSetChanged();
    }
    class SemesterHolder extends RecyclerView.ViewHolder {
        TextView name;

        public SemesterHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expandable_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   listener.onClick(semesterList.get(getAdapterPosition()).getShort_code());
                }
            });
        }
    }
}
