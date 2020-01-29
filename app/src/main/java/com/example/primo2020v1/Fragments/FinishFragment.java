package com.example.primo2020v1.Fragments;

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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FinishFragment extends Fragment implements View.OnClickListener {
    public interface FinishListener{
        void getDataFinish(Intent finishIntent);
        FormInfo setFormInfo();
        ArrayList<Cycle> getCyclesFinish();
    }

    FinishListener listener;
    public static int imageIndex = 0;
    public static CharSequence text = "";
    private ImageView imgReplace;
    private EditText edExtraInfo;
    private Button btnToSubmission;
    private Intent finishIntent, intent;

    private int[] images = {R.drawable.ic_won, R.drawable.ic_lost, R.drawable.ic_draw};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish, container, false);

        edExtraInfo = v.findViewById(R.id.edExtaInfo);
        imgReplace = v.findViewById(R.id.imgFinish);
        btnToSubmission = v.findViewById(R.id.btnToSubmission);

        edExtraInfo.setText(text);
        setImage();
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

            case R.id.btnToSubmission:
                Log.d(TAG, "onClick: Submission!");
                placeInfo();

                intent = new Intent(getContext(), SubmissionActivity.class);
                FormInfo formInfo = listener.setFormInfo();
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
        imgReplace.setBackgroundColor(images[imageIndex]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (FinishListener) context;
        } catch (ClassCastException e){
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
        finishIntent.putExtra(Keys.FINISH_TEXT, text);
        listener.getDataFinish(finishIntent);
    }
}
