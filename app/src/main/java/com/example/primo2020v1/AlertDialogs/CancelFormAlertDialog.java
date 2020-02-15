package com.example.primo2020v1.AlertDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.primo2020v1.DrawerActivity;
import com.example.primo2020v1.libs.GeneralFunctions;

public class CancelFormAlertDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Exiting will delete the information")
                .setTitle("Exit")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                        GeneralFunctions.resetForm();
                        Intent in = new Intent(getActivity().getApplicationContext(), DrawerActivity.class);
                        startActivity(in);
                    }
                })
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });

        return builder.create();
    }
}
