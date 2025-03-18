package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameView;
import com.example.projectcyber.R;

public class Enemy extends Entity implements Cloneable{
    private static Bitmap bitmap;
    protected int speed = 100;

    public Enemy(GameView gameView, double posX, double posY){
        super(posX,posY, gameView);
        if(bitmap == null){
            Bitmap enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.enemy_img);
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,75,75, false);
            this.bitmap = enemyBitmap;

        }
    }

    public Enemy(Enemy enemy, double posX, double posY){
        super(posX, posY, enemy.gameView);
        this.speed = enemy.speed;
        if(bitmap == null){
            Bitmap enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.enemy_img);
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,150,150, false);
            this.bitmap = enemyBitmap;

        }
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

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
