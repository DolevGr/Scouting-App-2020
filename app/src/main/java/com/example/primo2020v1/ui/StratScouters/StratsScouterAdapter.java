package com.example.primo2020v1.ui.StratScouters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StratsScouterAdapter extends ArrayAdapter {
    private Context context;
    private int resources,
        matchNumber, teamNumber;
    private boolean isNewComment;

    public StratsScouterAdapter(@NonNull Context context, int resource, ArrayList single, int matchNumber, int teamNumber, boolean isNewComment) {
        super(context, resource, single);
        this.context = context;
        this.resources = resource;

        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
        this.isNewComment = isNewComment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resources, null);

        if (isNewComment) {
            Button btnSubmit = view.findViewById(R.id.btnSubmitStrats);
            EditText edComment = view.findViewById(R.id.edCStratsNewComment);

            btnSubmit.setOnClickListener(v -> {
                DatabaseReference dbRef = User.databaseReference.child(Keys.SCOUTER_COMMENTS);
                dbRef.child(Integer.toString(teamNumber)).child(Integer.toString(matchNumber)).setValue(edComment.getText().toString().trim());
                Toast.makeText(context, "Sent Comment", Toast.LENGTH_SHORT).show();
                edComment.setText("");
            });
        } else {
            EditText edComment = view.findViewById(R.id.edCStratsNewComment);
            edComment.setEnabled(false);
            view.findViewById(R.id.btnSubmitStrats).setVisibility(View.INVISIBLE);

            DatabaseReference dbRef = User.databaseReference.child(Keys.SCOUTER_COMMENTS);
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(Integer.toString(teamNumber))) {
                        DataSnapshot dsTeamNumber = dataSnapshot.child(Integer.toString(teamNumber));
                        if (dsTeamNumber.hasChild(Integer.toString(matchNumber))) {
                            DataSnapshot dsMatchNumber = dsTeamNumber.child(Integer.toString(matchNumber));
                            edComment.setText(dsMatchNumber.getValue().toString().trim());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        return view;
    }
}
