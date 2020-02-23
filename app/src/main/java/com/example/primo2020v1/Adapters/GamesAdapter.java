package com.example.primo2020v1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Match;
import com.example.primo2020v1.utils.User;

import java.util.ArrayList;

public class GamesAdapter extends ArrayAdapter<Match> {

    private Context context;
    private int res;
    private String highlightedTeam;
    private ArrayList<Match> arrList;
    private boolean enable;

    public GamesAdapter(Context context, int id, ArrayList<Match> lst, String highlightedTeam, boolean enable) {
        super(context, id, lst);

        this.context = context;
        this.res = id;
        this.highlightedTeam = highlightedTeam;
        arrList = lst;
        this.enable = enable;
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

        img.setImageResource(R.drawable.red_blue);

        tvGame.setText(Integer.toString(match.getGameNum()));
        int colorOurMatch = context.getResources().getColor(R.color.ourGame),
                backgroundColorOurMatch = context.getResources().getColor(R.color.mainBlue);
        if (enable && position == User.currentGame - 1) {
            tvGame.setTextColor(Color.parseColor("#f37743"));
            colorOurMatch = context.getResources().getColor(R.color.mainBlue);
            backgroundColorOurMatch = context.getResources().getColor(R.color.currentMatch);
            view.setBackgroundColor(context.getResources().getColor(R.color.currentMatch));
        }

        if (tvR1.getText().toString().equals("4586")) {
            tvR1.setTextColor(colorOurMatch);
            tvR1.setBackgroundColor(backgroundColorOurMatch);
        } else if (tvR2.getText().toString().equals("4586")) {
            tvR2.setTextColor(colorOurMatch);
            tvR2.setBackgroundColor(backgroundColorOurMatch);
        } else if (tvR3.getText().toString().equals("4586")) {
            tvR3.setTextColor(colorOurMatch);
            tvR3.setBackgroundColor(backgroundColorOurMatch);
        } else if (tvB1.getText().toString().equals("4586")) {
            tvB1.setTextColor(colorOurMatch);
            tvB1.setBackgroundColor(backgroundColorOurMatch);
        } else if (tvB2.getText().toString().equals("4586")) {
            tvB2.setTextColor(colorOurMatch);
            tvB2.setBackgroundColor(backgroundColorOurMatch);
        } else if (tvB3.getText().toString().equals("4586")) {
            tvB3.setTextColor(colorOurMatch);
            tvB3.setBackgroundColor(backgroundColorOurMatch);
        }

        if (!highlightedTeam.equals("4586")) {
            if (tvR1.getText().toString().equals(this.highlightedTeam)) {
                tvR1.setTextColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvR2.getText().toString().equals(this.highlightedTeam)) {
                tvR2.setTextColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvR3.getText().toString().equals(this.highlightedTeam)) {
                tvR3.setTextColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvB1.getText().toString().equals(this.highlightedTeam)) {
                tvB1.setTextColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvB2.getText().toString().equals(this.highlightedTeam)) {
                tvB2.setTextColor(context.getResources().getColor(R.color.highlightedTeam));
            } else if (tvB3.getText().toString().equals(this.highlightedTeam)) {
                tvB3.setTextColor(context.getResources().getColor(R.color.highlightedTeam));
            }
        }

        if (!enable) {
            if (match.getGameNum() <= User.liveMatch) {
                ImageView imgMarked = view.findViewById(R.id.imgMarked);
                imgMarked.setImageResource(R.drawable.ic_check);
            }
        }

        return view;
    }
}
