package com.samkent.proapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samkent.proapp.models.Jobs;
import com.samkent.proapp.models.Profession;
import com.samkent.proapp.utilities.ObjectBox;

import java.util.List;

import io.objectbox.annotation.Id;

public class HomeViewModel extends ViewModel {
    private  MutableLiveData<List<Profession>> professions;
    private  MutableLiveData<List<Jobs>> jobs;


    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<List<Profession>> getProfessions(){
        if (professions == null){
            professions = new MutableLiveData<List<Profession>>();
            loadprofessions();
        }
        return professions;
    }

    private void loadprofessions() {
        professions.setValue(
                ObjectBox.get().boxFor(Profession.class).getAll()
        );

    }
    public LiveData<List<Jobs>> getJobs(){
        if (jobs == null){
            jobs = new MutableLiveData<List<Jobs>>();
            loadjobs();
        }
        return jobs;
    }

    private void loadjobs() {
        jobs.setValue(
                ObjectBox.get().boxFor(Jobs.class).getAll()
        );
    }

    private  MutableLiveData <Long> selected = new MutableLiveData<>();

    public LiveData<Long> getSelected() {

        return selected;

    }
    public void setSelected (long id){

        selected.setValue(id);

    }
}