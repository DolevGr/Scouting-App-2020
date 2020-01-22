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
    private ArrayList<Match> arrList;

    public GamesAdapter(Context context, int id, ArrayList<Match> lst) {
        super(context, id, lst);

        this.context = context;
        this.res = id;
        arrList = lst;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,   @NonNull ViewGroup parent) {
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

        return view;
    }
}
