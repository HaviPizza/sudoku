package com.example.havi.sudoku.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.havi.sudoku.R;
import com.example.havi.sudoku.puzzle.PuzzleType;

/**
 * Created by havi on 2017-11-24.
 */

public class SelectPuzzleTypeDialog extends DialogFragment {

    public interface SelectPuzzleTypeDialogListener {
        public void onOK(PuzzleType type);
    }

    SelectPuzzleTypeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SelectPuzzleTypeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SelectPuzzleTypeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_type_dialog)
                .setSingleChoiceItems(R.array.puzzleTypes, 2, null)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int selected = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                        listener.onOK(PuzzleType.values()[selected]);
                    }
                })
                .setNegativeButton(R.string.button_cancel, null);
        return builder.create();
    }

}
