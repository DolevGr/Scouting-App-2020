package com.example.primo2020v1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.Match;

import java.util.ArrayList;

public class GamesAdapter extends ArrayAdapter<Match> {

    private Context context;
    private int res;
    private String highlightedTeam;
    private ArrayList<Match> arrList;

    public GamesAdapter(Context context, int id, ArrayList<Match> lst, String highlightedTeam) {
        super(context, id, lst);

        this.context = context;
        this.res = id;
        this.highlightedTeam = highlightedTeam;
        arrList = lst;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

        TextView tvGame = view.findViewById(R.id.tvGameNumber);
        TextView tvR1 = (TextView) view.findViewById(R.id.tvR1);
        TextView tvR2 = (TextView) view.findViewById(R.id.tvR2);
        TextView tvR3 = (TextView) view.findViewById(R.id.tvR3);
        TextView tvB1 = (TextView) view.findViewById(R.id.tvB1);
        TextView tvB2 = (TextView) view.findViewById(R.id.tvB2);
        TextView tvB3 = (TextView) view.findViewById(R.id.tvB3);
        ImageView img = (ImageView) view.findViewById(R.id.imgRedBlue);

        Match match = this.getItem(position);
        tvR1.setText(match.getFirstRedRobot());
        tvR2.setText(match.getSecondRedRobot());
        tvR3.setText(match.getThirdRedRobot());
        tvB1.setText(match.getFirstBlueRobot());
        tvB2.setText(match.getSecondBlueRobot());
        tvB3.setText(match.getThirdBlueRobot());

        tvGame.setText(Integer.toString(match.getGameNum()));
        img.setImageResource(R.drawable.red_blue);

        if (tvR1.getText().toString().equals("4586")) {
            tvR1.setBackgroundColor(context.getResources().getColor(R.color.ourGame));
        } else if (tvR2.getText().toString().equals("4586")) {
            tvR2.setBackgroundColor(context.getResources().getColor(R.color.ourGame));
        } else if (tvR3.getText().toString().equals("4586")) {
            tvR3.setBackgroundColor(context.getResources().getColor(R.color.ourGame));
        } else if (tvB1.getText().toString().equals("4586")) {
            tvB1.setBackgroundColor(context.getResources().getColor(R.color.ourGame));
        } else if (tvB2.getText().toString().equals("4586")) {
            tvB2.setBackgroundColor(context.getResources().getColor(R.color.ourGame));
        } else if (tvB3.getText().toString().equals("4586")) {
            tvB3.setBackgroundColor(context.getResources().getColor(R.color.ourGame));
        }

        if (!highlightedTeam.equals("4586")) {
            if (tvR1.getText().toString().equals(this.highlightedTeam)) {
                tvR1.setBackgroundColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvR2.getText().toString().equals(this.highlightedTeam)) {
                tvR2.setBackgroundColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvR3.getText().toString().equals(this.highlightedTeam)) {
                tvR3.setBackgroundColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvB1.getText().toString().equals(this.highlightedTeam)) {
                tvB1.setBackgroundColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvB2.getText().toString().equals(this.highlightedTeam)) {
                tvB2.setBackgroundColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvB3.getText().toString().equals(this.highlightedTeam)) {
                tvB3.setBackgroundColor(context.getResources().getColor(R.color.highlightedTeam));
            }
        }

        return view;
    }
}
