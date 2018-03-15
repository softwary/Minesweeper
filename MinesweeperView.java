package ait.hu.minesweeper.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import ait.hu.minesweeper.Model.MinesweeperModel;

import static ait.hu.minesweeper.Model.MinesweeperModel.*;


public class MinesweeperView extends View {
    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintFont;
    private int BOARD_SIZE = 5;

    public MinesweeperView(Context context,
                           @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);


        paintFont = new Paint();
        paintFont.setColor(Color.RED);
        paintFont.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,
                0, getWidth(), getHeight(),
                paintBackground);

        drawGameArea(canvas);

        for (short i = 0; i < BOARD_SIZE; i++) {
            for (short j = 0; j < BOARD_SIZE; j++) {
                // not in flag mode && the game is activated
                if (!getInstance().isFlagMode() && getInstance().isActivatedGame()) {

                    // wasClicked & is a Mine
                    if (getInstance().getFieldContent(i, j).wasClicked() &&
                            getInstance().getFieldContent(i, j).isMine()) {
                        drawMine(canvas, i, j);

                        // wasClicked & is NOT a mine, draw INT
                    } else if (getInstance().getFieldContent(i, j).wasClicked() &&
                            !getInstance().getFieldContent(i, j).isMine()) {
                        drawMinesAroundCount(canvas, i, j);
                    }

                    // In flag mode, doesn't matter if game is activated or not
                } else if (getInstance().isFlagMode()) {

                    // if it is flagged
                    if (getInstance().getFieldContent(i, j).isFlagged()) {
                        drawFlag(canvas, i, j);
                    }
                }
            }
        }
    }

    private void drawMinesAroundCount(Canvas canvas, short i, short j) {
        int x = (j) * (getWidth() / BOARD_SIZE * 2);
        int y = (i) * (getHeight() / BOARD_SIZE * 2);
        String minesAround = Integer.toString(getInstance().getFieldContent(i, j).getMinesAround());

        if (i == 0 && j == 0) {
            x = (getWidth() / BOARD_SIZE) - (getWidth() / BOARD_SIZE * 2);
            y = (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 2);
            canvas.drawText(minesAround, x, y, paintFont);
//            String xY = "Draw1~~~~MINES";
//            Log.i(xY, "This mine should be at i,j (0,0)...it really is at " + i + ", " + j +
//                    " is being put in x,y : (" + x + ", " + y + ")");
        } else if (i == 0 && j > 0) {
            x = (getWidth() / BOARD_SIZE) - (getWidth() / BOARD_SIZE * 2);
//            String xY = "DRAW5000~~~~INTS";
//            Log.i(xY, i + ", " + j + " is being put in: (" + x2 + ", " + y2 + ") and is surrounded by this many mines: " + minesAround);

            canvas.drawText(minesAround, x, y, paintFont);
        } else if (j == 0 && i > 0) {
            y = (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 2);
            canvas.drawText(minesAround, x, y, paintFont);
        } else {
            canvas.drawText(minesAround, x, y, paintFont);
        }
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        canvas.drawLine(0, getHeight() / 5, getWidth(),
                getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);

        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);

        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // make things get revealed on touch is all this one does.
        // if not clicked yet, set to clicked
        // if it is a mine, deactivate game and reveal all fields
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int tX = ((int) event.getX() / (getWidth() / 5));
            int tY = ((int) event.getY() / (getHeight() / 5));

            String tXtY = "TOUCH~~~Value OF tX";
            Log.i(tXtY, tX + ", tY:" + tY);


            if (getInstance().isActivatedGame()) {
                // if NOT flag mode
                if (!getInstance().isFlagMode()) {

                    // if it was NOT clicked yet:
                    if (!getInstance().getFieldContent((short) tX, (short) tY).wasClicked()) {

                        // if it IS a mine:
                        if (getInstance().getFieldContent((short) tX, (short) tY).isMine()) {

                            // set it as clicked (thereby causing it to be drawn by other methods)
                            getInstance().getFieldContent((short) tX, (short) tY).setWasClicked(true);
                            getInstance().setGameOver();
                            proclaimGameOver();

                            // if it is NOT a mine
                        } else if (!getInstance().getFieldContent((short) tX, (short) tY).isMine()) {

                            getInstance().getFieldContent((short) tX, (short) tY).setWasClicked(true);
                        }
                    } else if (getInstance().getFieldContent((short) tX, (short) tY).wasClicked()) {
                        invalidate();
                    }

                    // if flag mode
                } else if (getInstance().isFlagMode()) {

                    // if not flagged already
                    if (!getInstance().getFieldContent((short) tX, (short) tY).wasClicked()) {
                        // set as clicked & flagged
                        getInstance().getFieldContent((short) tX, (short) tY).setWasClicked(true);
                        getInstance().getFieldContent((short) tX, (short) tY).setFlagged(true);
                        String yesFlagMode = "~~~~setFlagged(true)";
                        Log.i(yesFlagMode, "tX: " + tX + ", tY:" + tY);
                    }
                }
                invalidate();
            }
        }
        return true;
    }

    private void drawMine(Canvas canvas, short i, short j) {
        int x = (j) * (getWidth() / BOARD_SIZE*4) + (getWidth() / BOARD_SIZE * 2);
        int y = (i) * (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 4);
        if (i == 0 && j == 0) {
            x = (getWidth() / BOARD_SIZE*4) + (getWidth() / BOARD_SIZE * 2);
            y = (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 4);

            String mineAtZeroZero = "~~~~DrawMine1 (0,0)";
            Log.i(mineAtZeroZero, "The mine is at i,j (" + i + ", " + j + "). The x, y is (" +
                                        x +", " + y+ ")");

            /* FOR SOME REASON, WHEN THE MINE IS AT 0,0, THE X,Y = (-84, -84))
            WHY THOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
            */
            canvas.drawText("💣", x, y, paintFont);

        } else if (j > 0 && i == 0) {
            x = (getWidth() / BOARD_SIZE*4) + (getWidth() / BOARD_SIZE * 2);

            String mineAtZeroOne = "~~~~DrawMine2 (0, >0)";
            Log.i(mineAtZeroOne, "The mine is at i,j (" + i + ", " + j + "). The x, y is (" +
                    x +", " + y+ ")");

            canvas.drawText("💣", x, y, paintFont);

            String mineAtZeroOne2 = "~~~DrwMine2.000 (0, >0)";
            Log.i(mineAtZeroOne2, "The mine is at i,j (" + i + ", " + j + "). The x, y is (" +
                    x +", " + y+ ")");

        } else if (i > 0 && j == 0) {
            y = (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 4);

            String mineAtZeroOne = "~~~~DrawMine3 (>0,0)";
            Log.i(mineAtZeroOne, "The mine is at i,j (" + i + ", " + j + "). The x, y is (" +
                    x +", " + y + ")");

            canvas.drawText("💣", x, y, paintFont);

        } else {

            String normalMine = "~~~~DrawMine4 (>0,>0)";
            Log.i(normalMine, "The mine is at i,j (" + i + ", " + j + "). The x, y is (" +
                    x +", " + y + ")");

            canvas.drawText("💣", x, y, paintFont);
        }
    }

    private void drawFlag(Canvas canvas, short i, short j) {
        int x =  j * (getWidth() / BOARD_SIZE * 2);
        int y =  i * (getHeight() / BOARD_SIZE * 2);
        if (i == 0 && j == 0) {
            x = (getWidth() / BOARD_SIZE) - (getWidth() / BOARD_SIZE * 2);
            y = (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 2);
            canvas.drawText("🚩", x, y, paintFont);
        } else if (i == 0 && j > 0) {
            x = (getWidth() / BOARD_SIZE) - (getWidth() / BOARD_SIZE * 2);
            canvas.drawText("🚩", x, y, paintFont);
        } else if (j == 0 && i > 0) {
            y = (getHeight() / BOARD_SIZE) - (getHeight() / BOARD_SIZE * 2);
            canvas.drawText("🚩", x, y, paintFont);
        } else {
            canvas.drawText("🚩", x, y, paintFont);
        }
    }

    private void proclaimGameOver() {
        String over = "Game over. Press reset game to play again!";
        Toast.makeText(getContext(), over, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }


    public void resetGame() {
        getInstance().setupFullBoard();
        invalidate();
    }
}