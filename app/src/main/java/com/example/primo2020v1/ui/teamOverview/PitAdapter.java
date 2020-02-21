package com.example.primo2020v1.ui.teamOverview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.User;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PitAdapter extends ArrayAdapter {
    private Context context;
    private int res;
    private ArrayList<String> values;
    private ArrayList<TextView> tvs;

    public PitAdapter(@NonNull Context context, int resource, ArrayList<String> values, ArrayList<Integer> single) {
        super(context, resource, single);
        this.context = context;
        this.res = resource;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

        tvs = new ArrayList<>();
        tvs.add(view.findViewById(R.id.tvMass));
        tvs.add(view.findViewById(R.id.tvProgLanguagePit));
        tvs.add(view.findViewById(R.id.tvWheels));
        tvs.add(view.findViewById(R.id.tvIntakePit));
        tvs.add(view.findViewById(R.id.tvCarryPit));
        tvs.add(view.findViewById(R.id.tvShootPit));
        tvs.add(view.findViewById(R.id.tvCommentPit));
        Switch switchAuto = view.findViewById(R.id.switchAutoPit);
        Switch switchTrench = view.findViewById(R.id.switchTrenchPit);
        Switch switchBumpers = view.findViewById(R.id.switchBumpersPit);
        ImageView img1 = view.findViewById(R.id.imgEndGamePit);
        ImageView img2 = view.findViewById(R.id.imgPCPit);
        ImageView img3 = view.findViewById(R.id.imgRCPit);

        int i;
        for (i = 0; i < tvs.size(); i++) {
            tvs.get(i).setText(values.get(i));
        }

        switchAuto.setChecked(Boolean.parseBoolean(values.get(i++)));
        switchAuto.setEnabled(false);
        switchTrench.setChecked(Boolean.parseBoolean(values.get(i++)));
        switchTrench.setEnabled(false);
        switchBumpers.setChecked(Boolean.parseBoolean(values.get(i++)));
        switchBumpers.setEnabled(false);

        img1.setImageResource(User.endGameImages[Integer.parseInt(values.get(i++))]);
        Log.d(TAG, "getView: " + Integer.parseInt(values.get(i)));

        if (Integer.parseInt(values.get(i)) == 1) {
            img2.setImageResource(User.controlPanelRotation[Integer.parseInt(values.get(i++))]);
        } else {
            i++;
            img2.setVisibility(View.INVISIBLE);
        }

        if (Integer.parseInt(values.get(i)) == 1) {
            img3.setImageResource(User.controlPanelPosition[Integer.parseInt(values.get(i))]);
        } else {
            img3.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
