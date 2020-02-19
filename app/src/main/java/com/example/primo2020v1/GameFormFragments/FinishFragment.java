package com.example.primo2020v1.GameFormFragments;

import android.content.Context;
import android.content.Intent;
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
import com.example.primo2020v1.libs.User;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FinishFragment extends Fragment implements View.OnClickListener {
    public interface FinishListener{
        void setDataFinish(Intent finishIntent);
        FormInfo getFormInfo();
        ArrayList<Cycle> getCyclesFinish();
    }

    FinishListener listener;
    public static int finishIndex, ticketIndex, crashIndex, defIndex;
    public static CharSequence text;
    private ImageView imgFinish, imgTicket, imgCrash, imgDefence;
    private EditText edExtraInfo;
    private Button btnToSubmission;
    private Intent finishIntent, intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish, container, false);

        edExtraInfo = v.findViewById(R.id.edExtaInfo);
        imgFinish = v.findViewById(R.id.imgFinishFinish);
        imgTicket = v.findViewById(R.id.imgTicketFinish);
        imgCrash = v.findViewById(R.id.imgCrashFinish);
        imgDefence = v.findViewById(R.id.imgDefenceFinish);
        btnToSubmission = v.findViewById(R.id.btnToSubmission);

        edExtraInfo.setText(text);

        setImageFinish();
        setImageTicket();
        setImageCrash();
        setImageDefence();

        imgDefence.setOnClickListener(this);
        imgCrash.setOnClickListener(this);
        imgTicket.setOnClickListener(this);
        imgFinish.setOnClickListener(this);
        btnToSubmission.setOnClickListener(this);

        finishIntent = new Intent(getContext(), GameFormActivity.class);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgFinishFinish:
                finishIndex++;
                finishIndex %= User.finishImages.length;
                setImageFinish();
                break;

            case R.id.imgTicketFinish:
                ticketIndex++;
                ticketIndex %= User.finishTickets.length;
                setImageTicket();
                break;

            case R.id.imgCrashFinish:
                crashIndex++;
                crashIndex %= User.finishCrash.length;
                setImageCrash();
                break;

            case R.id.imgDefenceFinish:
                defIndex++;
                defIndex %= User.finishDefence.length;
                setImageDefence();
                break;

            case R.id.btnToSubmission:
                placeInfo();

                intent = new Intent(getContext(), SubmissionActivity.class);
                FormInfo formInfo = listener.getFormInfo();
                ArrayList<Cycle> cycles = listener.getCyclesFinish();

                intent.putExtra(Keys.FORM_INFO, formInfo);
                intent.putExtra(Keys.FINISH_PC, cycles);

                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void setImageTicket(){
        imgTicket.setImageResource(User.finishTickets[ticketIndex]);
    }

    private void setImageCrash(){
        imgCrash.setImageResource(User.finishCrash[crashIndex]);
    }

    private void setImageDefence() {
        imgDefence.setImageResource(User.finishDefence[defIndex]);
    }

    private void setImageFinish(){
        imgFinish.setImageResource(User.finishImages[finishIndex]);
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
        finishIntent.putExtra(Keys.FINISH_TEAM, finishIndex);
        finishIntent.putExtra(Keys.FINISH_TICKET, ticketIndex);
        finishIntent.putExtra(Keys.FINISH_CRASH, crashIndex);
        finishIntent.putExtra(Keys.FINISH_DEFENCE, defIndex);
        finishIntent.putExtra(Keys.FINISH_TEXT, text);
        listener.setDataFinish(finishIntent);
    }
}
