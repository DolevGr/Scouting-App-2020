package com.example.primo2020v1.ui.StratScouters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class StratScouterAdapter extends ArrayAdapter {
    private Context context;
    private int resources,
            matchNumber, teamNumber;
    private ArrayList<String> comments;

    public StratScouterAdapter(@NonNull Context context, int resource, ArrayList comments,
                               int teamNumber) {
        super(context, resource, comments);
        this.context = context;
        this.resources = resource;

        this.comments = comments;
        this.teamNumber = teamNumber;
    }

    public StratScouterAdapter(@NonNull Context context, int resource, ArrayList comments,
                               int matchNumber, int teamNumber) {
        super(context, resource, comments);
        this.context = context;
        this.resources = resource;

        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resources, null);

        TextView tvComment = view.findViewById(R.id.tvScouterCommentStrats);
        EditText edComment = view.findViewById(R.id.edCStratsNewComment);
        Button btnSubmit = view.findViewById(R.id.btnSubmitStrats);

        if (matchNumber > 0) {
            tvComment.setVisibility(View.INVISIBLE);

            btnSubmit.setOnClickListener(v -> {
                if (!edComment.getText().toString().trim().equals("")) {
                    DatabaseReference dbRef = User.databaseReference.child(Keys.SCOUTER_COMMENTS)
                            .child(Integer.toString(teamNumber)).child(Integer.toString(matchNumber));
                    dbRef.setValue(edComment.getText().toString().trim());
                    edComment.setText("");
                } else {
                    Toast.makeText(getContext(), "Comment is empty", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            edComment.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);

            tvComment.setText(comments.get(position));
        }

        return view;
    }
}
