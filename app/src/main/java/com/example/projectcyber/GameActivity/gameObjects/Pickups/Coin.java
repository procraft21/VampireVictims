package com.example.projectcyber.GameActivity.gameObjects.Pickups;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.R;

public class Coin extends MoneyDrop{

    private static Bitmap baseImg;

    public Coin(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        if(bitmap == null){
            Bitmap xpgemBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pickup_coin);
            xpgemBitmap = Bitmap.createScaledBitmap(xpgemBitmap,50,50, false);
            baseImg = xpgemBitmap;
        }
        this.bitmap = baseImg;
        amount = 1;

    }





}
