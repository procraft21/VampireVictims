package com.example.projectcyber.GameActivity.uiObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.R;

public class CoinCounter{
    Bitmap coinBitmap;
    int amount;
    GameView gameView;

    public CoinCounter(GameView gameView){
        this.gameView = gameView;
        if(coinBitmap == null){
            Bitmap xpgemBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.coin_img);
            xpgemBitmap = Bitmap.createScaledBitmap(xpgemBitmap,75,75, false);
            this.coinBitmap = xpgemBitmap;
        }
    }

    public void update(){
        amount = gameView.getCoins();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(coinBitmap, 0, 35, null);

        Paint paint = new Paint();
        paint.setTextSize(75);
        canvas.drawText(amount + "", 90, 100, paint);
    }
}
