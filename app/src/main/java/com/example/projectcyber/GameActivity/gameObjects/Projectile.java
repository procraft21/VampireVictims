package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.R;

public class Projectile extends Entity{

    private static Bitmap bitmap;


    public Projectile(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        if(bitmap == null){
            Bitmap enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_img);
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,50,50, false);
            this.bitmap = enemyBitmap;
        }
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap,(int)(relX - bitmap.getWidth()/2), (int)(relY - bitmap.getHeight()/2), null );
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void resolveEntityCollision(Entity other) {

    }



}
