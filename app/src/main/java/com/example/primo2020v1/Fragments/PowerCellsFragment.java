package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.primo2020v1.libs.Cycle;
import com.example.primo2020v1.libs.Keys;

import java.util.ArrayList;

public class PowerCellsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    public interface PowerCellsListener {
        void getDataPowerCells(Intent pcIntent);
    }

    private PowerCellsListener listener;
    private Intent pcIntent;

    public int pcInner = 0, pcOuter = 0, pcLower = 0, pcMissed = 0;
    public static int[] positions = new int[4];

    //Tele: true; Auto: false
    public static boolean phase = false;
    private ArrayList<Cycle> cycles;

    private Button btnCycle, btnTeleAuto;
    private TextView tvPowerCellsInner, tvPowerCellsOuter, tvPowerCellsLower, tvPowerCellsMissed;
    private SeekBar sbPowerCellsInner, sbPowerCellsOuter, sbPowerCellsLower, sbPowerCellsMissed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.power_cells, container, false);

        tvPowerCellsMissed = (TextView) v.findViewById(R.id.tvPowerCellsMissed);
        tvPowerCellsLower = (TextView) v.findViewById(R.id.tvPowerCellsLower);
        tvPowerCellsOuter = (TextView) v.findViewById(R.id.tvPowerCellsOuter);
        tvPowerCellsInner = (TextView) v.findViewById(R.id.tvPowerCellsInner);

        sbPowerCellsMissed = (SeekBar) v.findViewById(R.id.sbPowerCellsMissed);
        sbPowerCellsLower = (SeekBar) v.findViewById(R.id.sbPowerCellsLower);
        sbPowerCellsOuter = (SeekBar) v.findViewById(R.id.sbPowerCellsOuter);
        sbPowerCellsInner = (SeekBar) v.findViewById(R.id.sbPowerCellsInner);

        btnCycle = (Button) v.findViewById(R.id.btnCycle);
        btnTeleAuto = (Button) v.findViewById(R.id.btnTeleAuto);
        onReselect();

        sbPowerCellsMissed.setOnSeekBarChangeListener(this);
        sbPowerCellsLower.setOnSeekBarChangeListener(this);
        sbPowerCellsOuter.setOnSeekBarChangeListener(this);
        sbPowerCellsInner.setOnSeekBarChangeListener(this);
        btnTeleAuto.setOnClickListener(this);
        btnCycle.setOnClickListener(this);

        if(phase)
            btnTeleAuto.setText("Tele");
        else
            btnTeleAuto.setText("Auto");

        pcIntent = new Intent(getContext(), GameFormActivity.class);
        cycles = new ArrayList<>();

        return v;
    }

    //SeekBars
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser) {
            if(progress > getMax(seekBar))
                seekBar.setProgress(getMax(seekBar));
        }

        updateSeekBarsText();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCycle:
                getPCValues();
                placeInfo();
                resetSeekBars();
                break;

            case R.id.btnTeleAuto:
                if (phase){
                    btnTeleAuto.setText("Auto");
                } else {
                    btnTeleAuto.setText("Tele");
                }

                phase = !phase;
                break;

            default:
                break;
        }
    }

    private int getMax(SeekBar sb){
        return 5 - getPowerCellsFromSeekBars() + sb.getProgress();
    }

    //Power Cells SeekBars logic
    private int getPowerCellsFromSeekBars(){
        return sbPowerCellsMissed.getProgress()
                + sbPowerCellsLower.getProgress()
                + sbPowerCellsOuter.getProgress()
                + sbPowerCellsInner.getProgress();
    }

    private void updateSeekBarsText(){
        getPCValues();

        tvPowerCellsMissed.setText(Integer.toString(pcMissed));
        tvPowerCellsLower.setText(Integer.toString(pcLower));
        tvPowerCellsOuter.setText(Integer.toString(pcOuter));
        tvPowerCellsInner.setText(Integer.toString(pcInner));
    }

    private void resetSeekBars(){
        sbPowerCellsMissed.setProgress(0);
        sbPowerCellsLower.setProgress(0);
        sbPowerCellsOuter.setProgress(0);
        sbPowerCellsInner.setProgress(0);
    }

    private void getPCValues(){
        pcMissed = sbPowerCellsMissed.getProgress();
        pcLower = sbPowerCellsLower.getProgress();
        pcOuter = sbPowerCellsOuter.getProgress();
        pcInner = sbPowerCellsInner.getProgress();
    }

    private void onReselect(){
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

        try{
            listener = (PowerCellsListener) context;
        } catch (ClassCastException e){
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
        listener.getDataPowerCells(pcIntent);
        listener = null;
    }

    public void placeInfo() {
        if ((pcMissed + pcLower + pcOuter + pcInner) > 0)
            cycles.add(new Cycle(pcMissed, pcLower, pcOuter, pcInner, phase));
    }
}
