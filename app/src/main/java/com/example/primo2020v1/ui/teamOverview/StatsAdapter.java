package com.example.primo2020v1.ui.teamOverview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;

import java.util.ArrayList;

public class StatsAdapter extends ArrayAdapter {
    private int res;
    private double avrgShots;
    private Context context;
    private ArrayList<Double> values;
    private ArrayList<TextView> tvs;

    public StatsAdapter(Context context, int resource, ArrayList<Double> values) {
        super(context, resource, values);

        this.values = values;
        this.res = resource;
        this.context = context;
        avrgShots = 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

        if (position == 0) {
            tvs = new ArrayList<>();

            tvs.add((TextView) view.findViewById(R.id.tvShotInfo));
            tvs.add((TextView) view.findViewById(R.id.tvLowerInfo));
            tvs.add((TextView) view.findViewById(R.id.tvOuterInfo));
            tvs.add((TextView) view.findViewById(R.id.tvInnerInfo));
            tvs.add((TextView) view.findViewById(R.id.tvRC));
            tvs.add((TextView) view.findViewById(R.id.tvPC));
            tvs.add((TextView) view.findViewById(R.id.tvClimb));
            tvs.add((TextView) view.findViewById(R.id.tvBalanced));

            for (int i = 0; i < tvs.size(); i++) {
                tvs.get(i).setText(Double.toString(values.get(i)));
            }
            return view;
        }

        view = new View(getContext());
        view.setVisibility(View.INVISIBLE);
        return view;
    }
}
