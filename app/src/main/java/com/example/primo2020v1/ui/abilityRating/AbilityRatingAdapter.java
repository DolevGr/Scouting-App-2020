package com.example.primo2020v1.ui.abilityRating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;

import java.util.List;
import java.util.Map;

public class AbilityRatingAdapter extends ArrayAdapter {
    private Context context;
    private int res;
    private List<Map.Entry<Integer, Double>> ranking;

    public AbilityRatingAdapter(@NonNull Context context, int resource,
                                List<Map.Entry<Integer, Double>> ranking) {
        super(context, resource, ranking);
        this.context = context;
        this.res = resource;
        this.ranking = ranking;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

        final TextView tvTeamNumber = view.findViewById(R.id.tvTeamNumberAbility);
        TextView tvRating = view.findViewById(R.id.tvRating);

        tvTeamNumber.setText(ranking.get(position).getKey().toString());
        tvRating.setText(ranking.get(position).getValue().toString());

        return view;
    }
}
