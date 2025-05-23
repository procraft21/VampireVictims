package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;
import com.example.projectcyber.GameActivity.gameObjects.Player;

public class FollowerEnemy extends Enemy{
    Player player;

    public FollowerEnemy(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);
        player = gameView.getPlayer();
        double length = distance(player);
        if(length > 0){
            Log.d("startingVel", "startingVel");
            velX = (posX - player.getPositionX())/length;
            velY = (posY - player.getPositionY())/length;
        }


    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();
        lerpWithIdealVel();
        super.update(deltaTime);
        if(player.getPositionX() > posX) direction = Direction.Right;
        if(player.getPositionX() < posX) direction = Direction.Left;
    }

    /**lerp with ideal velocity(towards the player)*/
    private void lerpWithIdealVel(){
        double length = distance(player);
        double idealVelX = speed*(player.getPositionX()-posX)/length;
        double idealVelY = speed*(player.getPositionY()-posY)/length;

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
