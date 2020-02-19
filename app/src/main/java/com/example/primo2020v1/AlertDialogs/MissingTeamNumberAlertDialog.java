package com.example.primo2020v1.AlertDialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MissingTeamNumberAlertDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Missing Team Number")
                .setTitle("Error TeamNumberMissing")
                .setNegativeButton("Ok", (dialogInterface, i) -> {
                });

        return builder.create();
    }
}
