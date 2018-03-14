package ait.hu.minesweeper.Model;


public class Field {

    private int minesAround;
    private boolean isMine;
    private boolean isFlagged;
    private boolean wasClicked;

    public Field() {
        this.isMine = false;
        this.minesAround = 0;
        this.isFlagged = false;
        this.wasClicked = false;
    }


    public int getMinesAround() {
        return minesAround;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean wasClicked() {
        return wasClicked;
    }


    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public void setMine() {
        this.isMine = true;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void setWasClicked(boolean wasClicked) {
        this.wasClicked = wasClicked;
    }

    public boolean isBlank() {
        return (getMinesAround()==0);
    }
}

