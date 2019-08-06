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

import java.util.List;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelHolder> {
    Context mContext;
    List<Level> levelList;

    OnClickListener listener;

    public LevelsAdapter(Context mContext, List<Level> levelList, OnClickListener listener) {
        this.mContext = mContext;
        this.levelList = levelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LevelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new LevelHolder(LayoutInflater.from(mContext).inflate(R.layout.department_single,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LevelHolder levelHolder, int i) {
        levelHolder.name.setText(levelList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return levelList !=null ? levelList.size():0;
    }

    public void setLevelList(List<Level> levelList){
        this.levelList = levelList;
        notifyDataSetChanged();
    }
    class LevelHolder extends RecyclerView.ViewHolder {
        TextView name;

        public LevelHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expandable_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   listener.onClick(levelList.get(getAdapterPosition()).getShort_code());
                }
            });
        }
    }
}
