package com.example.projectcyber.GameActivity.gameObjects.Pickups;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.R;

public class XpGem extends Pickup {

    private static Bitmap baseImg;
    private int amount;

    public XpGem(double posX, double posY, GameView gameView){
        super(posX, posY, gameView);
        if(baseImg == null){
            Bitmap xpgemBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pickup_xpgem);
            xpgemBitmap = Bitmap.createScaledBitmap(xpgemBitmap,45,45, false);
            baseImg = xpgemBitmap;
        }

        bitmap = baseImg;


        amount = 1;
    }





    @Override
    protected void resolveEntityCollision(Entity other) {
        if (other instanceof Player) {
            destroy();
            ((Player) other).raiseXp(amount);
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
