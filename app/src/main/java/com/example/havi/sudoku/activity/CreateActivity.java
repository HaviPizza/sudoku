package com.example.havi.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.havi.sudoku.puzzle.PuzzleType;
import com.example.havi.sudoku.puzzle.GridCell;
import com.example.havi.sudoku.R;
import com.example.havi.sudoku.puzzle.SudokuPuzzleLoader;
import com.example.havi.sudoku.view.SudokuFragment;

/**
 * Created by havi on 2017-11-23.
 */

public class CreateActivity extends AppCompatActivity {

    private SudokuFragment sudokuFragment;
    private SudokuPuzzleLoader puzzleLoader;
    private PuzzleType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Intent intent = getIntent();
        type = PuzzleType.values()[intent.getIntExtra("type", 2)];

        sudokuFragment = (SudokuFragment) getFragmentManager().findFragmentById(R.id.fragment_sudoku);

        puzzleLoader = new SudokuPuzzleLoader(this);
        sudokuFragment.newPuzzle(puzzleLoader.getEmptyPuzzleGrid(type));

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSave();
            }
        });
        findViewById(R.id.button_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickReset();
            }
        });
    }

    private void onClickSave() {
        GridCell[][] grid = sudokuFragment.getPuzzleGrid();
        boolean result = puzzleLoader.save(grid);
        if (result)
            Toast.makeText(this, R.string.sudoku_saved, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void onClickReset() {
        Log.i("CREATE", "reset");
        sudokuFragment.newPuzzle(puzzleLoader.getEmptyPuzzleGrid(type));
    }

}
