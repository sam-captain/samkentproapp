package com.samkent.proapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.samkent.proapp.models.Jobs;
import com.samkent.proapp.ui.home.HomeViewModel;
import com.samkent.proapp.utilities.ObjectBox;


public class JobDetailsFragment extends Fragment {

    HomeViewModel homeViewModel;

    public JobDetailsFragment() {
        // Required empty public constructor
    }

    TextView description, requirement,salary,mode,location, applyBefore;
    Button applyJob;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_job_details, container, false);
        description = root.findViewById(R.id.txt_description);
        requirement = root.findViewById(R.id.txt_requirement);
        salary = root.findViewById(R.id.chip_salary);
        mode = root.findViewById(R.id.chip_mode);
        location =root.findViewById(R.id.chip_location);
        applyBefore = root.findViewById(R.id.txt_job_apply);
        applyJob = root.findViewById(R.id.btn_apply);
        
        return root;
    }

    @Override
    public void onResume() {

        super.onResume();
        getArguments().getLong("ID");

//        if (getArguments().containsKey("ID")){
//  }
        homeViewModel.getSelected().observe(requireActivity(),id ->{
            displayData(id);
        });

    }
    public void displayData(long id){

        Jobs ourJob = ObjectBox.get().boxFor(Jobs.class).get(id);
        description.setText(ourJob.getDescription());
        requirement.setText(ourJob.getRequirements());
        salary.setText(ourJob.getSalary());
        mode.setText("Full Time");
        location.setText(ourJob.getLocation());
        applyBefore.setText(ourJob.getExpiry_date());

    }

}