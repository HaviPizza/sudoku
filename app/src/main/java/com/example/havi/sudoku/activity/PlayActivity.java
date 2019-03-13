package com.example.havi.sudoku.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.havi.sudoku.puzzle.PuzzleType;
import com.example.havi.sudoku.puzzle.GridCell;
import com.example.havi.sudoku.R;
import com.example.havi.sudoku.puzzle.SudokuPuzzleLoader;
import com.example.havi.sudoku.view.SudokuFragment;
import com.example.havi.sudoku.view.WinDialog;

public class PlayActivity extends AppCompatActivity {

    private SudokuFragment sudokuFragment;
    private SudokuPuzzleLoader puzzleLoader;

    private PuzzleType currentType;

    private TextView timerText;
    private int time = 0;
    private Handler timerHandler;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            timerText.setText(Integer.toString(++time));
            timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        puzzleLoader = new SudokuPuzzleLoader(this);

        Intent intent = getIntent();
        currentType = PuzzleType.values()[intent.getIntExtra("type", 2)];

        sudokuFragment = (SudokuFragment) getFragmentManager().findFragmentById(R.id.fragment_sudoku);
        setPuzzle();

        findViewById(R.id.button_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFinish();
            }
        });
        findViewById(R.id.button_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNew();
            }
        });

        timerText = findViewById(R.id.txt_time);
        startTimer();
    }

    private void startTimer() {
        timerHandler = new Handler();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void setPuzzle() {
        GridCell[][] grid = puzzleLoader.getRandomPuzzle(currentType);
        sudokuFragment.newPuzzle(grid);
    }

    private void onClickFinish() {
        boolean valid = sudokuFragment.validate();
        if (valid) {
            timerHandler.removeCallbacks(timerRunnable);
            DialogFragment dialog = new WinDialog();
            Bundle args = new Bundle();
            args.putInt("time", time);
            dialog.setArguments(args);
            dialog.show(getFragmentManager(), "win");
        } else {
            Toast.makeText(this, R.string.sudoku_invalid, Toast.LENGTH_LONG).show();
        }
    }

    private void onClickNew() {
        GridCell[][] grid = puzzleLoader.getRandomPuzzle(currentType);
        sudokuFragment.newPuzzle(grid);
        time = 0;
    }

}
