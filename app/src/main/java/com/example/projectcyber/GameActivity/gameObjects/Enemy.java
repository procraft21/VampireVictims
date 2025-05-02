package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.R;

public class Enemy extends Mob implements Cloneable{
    private static Bitmap bitmap;

    //movement speed
    protected int speed = 100;

    //raw damage to player without armor.
    protected int might = 5;

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
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,75,75, false);
            this.bitmap = enemyBitmap;


        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }



    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap,(int)(relX - bitmap.getWidth()/2), (int)(relY - bitmap.getHeight()/2), null );

        /*int color = ContextCompat.getColor(gameView.getContext(), R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("" + this.tag, (int)(relX - bitmap.getWidth()/2), (int)(relY +50), paint);*/
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        Enemy clone = (Enemy) super.clone();
        clone.tag = counter;
        counter++;
        return clone;
    }

    @Override
    public double getCollisionRadius() {
        return super.getCollisionRadius();
    }

    public int getMight(){
        return might;
    }
}
