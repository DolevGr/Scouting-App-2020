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
import com.example.primo2020v1.utils.User;

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

        TextView tvTeamName = view.findViewById(R.id.tvTeamNameAbility);
        TextView tvTeamNumber = view.findViewById(R.id.tvTeamNumberAbility);
        TextView tvRating = view.findViewById(R.id.tvRating);
        TextView tvNumber = view.findViewById(R.id.tvRatingNumberAbility);

        tvTeamName.setText(User.participants.get(ranking.get(position).getKey()));
        tvTeamName.setTextColor(context.getResources().getColor(R.color.mainBlue));
        tvNumber.setText(Integer.toString(position + 1));
        tvTeamNumber.setText(ranking.get(position).getKey().toString());
        tvRating.setText(ranking.get(position).getValue().toString());

        return view;
    }
}
