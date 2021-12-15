package com.samkent.proapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samkent.proapp.R;
import com.samkent.proapp.adapters.ProfessionAdapter;
import com.samkent.proapp.adapters.ProfessionalAdapter;
import com.samkent.proapp.databinding.FragmentDashboardBinding;
import com.samkent.proapp.databinding.FragmentHomeBinding;
import com.samkent.proapp.models.Profession;
import com.samkent.proapp.models.Professional;
import com.samkent.proapp.ui.home.HomeViewModel;
import com.samkent.proapp.utilities.ObjectBox;
import com.samkent.proapp.utilities.PreferenceStorage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class DashboardFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentDashboardBinding binding;
    
    List<Professional> professionals = new ArrayList<>();
    ProfessionalAdapter professionalAdapter;


    List<Profession> professions = new ArrayList<>();
    ProfessionAdapter professionAdapter;

    TextView txthello;
    PreferenceStorage preferenceStorage;
    Box<Profession> professionBox = ObjectBox.get().boxFor(Profession.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferenceStorage = new PreferenceStorage(getActivity());

        txthello = root.findViewById(R.id.txt_hello);

        RecyclerView professionsRecyclerview = root.findViewById(R.id.profession_recyclerview);
        professionsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));

        professionsRecyclerview.setNestedScrollingEnabled(true);



        professionAdapter = new ProfessionAdapter(getActivity(),professions);
        professionsRecyclerview.setAdapter(professionAdapter);



        RecyclerView hiresRecyclerviews = root.findViewById(R.id.Hires_recyclerview);

        hiresRecyclerviews.setNestedScrollingEnabled(true);
        hiresRecyclerviews.setLayoutManager(new LinearLayoutManager(getActivity()));


        professionalAdapter = new ProfessionalAdapter(getActivity(),professionals);
        hiresRecyclerviews.setAdapter(professionalAdapter);


        Button btnAllProfessions = root.findViewById(R.id.btn_all_professions);

        btnAllProfessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addProfessionDialog();

            }

        });
        
        return root;
    }
    public void addProfessionDialog(){
        final BottomSheetDialog addProfession = new BottomSheetDialog(getActivity());
        addProfession.setContentView(R.layout.new_profession_layout);
        TextInputEditText txtViewProfession = addProfession.findViewById(R.id.input_profession_name);
        Button btnAddProfession = addProfession.findViewById(R.id.btn_add_profession);
        btnAddProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profession = txtViewProfession.getText().toString().trim();
                if (profession.isEmpty()){
                    txtViewProfession.setError("Please Add New Profession");
                }
                else{
                    saveProfession(profession);
                    professionAdapter.notifyDataSetChanged();
                    addProfession.dismiss();

                }
            }
        });
        addProfession.show();
    }
    public void saveProfession(String name){
        Profession profession = new Profession(name);
        long id = professionBox.put(profession);
        profession.setId(id);
        professions.add(profession);
    }

    @Override
    public void onResume() {
        super.onResume();

        homeViewModel.getProfessions().observe(requireActivity(), professions_variable ->{
            professions.clear();
            professions.addAll(professions_variable);
            professionAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}