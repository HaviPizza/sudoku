package com.example.havi.sudoku.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.havi.sudoku.puzzle.PuzzleType;
import com.example.havi.sudoku.R;
import com.example.havi.sudoku.view.SelectPuzzleTypeDialog;

/**
 * Created by havi on 2017-11-23.
 */

public class MainActivity extends AppCompatActivity implements SelectPuzzleTypeDialog.SelectPuzzleTypeDialogListener {

    private boolean createPuzzle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPlay();
            }
        });
        findViewById(R.id.button_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreate();
            }
        });
        findViewById(R.id.button_manual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickManual();
            }
        });
    }

    private void onClickPlay() {
        createPuzzle = false;
        DialogFragment dialog = new SelectPuzzleTypeDialog();
        dialog.show(getFragmentManager(), "types");
    }

    private void onClickCreate() {
        createPuzzle = true;
        DialogFragment dialog = new SelectPuzzleTypeDialog();
        dialog.show(getFragmentManager(), "types");
    }

    private void onClickManual() {
        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
    }

    @Override
    public void onOK(PuzzleType type) {
        Intent intent = new Intent(this, createPuzzle ? CreateActivity.class : PlayActivity.class);
        intent.putExtra("type", type.ordinal());
        startActivity(intent);
    }

}
