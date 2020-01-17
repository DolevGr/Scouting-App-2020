package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.R;

public class EndGameFragment extends Fragment {
    private Switch switchParked, switchClimbed, switchBalanced;
    private EndGameListener listener;
    private static boolean state1, state2, state3;

    public interface EndGameListener {
        void getDataEndGame(boolean parked, boolean climbed, boolean balanced);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.end_game, container, false);

        switchParked = v.findViewById(R.id.switchParked);
        switchClimbed = v.findViewById(R.id.switchClimbed);
        switchBalanced = v.findViewById(R.id.switchBalanced);

        switchParked.setChecked(state1);
        switchClimbed.setChecked(state2);
        switchBalanced.setChecked(state3);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (EndGameListener) context;
        } catch (ClassCastException e){
            throw new RuntimeException(context.toString() +
                    " must implement EndGameListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        state1 = switchParked.isChecked();
        state2 = switchClimbed.isChecked();
        state3 = switchBalanced.isChecked();
        listener.getDataEndGame(state1, state2, state3);
        listener = null;
    }

    public void resetFragment(){
        state1 = false;
        state2 = false;
        state3 = false;
    }
}
