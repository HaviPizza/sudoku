package com.example.havi.sudoku.puzzle;

public class GridCell {
    private int value;
    private boolean changeable;
    private boolean marked = false;

    public GridCell() {
        this.value = 0;
        changeable = true;
    }

    public GridCell(int value) {
        this.value = value;
        this.changeable = false;
    }


    public GridCell(int value, boolean changeable) {
        this.value = value;
        this.changeable = changeable;
    }

    public boolean setValue(int newValue) {
        if (changeable) {
            this.value = newValue;
            return true;
        }
        return false;
    }

    public int getValue() {
        return value;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

}
