package com.samkent.proapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samkent.proapp.R;
import com.samkent.proapp.models.Profession;
import com.google.android.material.chip.Chip;

import java.util.List;

public class JobProfessionsAdapter extends RecyclerView.Adapter<JobProfessionsAdapter.ViewHolder> {
    Context context;
    List<Profession> professions;
   public long profession_id = 0;


    public JobProfessionsAdapter(Context context, List<Profession> professions) {
        this.context = context;
        this.professions = professions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_professions,parent,false);

        return new JobProfessionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profession profession = professions.get(position);
        holder.professionChip.setText(profession.getName());
        holder.id = profession.getId();
        holder.pro = profession.getName();

    }

    @Override
    public int getItemCount() {
        return professions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Chip professionChip;
        String pro;
        long id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            professionChip = itemView.findViewById(R.id.chip_profession);



            professionChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profession_id =id;
                    Toast.makeText(context, "you have selected "+pro, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
