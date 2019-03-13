package com.example.havi.sudoku.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.havi.sudoku.R;
import com.example.havi.sudoku.activity.MainActivity;

/**
 * Created by havi on 2017-11-26.
 */

public class WinDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        int time = getArguments().getInt("time", 0);
        String message = getResources().getString(R.string.time) + ": " + time + " " + getString(R.string.seconds);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sudoku_win)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getContext(), MainActivity.class));
                    }
                });
        return builder.create();
    }

}