package com.example.primo2020v1.AlertDialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.primo2020v1.SubmissionActivity;

public class OverrideExistingMatchAlertDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Override existing match?")
                .setTitle("Match already exists in DataBase")
                .setPositiveButton("Yes", (dialogInterface, i) -> SubmissionActivity.isValid = true)
                .setNegativeButton("No", (dialogInterface, i) -> SubmissionActivity.isValid = false);

        return builder.create();
    }
}
