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

public class ControlPanelFragment extends Fragment {
    private Switch switchControlPanelColor, switchControlPanel;
    private ControlPanelListener listener;
    private static boolean state1, state2;


    public interface ControlPanelListener {
        void getDataControlPanel(boolean switchControlPanelState, boolean switchControlPanelColorState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.control_panel, container, false);

        switchControlPanel = v.findViewById(R.id.switchControlPanel);
        switchControlPanelColor = v.findViewById(R.id.switchControlPanelColor);

        switchControlPanel.setChecked(state1);
        switchControlPanelColor.setChecked(state2);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (ControlPanelListener) context;
        } catch (ClassCastException e){
            throw new RuntimeException(context.toString() +
                    " must implement ControlPanelListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        state1 = switchControlPanel.isChecked();
        state2 = switchControlPanelColor.isChecked();

        listener.getDataControlPanel(state1, state2);
        listener = null;
    }

    public void resetFragment(){
        state1 = false;
        state2 = false;
    }
}
