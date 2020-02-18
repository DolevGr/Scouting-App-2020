package com.example.primo2020v1.ui.teamOverview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StatsAdapter extends ArrayAdapter {
    private int res;
    private double avrgShots;
    private Context context;
    private ArrayList<Double> values;
    private ArrayList<TextView> tvs;

    public StatsAdapter(Context context, int resource, ArrayList<Double> values, ArrayList<Integer> single) {
        super(context, resource, single);
        this.values = values;
        this.res = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

        if (position == 0) {
            tvs = new ArrayList<>();
            tvs.add(view.findViewById(R.id.tvCrashInfo));
            tvs.add(view.findViewById(R.id.tvDefenceInfo));
            tvs.add(view.findViewById(R.id.tvYellowCardsInfo));
            tvs.add(view.findViewById(R.id.tvRedCardsInfo));
            tvs.add(view.findViewById(R.id.tvShotInfo));
            tvs.add(view.findViewById(R.id.tvSuccessRateInfo));
            tvs.add(view.findViewById(R.id.tvLowerInfo));
            tvs.add(view.findViewById(R.id.tvOuterInfo));
            tvs.add(view.findViewById(R.id.tvInnerInfo));
            tvs.add(view.findViewById(R.id.tvRCComp));
            tvs.add(view.findViewById(R.id.tvPCComp));
            tvs.add(view.findViewById(R.id.tvClimbInfo));
            tvs.add(view.findViewById(R.id.tvBalancedInfo));

            DecimalFormat df = new DecimalFormat("#.##");
            for (int i = 0; i < tvs.size(); i++) {
                Log.d(TAG, "getView: " + i);
                tvs.get(i).setText(df.format(values.get(i)));
                if (i == 5)
                    tvs.get(i).setText(df.format(values.get(i) * 100) + "%");
            }
            return view;
        }

        view = new View(getContext());
        view.setVisibility(View.INVISIBLE);
        return view;
    }
}
