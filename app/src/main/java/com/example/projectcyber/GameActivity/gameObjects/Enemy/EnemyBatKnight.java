package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.R;

public class EnemyBatKnight extends FollowerEnemy{

    private static Bitmap batImg;

    public EnemyBatKnight(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);

        if(batImg == null){
            batImg = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.enemy_bat_knight);
            batImg = Bitmap.createScaledBitmap(batImg, 75, 75, false);
        }
        imgRight = batImg;
        this.maxHp = 20;
        currHP = maxHp;
        this.speed = 100;
        this.might = 5;
        this.xpDrop = 1;
    }
}
