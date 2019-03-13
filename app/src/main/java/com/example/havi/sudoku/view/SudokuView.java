package com.example.havi.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.havi.sudoku.puzzle.GridCell;
import com.example.havi.sudoku.puzzle.SudokuPuzzle;

/**
 * Created by havi on 2017-11-22.
 */

public class SudokuView extends View {

    private int size;
    private SudokuPuzzle puzzle;

    private int selectedX = -1, selectedY = -1, selectedValue = 0;

    private Paint textPaint;
    private Paint gridPaint;
    private Paint linePaint;

    public SudokuView(Context context) {
        super(context);
        init(null);
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.LTGRAY);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (puzzle == null)
            return;

        int rectSize = getRectSize();

        for (int y = 0; y < puzzle.getSize(); y++) {
            for (int x = 0; x < puzzle.getSize(); x++) {

                // Set color of square
                if (x == selectedX && y == selectedY) {
                    if (puzzle.isMarked(x, y))
                        gridPaint.setColor(Color.rgb(200,220,170));
                    else
                        gridPaint.setColor(Color.rgb(150,200,255));
                }
                else if (selectedValue > 0 && puzzle.getCellValue(x, y) == selectedValue)
                    gridPaint.setColor(Color.rgb(150, 200, 150));
                else if (!puzzle.isChangable(x, y))
                    gridPaint.setColor(Color.rgb(180, 180, 180));
                else if (puzzle.isMarked(x, y))
                    gridPaint.setColor(Color.rgb(200, 200, 140));
                else
                    gridPaint.setColor(Color.rgb(200, 200, 200));

                // Draw square
                int startX = rectSize*x; int startY = rectSize*y;
                canvas.drawRect(startX, startY, startX+rectSize-3, startY+rectSize-3, gridPaint);

                // Draw text in square
                int num = puzzle.getCellValue(x, y);
                if (num > 0) {
                    canvas.drawText(Integer.toString(num), startX+(rectSize/2.2f), startY+(rectSize/1.3f), textPaint);
                }

            }
        }

        int boxWidth = puzzle.getBoxWidth();
        int boxHeight = puzzle.getBoxHeight();

        // draw vertical lines
        for (int i = 1; i <= (puzzle.getSize()/boxWidth)-1; i++) {
            float startX = (rectSize*boxWidth) * i, startY = 0;
            float stopX = startX, stopY = canvas.getHeight();
            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        }

        // draw horizontal lines
        for (int i = 1; i <= (puzzle.getSize()/boxHeight)-1; i++) {
            float startX = 0, startY = (rectSize*boxHeight) * i;
            float stopX = canvas.getWidth(), stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (puzzle == null)
            return false;

        int rectSize = getRectSize();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int touchX = (int) event.getX();
            int touchY = (int) event.getY();

            // Get selected x and y
            for (int y = 0; y < puzzle.getSize(); y++) {
                for (int x = 0; x < puzzle.getSize(); x++) {
                    if (touchX > x*rectSize && touchX < x*rectSize+rectSize)
                        selectedX = x;
                    if (touchY > y*rectSize && touchY < y*rectSize+rectSize)
                        selectedY = y;
                }
            }

            updateSelectedValue();

            invalidate();

            return true;
        }
        return false;
    }

    private void updateSelectedValue() {
        if (selectedX >= 0 && selectedY >= 0)
            selectedValue = puzzle.getCellValue(selectedX, selectedY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        size = w;

        if (puzzle == null)
            return;

        int textSize = (int) (size/ puzzle.getSize()*0.75f);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void newPuzzle(GridCell[][] grid) {
        puzzle = new SudokuPuzzle(grid);
        invalidate();
    }

    public GridCell[][] getPuzzleGrid() {
        return puzzle.getGrid();
    }

    public int getPuzzleSize() {
        if (puzzle == null)
            return 0;
        return puzzle.getSize();
    }

    public boolean setSelectedCellValue(int value) {
        if (puzzle == null)
            return false;
        boolean result = puzzle.setCellValue(selectedX, selectedY, value);
        if (result) {
            updateSelectedValue();
            invalidate();
        }
        return result;
    }

    public boolean validate() {
        return puzzle.validate();
    }

    private int getRectSize() {
        if (puzzle == null)
            return 0;
        return size/ puzzle.getSize();
    }

    public void setMarked(boolean marked) {
        if (selectedX >= 0 && selectedY >= 0) {
            puzzle.setMarked(selectedX, selectedY, marked);
            invalidate();
        }
    }

}
