package com.example.projectcyber.gameObjects;

import com.example.projectcyber.GameView;

public class StationaryEnemy extends Enemy{

    public StationaryEnemy(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);
    }

    public StationaryEnemy(Enemy enemy, double posX, double posY) {
        super(enemy, posX, posY);
    }
}
