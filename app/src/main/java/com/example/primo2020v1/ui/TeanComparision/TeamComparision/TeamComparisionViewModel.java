package com.example.primo2020v1.ui.TeanComparision.TeamComparision;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeamComparisionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TeamComparisionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}