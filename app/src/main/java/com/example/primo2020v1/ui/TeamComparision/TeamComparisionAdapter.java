package com.example.primo2020v1.ui.TeamComparision;

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

public class TeamComparisionAdapter extends ArrayAdapter {
    private int res;
    private double avrgShots;
    private Context context;
    private ArrayList<Double> values;
    private ArrayList<TextView> tvs;

    public TeamComparisionAdapter(@NonNull Context context, int resource, ArrayList<Double> values, ArrayList<Integer> single) {
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
            tvs.add(view.findViewById(R.id.tvCrashesComp));
            tvs.add(view.findViewById(R.id.tvDefendedComp));
            tvs.add(view.findViewById(R.id.tvYellowCardsComp));
            tvs.add(view.findViewById(R.id.tvRedCardsComp));
            tvs.add(view.findViewById(R.id.tvShotsComp));
            tvs.add(view.findViewById(R.id.tvSuccessRateComp));
            tvs.add(view.findViewById(R.id.tvLowerComp));
            tvs.add(view.findViewById(R.id.tvOuterComp));
            tvs.add(view.findViewById(R.id.tvInnerComp));
            tvs.add(view.findViewById(R.id.tvRCComp));
            tvs.add(view.findViewById(R.id.tvPCComp));
            tvs.add(view.findViewById(R.id.tvClimbComp));
            tvs.add(view.findViewById(R.id.tvBalancedComp));

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
