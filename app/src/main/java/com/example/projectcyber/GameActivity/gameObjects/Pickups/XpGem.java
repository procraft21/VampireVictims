package com.example.projectcyber.GameActivity.gameObjects.Pickups;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.uiObjects.XpProgressBar;
import com.example.projectcyber.R;

public class XpGem extends Pickup {

    private static Bitmap bitmap;
    private int amount;

    public XpGem(double posX, double posY, GameView gameView){
        super(posX, posY, gameView);
        if(bitmap == null){
            Bitmap xpgemBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.xpgem_img);
            xpgemBitmap = Bitmap.createScaledBitmap(xpgemBitmap,45,45, false);
            this.bitmap = xpgemBitmap;

        }
        amount = 1;
    }



    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap, (int)(relX - bitmap.getWidth()/2),(int)(relY - bitmap.getHeight()/2), null);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void resolveEntityCollision(Entity other) {
        if (other instanceof Player) {
            destroy();
            ((Player) other).raiseXp(amount);
        }
    }

}
