package com.example.projectcyber.GameActivity.gameObjects.Pickups;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Player;

public abstract class MoneyDrop extends Pickup {

    protected int amount;
    public MoneyDrop(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
    }
    @Override
    protected void resolveEntityCollision(Entity other) {
        if (other instanceof Player) {
            destroy();
            gameView.addCoins(amount);
        }
    }
}
