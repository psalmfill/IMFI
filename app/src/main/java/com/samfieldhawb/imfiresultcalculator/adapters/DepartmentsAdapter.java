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
import com.samfieldhawb.imfiresultcalculator.models.Department;

import java.util.List;

public class DepartmentsAdapter  extends RecyclerView.Adapter<DepartmentsAdapter.DepartmentHolder> {
    Context mContext;
    List<Department> departmentList;

    public DepartmentsAdapter(Context mContext, List<Department> departmentList) {
        this.mContext = mContext;
        this.departmentList = departmentList;
    }

    @NonNull
    @Override
    public DepartmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new DepartmentHolder(LayoutInflater.from(mContext).inflate(R.layout.department_single,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentHolder departmentHolder, int i) {
        departmentHolder.name.setText(departmentList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return departmentList !=null ?departmentList.size():0;
    }

    public void setDepartmentList(List<Department> departmentList){
        this.departmentList = departmentList;
        notifyDataSetChanged();
    }
    class DepartmentHolder extends RecyclerView.ViewHolder {
        TextView name;

        public DepartmentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expandable_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext.getApplicationContext(), LecturerHomeActivity.class);
//                    intent.putExtra(LecturerHomeActivity.DEPARTMENT,mDepartmentList.get(getAdapterPosition()).getShort_code().toLowerCase());
//                    mContext.startActivity(intent);
                }
            });
        }
    }
}
