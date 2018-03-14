package ait.hu.minesweeper.Model;

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
        this.activatedGame = true;
        this.flagMode = false;
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

    public void setupBoardWithMines() {
        setupEmptyBoard(BOARD_SIZE, BOARD_SIZE);
        generateRandomNumbers();
        model[random1sti][random1stj].setMine();
        model[random2ndi][random2ndj].setMine();
        model[random3rdi][random3rdj].setMine();

    }

    public boolean isMineInNorthWestCorner() {
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (model[i - 1][j - 1].isMine()) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean isMineInNorthEastCorner() {
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (model[i - 1][j + 1].isMine()) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean isMineInSouthWestCorner() {
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (model[i + 1][j - 1].isMine()) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean isMineInSouthEastCorner() {
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (model[i + 1][j + 1].isMine()) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean isMineEast() {
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (model[i][j + 1].isMine()) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean isMineWest() {
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (model[i][j - 1].isMine()) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public int setUpMinesAroundCount() {
        int mineNeighbors = 0;
        for (int i = -1; i < 6; i++) {
            for (int j = -1; j < 6; i++) {
                if (isMineEast()) {
                    mineNeighbors++;
                }
                if (isMineWest()) {
                    mineNeighbors++;
                }
                if (isMineInNorthEastCorner()) {
                    mineNeighbors++;
                }
                if (isMineInNorthWestCorner()) {
                    mineNeighbors++;
                }
                if (isMineInSouthEastCorner()) {
                    mineNeighbors++;
                }
                if (isMineInSouthWestCorner()) {
                    mineNeighbors++;
                }
                model[i][j].setMinesAround(mineNeighbors);
            }
        }
        return mineNeighbors;
    }


    public void setUpFullBoard() {
        setupBoardWithMines();
        setUpMinesAroundCount();
    }

    public void setGameOver() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; i++) {
                model[i][j].setWasClicked(true);
            }
        }
        activatedGame = false;
    }


}