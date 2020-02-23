package com.example.primo2020v1.GameFormFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Cycle;
import com.example.primo2020v1.utils.Keys;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PowerCellsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    public interface PowerCellsListener {
        void setDataPowerCells(Intent pcIntent);
    }

    private PowerCellsListener listener;
    private Intent pcIntent;

    private int pcInner, pcOuter, pcLower, pcMissed;
    public static int[] positions = new int[4];
    private int[] resColors = {R.color.mainOrange, R.color.mainBlue};

    //Tele: true; Auto: false
    public static boolean phase;
    private ArrayList<Cycle> cycles;

    private Button btnCycle, btnTeleAuto;
    private TextView tvPowerCellsInner, tvPowerCellsOuter, tvPowerCellsLower, tvPowerCellsMissed;
    private SeekBar sbPowerCellsInner, sbPowerCellsOuter, sbPowerCellsLower, sbPowerCellsMissed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.power_cells, container, false);

        tvPowerCellsMissed = v.findViewById(R.id.tvPowerCellsMissed);
        tvPowerCellsLower = v.findViewById(R.id.tvPowerCellsLower);
        tvPowerCellsOuter = v.findViewById(R.id.tvPowerCellsOuter);
        tvPowerCellsInner = v.findViewById(R.id.tvPowerCellsInner);

        sbPowerCellsMissed = v.findViewById(R.id.sbPowerCellsMissed);
        sbPowerCellsLower = v.findViewById(R.id.sbPowerCellsLower);
        sbPowerCellsOuter = v.findViewById(R.id.sbPowerCellsOuter);
        sbPowerCellsInner = v.findViewById(R.id.sbPowerCellsInner);

        btnCycle = v.findViewById(R.id.btnCycle);
        btnTeleAuto = v.findViewById(R.id.btnTeleAuto);
        onReselect();
        getPCValues();

        sbPowerCellsMissed.setOnSeekBarChangeListener(this);
        sbPowerCellsLower.setOnSeekBarChangeListener(this);
        sbPowerCellsOuter.setOnSeekBarChangeListener(this);
        sbPowerCellsInner.setOnSeekBarChangeListener(this);
        btnTeleAuto.setOnClickListener(this);
        btnCycle.setOnClickListener(this);

        if (!phase) {
            btnTeleAuto.setText("Auto");
            btnTeleAuto.setBackgroundResource(resColors[0]);
        } else {
            btnTeleAuto.setText("Tele");
            btnTeleAuto.setBackgroundResource(resColors[1]);
        }
        pcIntent = new Intent(getContext(), GameFormActivity.class);
        cycles = new ArrayList<>();

        return v;
    }

    //SeekBars
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (progress > getMax(seekBar))
                seekBar.setProgress(getMax(seekBar));
        }
        updateSeekBarsText();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCycle:
                getPCValues();
                placeInfo();
                resetSeekBars();
                break;

            case R.id.btnTeleAuto:
                phase = !phase;

                if (!phase) {
                    btnTeleAuto.setText("Auto");
                    btnTeleAuto.setBackgroundResource(resColors[0]);
                } else {
                    btnTeleAuto.setText("Tele");
                    btnTeleAuto.setBackgroundResource(resColors[1]);
                }
                break;

            default:
                break;
        }
    }

    private int getMax(SeekBar sb) {
        return 5 - getPowerCellsFromSeekBars() + sb.getProgress();
    }

    //Power Cells SeekBars logic
    private int getPowerCellsFromSeekBars() {
        return sbPowerCellsMissed.getProgress()
                + sbPowerCellsLower.getProgress()
                + sbPowerCellsOuter.getProgress()
                + sbPowerCellsInner.getProgress();
    }

    private void updateSeekBarsText() {
        getPCValues();

        tvPowerCellsMissed.setText(Integer.toString(pcMissed));
        tvPowerCellsLower.setText(Integer.toString(pcLower));
        tvPowerCellsOuter.setText(Integer.toString(pcOuter));
        tvPowerCellsInner.setText(Integer.toString(pcInner));
    }

    private void resetSeekBars() {
        sbPowerCellsMissed.setProgress(0);
        sbPowerCellsLower.setProgress(0);
        sbPowerCellsOuter.setProgress(0);
        sbPowerCellsInner.setProgress(0);
    }

    private void getPCValues() {
        pcMissed = sbPowerCellsMissed.getProgress();
        pcLower = sbPowerCellsLower.getProgress();
        pcOuter = sbPowerCellsOuter.getProgress();
        pcInner = sbPowerCellsInner.getProgress();
    }

    private void onReselect() {
        sbPowerCellsMissed.setProgress(positions[0]);
        sbPowerCellsLower.setProgress(positions[1]);
        sbPowerCellsOuter.setProgress(positions[2]);
        sbPowerCellsInner.setProgress(positions[3]);

        tvPowerCellsMissed.setText(Integer.toString(positions[0]));
        tvPowerCellsLower.setText(Integer.toString(positions[1]));
        tvPowerCellsOuter.setText(Integer.toString(positions[2]));
        tvPowerCellsInner.setText(Integer.toString(positions[3]));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PowerCellsListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString() +
                    " must implement PowerCellsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        positions[0] = sbPowerCellsMissed.getProgress();
        positions[1] = sbPowerCellsLower.getProgress();
        positions[2] = sbPowerCellsOuter.getProgress();
        positions[3] = sbPowerCellsInner.getProgress();
        pcIntent.putParcelableArrayListExtra(Keys.PC_CYCLE, cycles);
        listener.setDataPowerCells(pcIntent);
        listener = null;
    }

    public void placeInfo() {
        if ((pcMissed + pcLower + pcOuter + pcInner) > 0)
            cycles.add(new Cycle(pcMissed, pcLower, pcOuter, pcInner, phase));
        Log.d(TAG, "placeInfo: " + cycles.toString());
        Log.d(TAG, "placeInfo: " + pcMissed + " " + pcLower + " " + pcOuter + " " + pcInner);
    }
}
