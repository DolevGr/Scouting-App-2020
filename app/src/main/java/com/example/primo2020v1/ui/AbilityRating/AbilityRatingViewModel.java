package com.example.primo2020v1.ui.AbilityRating;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AbilityRatingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AbilityRatingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}