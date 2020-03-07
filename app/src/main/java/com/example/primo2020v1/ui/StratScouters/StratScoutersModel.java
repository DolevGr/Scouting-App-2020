package com.example.primo2020v1.ui.StratScouters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StratScoutersModel extends ViewModel {
    private MutableLiveData<String> mText;

    public StratScoutersModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is strat Scouter fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
