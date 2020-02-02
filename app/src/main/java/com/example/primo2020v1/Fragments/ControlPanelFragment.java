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

public class ControlPanelFragment extends Fragment implements View.OnClickListener {
    private ImageView imgCPnormal, imgCPcolor;
    private ControlPanelListener listener;
    public static boolean isPCnormal, isPCcolor;
    private Intent cpIntent;


    public interface ControlPanelListener {
        void setDataControlPanel(Intent cpIntent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.control_panel, container, false);

        imgCPnormal = v.findViewById(R.id.imgCPnormal);
        imgCPcolor = v.findViewById(R.id.imgCPcolor);

        setBackGroundColor(imgCPnormal, isPCnormal);
        setBackGroundColor(imgCPcolor, isPCcolor);
        imgCPnormal.setOnClickListener(this);
        imgCPcolor.setOnClickListener(this);

        cpIntent = new Intent(getContext(), GameFormActivity.class);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCPnormal:
                isPCnormal = !isPCnormal;
                setBackGroundColor(imgCPnormal, isPCnormal);
                break;

            case R.id.imgCPcolor:
                isPCcolor = !isPCcolor;
                setBackGroundColor(imgCPcolor, isPCcolor);
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
        cpIntent.putExtra(Keys.CP_NORMAL, isPCnormal);
        cpIntent.putExtra(Keys.CP_COLOR, isPCcolor);

        listener.setDataControlPanel(cpIntent);

    }
}