package com.example.projectcyber.GameActivity.uiObjects;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.R;
import com.example.projectcyber.GameActivity.gameObjects.Player;

public class HealthBar {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 35;
    private static final int DISTANCE_FROM_PLAYER = 50;

    private Player player;

    private GameView gameView;

    double healthRatio;

    public HealthBar(GameView gameView){
       this.player = gameView.getPlayer();
       this.gameView = gameView;
    }

    public void update(){
        //updates the current health to max health ratio.
        healthRatio = player.getCurrentHP() / player.getStatValue(PlayerStatsType.MaxHp);
    }

    public void draw(Canvas canvas){
        int left = canvas.getWidth()/2 - WIDTH/2;
        int top = canvas.getHeight()/2 + player.getHeight()/2 + DISTANCE_FROM_PLAYER;
        int bottom = top + HEIGHT;
        int fullRight = left + WIDTH;

        int currentRight = left + (int)(WIDTH * healthRatio);

        Rect full = new Rect(left, top, fullRight, bottom);
        Rect current = new Rect(left, top, currentRight, bottom);

        Paint fullPaint = new Paint();
        fullPaint.setColor(gameView.getContext().getResources().getColor(R. color. neonRed));

        Paint currPaint = new Paint();
        currPaint.setColor(gameView.getContext().getResources().getColor(R.color.neonGreen));

        canvas.drawRect(full, fullPaint);
        canvas.drawRect(current, currPaint);
    }




}
