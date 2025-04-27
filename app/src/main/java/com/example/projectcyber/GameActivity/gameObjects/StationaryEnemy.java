package com.example.projectcyber.GameActivity.gameObjects;

import com.example.projectcyber.GameActivity.GameView;

public class StationaryEnemy extends Enemy {

    public StationaryEnemy(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);
    }

    public StationaryEnemy(Enemy enemy, double posX, double posY) {
        super(enemy, posX, posY);
    }
}
