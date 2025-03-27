package com.example.projectcyber.gameObjects;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameView;
import com.example.projectcyber.Utils;

public class FollowerEnemy extends Enemy{
    Player player;

    public FollowerEnemy(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);
        player = gameView.getPlayer();
        double length = distance(player);
        if(length > 0){
            Log.d("startingVel", "startingVel");
            velX = (posX - player.posX)/length;
            velY = (posY - player.posY)/length;
        }


    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();
        lerpWithIdealVel();
        super.update(deltaTime);

    }

    /**lerp with ideal velocity(towards the player)*/
    private void lerpWithIdealVel(){
        double length = distance(player);
        double idealVelX = speed*(player.posX-posX)/length;
        double idealVelY = speed*(player.posY-posY)/length;

        double idealWeight = 0.5;
        velX = Utils.lerp(idealVelX, velX, idealWeight);
        velY = Utils.lerp(idealVelY, velY, idealWeight);
    }
    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
