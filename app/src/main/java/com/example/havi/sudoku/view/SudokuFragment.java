package com.example.havi.sudoku.view;


import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.havi.sudoku.R;
import com.example.havi.sudoku.puzzle.GridCell;

public class SudokuFragment extends Fragment {

    private SudokuView sudokuView;
    private LinearLayout numButtonsView;

    public SudokuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sudoku, container, false);

        sudokuView = (SudokuView) view.findViewById(R.id.sudokuView);
        numButtonsView = (LinearLayout) view.findViewById(R.id.layout_num_buttons);

        view.findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sudokuView.setSelectedCellValue(0);
            }
        });

        view.findViewById(R.id.button_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sudokuView.setMarked(true);
            }
        });
        view.findViewById(R.id.button_unmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sudokuView.setMarked(false);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void addNumButtons(View view) {
        int puzzleSize = sudokuView.getPuzzleSize();

        // Calculate width for buttons
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels/puzzleSize;

        // Add buttons
        for (int i = 0; i < puzzleSize; i++) {
            Button button = new Button(getActivity());
            button.setText(Integer.toString(i+1));
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = width;
            button.setLayoutParams(layoutParams);

            final int value = i+1;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sudokuView.setSelectedCellValue(value);
                }
            });

            numButtonsView.addView(button);
        }
    }

    public void newPuzzle(GridCell[][] grid) {
        sudokuView.newPuzzle(grid);
        addNumButtons(getView());
    }

    public GridCell[][] getPuzzleGrid() {
        return sudokuView.getPuzzleGrid();
    }

    public boolean validate() {
        return sudokuView.validate();
    }

}
