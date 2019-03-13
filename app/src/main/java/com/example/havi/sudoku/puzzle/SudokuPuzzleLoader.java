package com.example.havi.sudoku.puzzle;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by havi on 2017-11-24.
 */

public class SudokuPuzzleLoader {

    private static final String FILENAME_4 = "puzzles4x4";
    private static final String FILENAME_6 = "puzzles6x6";
    private static final String FILENAME_9 = "puzzles9x9";

    private Context context;

    public SudokuPuzzleLoader(Context context) {
        this.context = context;
    }

    public GridCell[][] getDefaultPuzzle(PuzzleType type) {
        switch (type) {
            case SMALL: return getDefault4();
            case MEDIUM: return getDefault6();
            case LARGE: return getDefault9();
        }
        return null;
    }

    public GridCell[][] getEmptyPuzzleGrid(PuzzleType type) {
        int size = 9;
        switch (type) {
            case SMALL:
                size = 4;
                break;
            case MEDIUM:
                size = 6;
                break;
        }
        GridCell[][] grid = new GridCell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = new GridCell();
            }
        }
        return grid;
    }

    public boolean save(GridCell[][] grid) {

        String filename = "";
        switch (grid.length) {
            case 4:
                filename = FILENAME_4;
                break;
            case 6:
                filename = FILENAME_6;
                break;
            case 9:
                filename = FILENAME_9;
                break;
            default:
                return false;
        }

        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println(serialize(grid));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public GridCell[][] getRandomPuzzle(PuzzleType type) {

        String filename = "";
        switch (type) {
            case SMALL:
                filename = FILENAME_4;
                break;
            case MEDIUM:
                filename = FILENAME_6;
                break;
            case LARGE:
                filename = FILENAME_9;
                break;
        }

        String data = "";

        try {

            FileInputStream inputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            List<String> lines = new ArrayList<>();

            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            reader.close();
            inputStreamReader.close();
            inputStream.close();

            if (lines.size() > 0) {
                Random random = new Random();
                data = lines.get(random.nextInt(lines.size()));
            } else {
                return getDefaultPuzzle(type);
            }


        } catch (IOException e) {
            e.printStackTrace();
            return getDefaultPuzzle(type);
        }

        return deserialize(data);
    }

    private String serialize(GridCell[][] grid) {
        int size = grid.length;
        String out = "";
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                out += Integer.toString(grid[x][y].getValue());
            }
        }
        return out;
    }

    private GridCell[][] deserialize(String data) {
        String[] dataArray = data.split("");
        dataArray = Arrays.copyOfRange(dataArray, 1, dataArray.length);
        int size;
        switch (dataArray.length) {
            case 16:
                size = 4;
                break;
            case 36:
                size = 6;
                break;
            case 81:
                size = 9;
                break;
            default:
                return null;
        }
        GridCell[][] grid = new GridCell[size][size];
        int index = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                try {
                    int number = Integer.parseInt(dataArray[index]);
                    grid[x][y] = new GridCell(number, number == 0);
                    index++;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return grid;
    }

    private GridCell[][] getDefault4() {
        return deserialize("4020020404310340");
    }

    private GridCell[][] getDefault6() {
        return deserialize("000004602050104560000142203005450036");
    }

    private GridCell[][] getDefault9() {
        return deserialize("060430709350001006090060102009056300703080690006094210002805971905013804418600003");
    }

}
