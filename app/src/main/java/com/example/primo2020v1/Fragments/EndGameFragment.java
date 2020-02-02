package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.Keys;

public class EndGameFragment extends Fragment implements View.OnClickListener {
    private Intent egIntent;
    private EndGameListener listener;
    private ImageView imgReplace;
    private int[] images = {R.drawable.ic_empty, R.drawable.ic_park, R.drawable.ic_climb, R.drawable.ic_balance};
    //0: nothing; 1: parked; 2: climbed; 3: balanced
    public static int imageIndex;

    public interface EndGameListener {
        void setDataEndGame(Intent egIntent);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.end_game, container, false);

        imgReplace = v.findViewById(R.id.imgPark);
        setImage();
        imgReplace.setOnClickListener(this);

        egIntent = new Intent(getContext(), GameFormActivity.class);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgPark:
                imageIndex++;
                 imageIndex %= images.length;
                setImage();
                break;

                default:
        }
    }

    public void setImage(){
       imgReplace.setImageResource(images[imageIndex]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (EndGameListener) context;
        } catch (ClassCastException e){
            throw new RuntimeException(context.toString() +
                    " must implement EndGameListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        placeInfo();
        listener = null;
    }

    public void placeInfo(){
        egIntent.putExtra(Keys.EG_IMG_ID, images[imageIndex]);
        listener.setDataEndGame(egIntent);
    }
}
