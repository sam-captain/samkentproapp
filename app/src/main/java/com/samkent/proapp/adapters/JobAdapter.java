package com.samkent.proapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.samkent.proapp.JobDetailsActivity;
import com.samkent.proapp.R;
import com.samkent.proapp.models.Jobs;
import com.samkent.proapp.models.Profession;
import com.samkent.proapp.ui.home.HomeViewModel;
import com.samkent.proapp.utilities.ObjectBox;
import com.google.android.material.chip.Chip;

import java.util.List;

import io.objectbox.Box;

public class JobAdapter  extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    Context context;
    List<Jobs> jobs;
    Box<Profession> professionBox= ObjectBox.get().boxFor(Profession.class);
    HomeViewModel homeViewModel;


    public JobAdapter(Context context, List<Jobs> jobs, HomeViewModel homeViewModel) {
        this.context = context;
        this.jobs = jobs;
        this.homeViewModel = homeViewModel;

    }
    @NonNull
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job,parent,false);

        return new JobAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.ViewHolder holder, int position) {
        Jobs job = jobs.get(position);
        holder.title.setText(job.getTitle());
        holder.description.setText(job.getDescription());
        holder.id =job.getId();
        if (job.getProfession() != 0){
            long profession_id = job.getProfession();
            Profession ourProfession = professionBox.get(profession_id);
            holder.professionJobChip.setText(ourProfession.getName());

        }
        else{
            holder.professionJobChip.setText("Fit for all");
        }
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Chip professionJobChip;
        TextView title,description;
        Button details;
        long id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            professionJobChip = itemView.findViewById(R.id.chip_job);
            title = itemView.findViewById(R.id.txt_title_job);
            description = itemView.findViewById(R.id.job_description);
            details = itemView.findViewById(R.id.btn_more_details);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, JobDetailsActivity.class);
//                    intent.putExtra("ID", id);
//                    context.startActivity(intent);
                    homeViewModel.setSelected(id);
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID", id);

                    Navigation.findNavController(v).navigate(R.id.action_jobDetails, bundle);


                }
            });

        }
    }
}
