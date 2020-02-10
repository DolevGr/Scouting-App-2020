package com.example.primo2020v1.ui.teamOverview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeamOverviewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TeamOverviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}