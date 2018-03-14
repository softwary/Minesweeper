package ait.hu.minesweeper.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ait.hu.minesweeper.Model.MinesweeperModel;

import static ait.hu.minesweeper.Model.MinesweeperModel.*;


public class MinesweeperView extends View {
    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintFont;

    public MinesweeperView(Context context,
                           @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);


        paintFont = new Paint();
        paintFont.setColor(Color.BLUE);
        paintFont.setTextSize(60);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,
                0, getWidth(), getHeight(),
                paintBackground);

        drawGameArea(canvas);

        for (short i = 0; i < 6; i++) {
            for (short j = 0; j < 6; j++) {
                if (!getInstance().isFlagMode()) {
                    if (getInstance().getFieldContent(i, j).wasClicked() &&
                            getInstance().getFieldContent(i, j).isMine()) {
                        drawMine(canvas);
                    } else if (getInstance().getFieldContent(i, j).wasClicked() &&
                            !getInstance().getFieldContent(i, j).isMine() &&
                            !getInstance().getFieldContent(i, j).isBlank()) {
                        drawNumbersOfNeighboringMines(canvas);
                    } else if (getInstance().getFieldContent(i, j).wasClicked() &&
                            getInstance().getFieldContent(i, j).isBlank()) {
                        drawBlankField(canvas);
                    }
                } else if (getInstance().isFlagMode()) {
                    if (getInstance().getFieldContent(i, j).isFlagged()) {
                        drawFlag(canvas);
                    }
                }
            }
        }
    }

    private void drawGameArea(Canvas canvas) {

        // Draws rectangle around whole View
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // four horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(),
                getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);

        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);

        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // four vertical lines
        canvas.drawLine(getWidth() / 5, 0,
                getWidth() / 5,
                getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0,
                2 * getWidth() / 5,
                getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0,
                3 * getWidth() / 5,
                getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0,
                4 * getWidth() / 5,
                getHeight(),
                paintLine);
    }


    private void drawNumbersOfNeighboringMines(Canvas canvas) {
        for (short i = 0; i < 6; i++) {
            for (short j = 0; j < 6; j++) {
                // if it'S NOT blank and it was clicked, then draw the int of number of mines
                if (!getInstance().getFieldContent(i, j).isBlank() &&
                        getInstance().getFieldContent(i, j).wasClicked()) {
                    int minesAround = getInstance().getFieldContent(i, j).getMinesAround();
                    canvas.drawText(Integer.toString(minesAround), 100, getHeight() / 5, paintFont);
                }
            }
        }
    }


    private void drawBlankField(Canvas canvas) {
        for (short i = 0; i < 6; i++) {
            for (short j = 0; j < 6; j++) {
                // if it'S NOT blank and it was clicked, then draw the int of number of mines
                if (!getInstance().getFieldContent(i, j).isBlank() &&
                        getInstance().getFieldContent(i, j).wasClicked()) {
                    int minesAround = getInstance().getFieldContent(i, j).getMinesAround();
                    canvas.drawText(Integer.toString(minesAround), 100, getHeight() / 5, paintFont);
                }
            }
        }
    }

    private void drawFlag(Canvas canvas) {
        canvas.drawText("🚩", 100, getHeight() / 5, paintFont);
    }

    private void drawMine(Canvas canvas) {
        for (short i = 0; i < 6; i++) {
            for (short j = 0; j < 6; j++) {
                if (getInstance().getFieldContent(i, j).isMine() &&
                        getInstance().getFieldContent(i, j).wasClicked()) {
                    canvas.drawText("💣", getWidth() / 5, getHeight() / 5, paintFont);
                    // else if it is a numberOfMines field (& was clicked):
                }
            }
        }
    }

    // basically, get the location of where they clicked, and set it equal to tmpPlayer, invalidate
    // wherever they clicked, set those equal to 2 coordinates: (tX, tY)
    // check the Model.getInstance().getFieldContent of that place where they clicked in the model
    // if it is empty, and they are able to go, then set that place in the model to a new piece of information

    // else check if the game just ended because there was a winner/loser

    // invalidate at the end


    //set up just model here


//    The on touch event is just gonna take the coordinates the user touches and say is there a mine
//    at these coordinates? If so, mark as clicked and then game over etc

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // make things get revealed on touch is all this one does.
        // if not clicked yet, set to clicked
        // if it is a mine, deactivate game and reveal all fields
        if (event.getAction() == MotionEvent.ACTION_UP) {

            int tX = ((int) event.getX() / (getWidth() / 5));
            int tY = ((int) event.getY() / (getHeight() / 5));


            if (getInstance().isActivatedGame()) {
                if (!getInstance().isFlagMode()) {
                    if (!getInstance().getFieldContent((short) tX, (short) tY).wasClicked()) {
                        if (getInstance().getFieldContent((short) tX, (short) tY).isMine()) {
                            getInstance().getFieldContent((short) tX, (short) tY).setWasClicked(true);
                            getInstance().setGameOver();
                        } else if (!getInstance().getFieldContent((short) tX, (short) tY).isMine() &&
                                !getInstance().getFieldContent((short) tX, (short) tY).isBlank()) {
                            getInstance().getFieldContent((short) tX, (short) tY).setWasClicked(true);
                        } else {
                            getInstance().getFieldContent((short) tX, (short) tY).setWasClicked(true);
                        }
                    } else {
                        return true;
                    }
                } else if (getInstance().isFlagMode()) {
                    if (!getInstance().getFieldContent((short) tX, (short) tY).isFlagged()) {
                        getInstance().getFieldContent((short) tX, (short) tY).setFlagged(true);
                    } else {
                        return true;
                    }
                }
            } else if (!getInstance().isActivatedGame()) {
                return true;
            }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }


    public void clearBoard() {
        getInstance().setUpFullBoard();
        invalidate();
    }
}

