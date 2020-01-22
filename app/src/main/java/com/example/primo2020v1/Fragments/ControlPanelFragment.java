package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.Keys;

public class ControlPanelFragment extends Fragment {
    private Switch switchControlPanelColor, switchControlPanel;
    private ControlPanelListener listener;
    public static boolean state1 = false, state2 = false;
    private Intent cpIntent;


    public interface ControlPanelListener {
        void getDataControlPanel(Intent cpIntent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.control_panel, container, false);

        switchControlPanel = v.findViewById(R.id.switchControlPanel);
        switchControlPanelColor = v.findViewById(R.id.switchControlPanelColor);

        switchControlPanel.setChecked(state1);
        switchControlPanelColor.setChecked(state2);

        cpIntent = new Intent(getContext(), GameFormActivity.class);

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

        placeInfo();
        listener = null;
    }

    public void placeInfo(){
        state1 = switchControlPanel.isChecked();
        state2 = switchControlPanelColor.isChecked();
        cpIntent.putExtra(Keys.CP_NORMAL, state1);
        cpIntent.putExtra(Keys.CP_COLOR, state2);

        listener.getDataControlPanel(cpIntent);

    }
}
