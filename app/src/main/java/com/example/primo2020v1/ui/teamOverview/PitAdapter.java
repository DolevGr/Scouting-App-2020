package com.example.primo2020v1.ui.teamOverview;

import android.content.Context;
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

public class PitAdapter extends ArrayAdapter {
    private Context context;
    private int res;
    private ArrayList<String> values;
    private ArrayList<TextView> tvs;

    public PitAdapter(@NonNull Context context, int resource, ArrayList<String> values) {
        super(context, resource, values);

        this.context = context;
        this.res = resource;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res, null);

        if (position == 0) {
            tvs = new ArrayList<>();
            tvs.add((TextView) view.findViewById(R.id.tvHeight));
            tvs.add((TextView) view.findViewById(R.id.tvMass));
            tvs.add((TextView) view.findViewById(R.id.tvWheels));
            tvs.add((TextView) view.findViewById(R.id.tv1));
            Switch s = view.findViewById(R.id.swtch1);
            ImageView img1 = view.findViewById(R.id.img1);
            ImageView img2 = view.findViewById(R.id.img2);
            ImageView img3 = view.findViewById(R.id.img3);

            int i;
            for (i = 0; i < tvs.size(); i++) {
                tvs.get(i).setText(values.get(i));
            }
            s.setChecked(Boolean.parseBoolean(values.get(i++)));
            s.setEnabled(false);
            img1.setImageResource(User.endGameImages[Integer.parseInt(values.get(i++))]);
            img2.setImageResource(User.controlPanelRotation[Integer.parseInt(values.get(i++))]);
            img3.setImageResource(User.controlPanelPosition[Integer.parseInt(values.get(i))]);

            return view;
        }

        view = new View(getContext());
        view.setVisibility(View.INVISIBLE);
        return view;
    }
}
