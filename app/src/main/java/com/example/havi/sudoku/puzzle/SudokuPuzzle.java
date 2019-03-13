package com.example.havi.sudoku.puzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by havi on 2017-11-22.
 */

public class SudokuPuzzle {

    private PuzzleType type;
    private int size;
    private GridCell[][] grid;

    public SudokuPuzzle(GridCell[][] grid) {
        switch (grid.length) {
            case 4:
                type = PuzzleType.SMALL;
                break;
            case 6:
                type = PuzzleType.MEDIUM;
                break;
            case 9:
                type = PuzzleType.LARGE;
                break;
        }

        this.grid = grid;
        setSize();
    }

    public GridCell[][] getGrid() {
        return grid;
    }

    public boolean setCellValue(int x, int y, int value) {
        if (x >= 0 && x < size && y >= 0 && y < size)
            return grid[x][y].setValue(value);
        return false;
    }

    public int getCellValue(int x, int y) {
        return (grid[x][y] != null) ? grid[x][y].getValue() : 0;
    }

    public int getSize() {
        return size;
    }

    public PuzzleType getType() {
        return type;
    }

    public boolean validate() {

        // validate rows
        for (int y = 0; y < size; y++) {
            Set<Integer> numbers = new HashSet<>();
            for (int x = 0; x < size; x++) {
                int number = grid[x][y].getValue();
                if (number == 0 || numbers.contains(number))
                    return false;
                numbers.add(grid[x][y].getValue());
            }
        }

        // validate columns
        for (int x = 0; x < size; x++) {
            Set<Integer> numbers = new HashSet<>();
            for (int y = 0; y < size; y++) {
                int number = grid[x][y].getValue();
                if (numbers.contains(number))
                    return false;
                numbers.add(grid[x][y].getValue());
            }
        }

        // validate boxes
        for (int x = 0; x < size; x += getBoxWidth()) {
            for (int y = 0; y < size; y += getBoxHeight()) {
                Set<Integer> numbers = new HashSet<>();
                for (int i = 0; i < getBoxWidth(); i++) {
                    for (int j = 0; j < getBoxHeight(); j++) {
                        int number = grid[x+i][y+j].getValue();
                        if (numbers.contains(number))
                            return false;
                        numbers.add(grid[x+i][y+j].getValue());
                    }
                }
            }
        }

        return true;
    }

    public int getBoxWidth() {
        if (type == PuzzleType.SMALL)
            return 2;
        return 3;
    }

    public int getBoxHeight() {
        if (type == PuzzleType.LARGE)
            return 3;
        return 2;
    }

    private void setSize() {
        switch (type) {
            case SMALL:
                this.size = 4;
                return;
            case MEDIUM:
                this.size = 6;
                return;
            case LARGE:
                this.size = 9;
                return;
        }
    }

    public boolean isChangable(int x, int y) {
        return grid[x][y].isChangeable();
    }

    public void setMarked(int x, int y, boolean marked) {
        if (isChangable(x, y))
            grid[x][y].setMarked(marked);
    }

    public boolean isMarked(int x, int y) {
        return grid[x][y].isMarked();
    }

}

