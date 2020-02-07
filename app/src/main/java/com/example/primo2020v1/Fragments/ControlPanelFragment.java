package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;

public class ControlPanelFragment extends Fragment implements View.OnClickListener {
    private ImageView imgCPRC, imgCPPC;
    private ControlPanelListener listener;
    public static int rcIndex, pcIndex;
    private Intent cpIntent;


    public interface ControlPanelListener {
        void setDataControlPanel(Intent cpIntent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.control_panel, container, false);

        imgCPRC = v.findViewById(R.id.imgCPnormal);
        imgCPPC = v.findViewById(R.id.imgCPcolor);

        setRotationControlImage();
        setPositionControlImage();
        imgCPRC.setOnClickListener(this);
        imgCPPC.setOnClickListener(this);

        cpIntent = new Intent(getContext(), GameFormActivity.class);

        return v;
    }

    private void setRotationControlImage() {
        imgCPRC.setImageResource(User.controlPanelRotation[rcIndex]);
    }

    private void setPositionControlImage() {
        imgCPPC.setImageResource(User.controlPanelPosition[pcIndex]);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCPnormal:
                rcIndex++;
                rcIndex %= User.controlPanelRotation.length;
                setRotationControlImage();
                break;

            case R.id.imgCPcolor:
                pcIndex++;
                pcIndex %= User.controlPanelPosition.length;
                setPositionControlImage();
                break;

            default:
                break;
        }
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

    public void setBackGroundColor(ImageView img, boolean b) {
        img.setColorFilter(b ? getResources().getColor(R.color.mainBlue) :
                getResources().getColor(R.color.defaultColor));
    }

    public void placeInfo(){
        cpIntent.putExtra(Keys.CP_RC, rcIndex);
        cpIntent.putExtra(Keys.CP_PC, pcIndex);

        listener.setDataControlPanel(cpIntent);

    }
}