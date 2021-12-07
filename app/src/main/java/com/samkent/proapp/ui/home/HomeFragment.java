package com.samkent.proapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samkent.proapp.R;
import com.samkent.proapp.adapters.JobAdapter;
import com.samkent.proapp.databinding.FragmentHomeBinding;
import com.samkent.proapp.models.Jobs;
import com.samkent.proapp.utilities.ObjectBox;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    JobAdapter jobAdapter;
    Box<Jobs> jobsBox = ObjectBox.get().boxFor(Jobs.class);
    List<Jobs> jobs = new ArrayList<>();
    RecyclerView jobRecyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        jobRecyclerView = root.findViewById(R.id.jobsRecyclerView);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        jobRecyclerView.setNestedScrollingEnabled(true);
        jobs.addAll(jobsBox.getAll());
        jobAdapter = new JobAdapter(getActivity(), jobs);
        jobRecyclerView.setAdapter(jobAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}