package com.example.projectcyber.gameObjects;

import com.example.projectcyber.GameView;
import com.example.projectcyber.Utils;

public class FollowerEnemy extends Enemy{
    Player player;

    public FollowerEnemy(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);
        player = gameView.getPlayer();
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        double distance = Utils.distance(posX, posY, player.getPositionX(), player.getPositionY());
        if(distance > 0){
            velX = speed * (player.getPositionX() -posX)/distance;
            velY = speed * (player.getPositionY() -posY)/distance;
        }


        posX += velX*deltaTime/1000;
        posY += velY*deltaTime/1000;

    }
}
