package com.example.primo2020v1.ui.matches;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MatchesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MatchesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}