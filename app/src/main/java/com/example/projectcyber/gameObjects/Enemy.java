package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.projectcyber.GameView;

public class Enemy extends Entity{
    private Bitmap bitmap;
    protected int speed = 100;

    public Enemy(GameView gameView, double posX, double posY){
        super(posX,posY, gameView);
    }

    public Enemy(Enemy enemy, double posX, double posY){
        super(posX, posY, enemy.gameView);
        this.speed = enemy.speed;
        this.bitmap = enemy.bitmap;
    }
    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap,(int)(relX - bitmap.getWidth()/2), (int)(relY - bitmap.getHeight()/2), null );
    }

    @Override
    public void update(long deltaTime) {

    }
}
