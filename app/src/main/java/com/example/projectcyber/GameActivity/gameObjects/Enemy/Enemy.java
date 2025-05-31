package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Mob;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.XpGem;
import com.example.projectcyber.R;

public abstract class  Enemy extends Mob implements Cloneable{

    //movement speed
    protected int speed = 100;

    //raw damage to player without armor.
    protected int might = 5;

    //max hp
    protected int maxHp = 1;

    //xpDrop when dead
    protected int xpDrop = 10;


    public Enemy(GameView gameView, double posX, double posY){
        super(posX,posY, gameView);

        this.currHP = maxHp;
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        Enemy clone = (Enemy) super.clone();
        return clone;
    }

    @Override
    public double getCollisionRadius() {
        return super.getCollisionRadius();
    }

    public int getMight(){
        return might;
    }

    @Override
    public void destroy() {
        super.destroy();

        Pickup pickup = gameView.getDropTable().getDrop();
        if(pickup != null){
            pickup.setPosX(posX);
            pickup.setPosY(posY);
            if(pickup instanceof XpGem)
                ((XpGem) pickup).setAmount(xpDrop);
            gameView.addPickup(pickup);
        }
    }
}
