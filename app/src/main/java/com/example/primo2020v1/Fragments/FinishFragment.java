package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.SubmissionActivity;
import com.example.primo2020v1.libs.Cycle;
import com.example.primo2020v1.libs.FormInfo;
import com.example.primo2020v1.libs.Keys;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FinishFragment extends Fragment implements View.OnClickListener {
    public interface FinishListener{
        void setDataFinish(Intent finishIntent);
        FormInfo getFormInfo();
        ArrayList<Cycle> getCyclesFinish();
    }

    FinishListener listener;
    public static int imageIndex, ticketIndex;
    public static CharSequence text;
    public static boolean didCrash;
    private ImageView imgReplace, imgTicket, imgCrash;
    private EditText edExtraInfo;
    private Button btnToSubmission;
    private Intent finishIntent, intent;

    private int[] images = {R.drawable.ic_won, R.drawable.ic_lost, R.drawable.ic_draw};
    private int[] tickets = {Color.BLACK, Color.YELLOW, Color.RED};
    private int[] crash = {Color.RED, Color.GREEN};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish, container, false);

        edExtraInfo = v.findViewById(R.id.edExtaInfo);
        imgReplace = v.findViewById(R.id.imgFinish);
        imgTicket = v.findViewById(R.id.imgTicket);
        imgCrash = v.findViewById(R.id.imgCrash);
        btnToSubmission = v.findViewById(R.id.btnToSubmission);

        edExtraInfo.setText(text);
        setCrash();
        setImage();
        setTicket();

        imgCrash.setOnClickListener(this);
        imgTicket.setOnClickListener(this);
        imgReplace.setOnClickListener(this);
        btnToSubmission.setOnClickListener(this);

        finishIntent = new Intent(getContext(), GameFormActivity.class);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgFinish:
                imageIndex++;
                imageIndex %= images.length;
                setImage();
                break;

            case R.id.imgTicket:
                ticketIndex++;
                ticketIndex %= tickets.length;
                setTicket();
                break;

            case R.id.imgCrash:
                didCrash = !didCrash;
                setCrash();
                break;

            case R.id.btnToSubmission:
                Log.d(TAG, "onClick: Submission!");
                placeInfo();

                intent = new Intent(getContext(), SubmissionActivity.class);
                FormInfo formInfo = listener.getFormInfo();
                ArrayList<Cycle> cycles = listener.getCyclesFinish();
                Log.d(TAG, "onClick: FinishFragment" + formInfo.toString());

                intent.putExtra(Keys.FORM_INFO, formInfo);
                intent.putExtra(Keys.FINISH_PC, cycles);

                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void setImage(){
        imgReplace.setImageDrawable(getResources().getDrawable(images[imageIndex]));
    }

    public void setTicket() {
        imgTicket.setColorFilter(tickets[ticketIndex]);
    }

    public void setCrash() {
        imgCrash.setColorFilter(didCrash ? crash[1] : crash[0]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (FinishListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString() +
                    " must implement FinishListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        placeInfo();
        listener = null;
    }

    private void placeInfo(){
        text = edExtraInfo.getText().toString().trim();
        finishIntent.putExtra(Keys.FINISH_TEAM, images[imageIndex]);
        finishIntent.putExtra(Keys.FINISH_TICKET, tickets[ticketIndex]);
        finishIntent.putExtra(Keys.FINISH_CRASH, didCrash);
        finishIntent.putExtra(Keys.FINISH_TEXT, text);
        listener.setDataFinish(finishIntent);
    }
}
