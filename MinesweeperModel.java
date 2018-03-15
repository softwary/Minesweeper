package ait.hu.minesweeper.Model;

import android.util.Log;

import java.util.Random;

public class MinesweeperModel {

    public int BOARD_SIZE = 5;
    Field model[][] = new Field[BOARD_SIZE][BOARD_SIZE];
    private int random1sti;
    private int random1stj;
    private int random2ndi;
    private int random2ndj;
    private int random3rdi;
    private int random3rdj;
    private boolean flagMode = false;


    private boolean activatedGame = true;

    private static MinesweeperModel instance = null;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    private MinesweeperModel() {
        setupFullBoard();
    }

    public boolean isFlagMode() {
        return this.flagMode;
    }

    public void setFlagMode(boolean flagMode) {
        this.flagMode = flagMode;
    }

    public boolean isActivatedGame() {
        return this.activatedGame;
    }

    public void setActivatedGame(boolean activatedGame) {
        this.activatedGame = activatedGame;
    }

    public Field getFieldContent(short x, short y) {
        return model[x][y];
    }


    public void setupEmptyBoard(int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                model[i][j] = new Field();
            }
        }
        activatedGame = true;
    }

    public void generateRandomNumbers() {
        Random seed = new Random();
        random1sti = seed.nextInt(BOARD_SIZE);
        random1stj = seed.nextInt(BOARD_SIZE);
        random2ndi = seed.nextInt(BOARD_SIZE);
        random2ndj = seed.nextInt(BOARD_SIZE);
        random3rdi = seed.nextInt(BOARD_SIZE);
        random3rdj = seed.nextInt(BOARD_SIZE);
    }

    public boolean isMineInNorthWestCorner(int i, int j) {
        if (model[i - 1][j - 1].isMine() && i - 1 >= 0 && j - 1 >= 0 && i - 1 < BOARD_SIZE && j - 1 < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineInNorthEastCorner(int i, int j) {
        if (model[i - 1][j + 1].isMine() && i - 1 >= 0 && j + 1 >= 0 && i - 1 < BOARD_SIZE && j + 1 < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineInSouthWestCorner(int i, int j) {
        if (model[i + 1][j - 1].isMine() && i + 1 >= 0 && j - 1 >= 0 && i + 1 < BOARD_SIZE && j - 1 < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineInSouthEastCorner(int i, int j) {
        if (model[i + 1][j + 1].isMine() && i + 1 >= 0 && j + 1 >= 0 && i + 1 < BOARD_SIZE && j + 1 < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineEast(int i, int j) {
        if (model[i][j + 1].isMine() && i >= 0 && j + 1 >= 0 && i < BOARD_SIZE && j + 1 < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineWest(int i, int j) {
        String notFlagMode = "THIS IS THE VALUE OF I AND J";
        Log.i(notFlagMode, i + ", j:" + j);
        if (model[i][j - 1].isMine() && i >= 0 && j - 1 >= 0 && i < BOARD_SIZE && j - 1 < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineNorth(int i, int j) {
        if (model[i - 1][j].isMine() && i - 1 >= 0 && j >= 0 && i - 1 < BOARD_SIZE && j < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public boolean isMineSouth(int i, int j) {
        if (model[i + 1][j].isMine() && i + 1 >= 0 && j >= 0 && i + 1 < BOARD_SIZE && j < BOARD_SIZE) {
            return true;
        } else
            return false;
    }

    public void setUpMinesAroundCount() {
        int mineNeighbors = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; i++) {
                if (isMineEast(i, j)) {
                    mineNeighbors++;
                }
                if (isMineWest(i, j)) {
                    mineNeighbors++;
                }
                if (isMineInNorthEastCorner(i, j)) {
                    mineNeighbors++;
                }
                if (isMineInNorthWestCorner(i, j)) {
                    mineNeighbors++;
                }
                if (isMineInSouthEastCorner(i, j)) {
                    mineNeighbors++;
                }
                if (isMineInSouthWestCorner(i, j)) {
                    mineNeighbors++;
                }
                if (isMineSouth(i, j)) {
                    mineNeighbors++;
                }
                if (isMineNorth(i, j)) {
                    mineNeighbors++;
                }
                model[i][j].setMinesAround(mineNeighbors);
            }
        }
    }

    public void setupFullBoard() {
        activatedGame = true;
        setupEmptyBoard(BOARD_SIZE, BOARD_SIZE);
        generateRandomNumbers();
        model[random1sti][random1stj].setMine();
        model[random2ndi][random2ndj].setMine();
        model[random3rdi][random3rdj].setMine();
        setUpMinesAroundCount();
    }

    public void setGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; i++) {
                model[i][j].setWasClicked(true);
            }
        }
        activatedGame = false;
    }
}
