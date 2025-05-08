package com.example.projectcyber.GameActivity.uiObjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.projectcyber.GameActivity.GameView;

public class XpProgressBar {

    private final int HEIGHT = 35;

    private GameView gameView;
    private double xpRatio;

    public XpProgressBar(GameView gameView){
        this.gameView = gameView;
    }

    public void update(long deltaTime){
        xpRatio = (double)gameView.getPlayer().getXpAcquired()/gameView.getPlayer().getXpRequired();
    }

    public void draw(Canvas canvas){
        int left = 0;
        int rightFull = canvas.getWidth();
        int right = (int) (left + xpRatio*rightFull);
        int up = 0;
        int down = HEIGHT;

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(0);

        Paint barPaint = new Paint();
        barPaint.setColor(0xFF2568d6);

        canvas.drawRect(left, up, rightFull, down, backgroundPaint);
        canvas.drawRect(left, up, right, down, barPaint);
    }
}
