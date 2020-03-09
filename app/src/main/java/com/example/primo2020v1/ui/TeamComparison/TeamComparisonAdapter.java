package com.example.primo2020v1.ui.TeamComparison;

import android.content.Context;
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

public class TeamComparisonAdapter extends ArrayAdapter {
    private int res;
    private Context context;
    private ArrayList<Double> values;
    private ArrayList<TextView> tvs;
    private String comments;

    public TeamComparisonAdapter(@NonNull Context context, int resource, ArrayList<Double> values,
                                 String comments, ArrayList<Integer> single) {
        super(context, resource, single);
        this.values = values;
        this.res = resource;
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

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
            tvs.get(i).setText(df.format(values.get(i)));
            if (i == 5)
                tvs.get(i).setText(df.format(values.get(i) * 100) + "%");
        }

        TextView tvComments = view.findViewById(R.id.tvCommentsComp);
        tvComments.setText(comments);

        return view;
    }
}
