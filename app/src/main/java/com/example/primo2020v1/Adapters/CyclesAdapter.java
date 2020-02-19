package com.example.primo2020v1.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.EditFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.SubmissionActivity;
import com.example.primo2020v1.libs.Cycle;

import java.util.ArrayList;

public class CyclesAdapter extends ArrayAdapter<Cycle> {
    Context context;
    int res;
    ArrayList<Cycle> cycles;

    public CyclesAdapter(@NonNull Context context, int resource, ArrayList<Cycle> cycles) {
        super(context, resource, cycles);
        this.context = context;
        res = resource;
        this.cycles = cycles;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(res, null);

        TextView edMissed = v.findViewById(R.id.edMissed);
        TextView edLower = v.findViewById(R.id.edLower);
        TextView edOuter = v.findViewById(R.id.edOuter);
        TextView edInner = v.findViewById(R.id.edInner);

        TextView tvCycles = v.findViewById(R.id.tvCycle);
        TextView tvPhase = v.findViewById(R.id.tvPhase);

        ImageView imgDelete = v.findViewById(R.id.imgDelete);

        int[] arrCycle = cycles.get(position).toArray();
        edMissed.setText(Integer.toString(arrCycle[0]));
        edLower.setText(Integer.toString(arrCycle[1]));
        edOuter.setText(Integer.toString(arrCycle[2]));
        edInner.setText(Integer.toString(arrCycle[3]));
        tvCycles.setText(tvCycles.getText().toString() + " " + (position + 1));

        String phase = "Auto";
        tvPhase.setTextColor(R.color.mainOrange);

        if (cycles.get(position).getPhase()){
            phase = "TeleOp";
            tvPhase.setHintTextColor(R.color.mainBlue);
        }
        tvPhase.setText(phase);

        if (context instanceof SubmissionActivity || context instanceof EditFormActivity) {
            imgDelete.setImageResource(R.drawable.ic_delete_black_24dp);
            imgDelete.setOnClickListener(view -> {
                cycles.remove(position);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Deleted Cycle", Toast.LENGTH_SHORT).show();
            });
        }

        return v;
    }

    public ArrayList<Cycle> getCycles() {
        return cycles;
    }

    public void setCycles(ArrayList<Cycle> cycles) {
        this.cycles = cycles;
    }
}
