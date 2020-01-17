package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.R;

import static android.content.ContentValues.TAG;

public class FinishFragment extends Fragment implements View.OnClickListener {
    FinishListener listener;
    private static int teamState;
    private ImageView imgTeamState;


    public interface FinishListener{
        void getDataFinish(int state);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish, container, false);

        imgTeamState = v.findViewById(R.id.imgTeamState);
        imgBGColor();

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgTeamState:
                teamState++;
                teamState %= 3;
                imgBGColor();
                break;

            case R.id.btnToSubmission:
                break;
        }
    }

    public void imgBGColor(){
        switch (teamState){
            case 0:
                imgTeamState.setBackgroundColor(Color.GREEN);
                break;

            case 1:
                imgTeamState.setBackgroundColor(Color.RED);
                break;

            default:
                imgTeamState.setBackgroundColor(Color.YELLOW);
        }

        Log.d(TAG, "imgBGColor: " + teamState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (FinishListener) context;
        } catch (ClassCastException e){
            throw new RuntimeException(context.toString() +
                    " must implement FinishListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener.getDataFinish(teamState);
        listener = null;
    }
}
