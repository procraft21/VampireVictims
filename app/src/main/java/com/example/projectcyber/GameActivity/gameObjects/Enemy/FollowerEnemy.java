package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;
import com.example.projectcyber.GameActivity.gameObjects.Player;

public class FollowerEnemy extends Enemy {

    private final Player player;

    public FollowerEnemy(GameView gameView, double posX, double posY) {
        super(gameView, posX, posY);
        this.player = gameView.getPlayer();

        // Initial velocity pointing toward the player
        double length = distance(player);
        if (length > 0) {
            velX = (player.getPositionX() - posX) / length;
            velY = (player.getPositionY() - posY) / length;
        }
    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();

        lerpTowardPlayer();

        super.update(deltaTime);
    }

    /** Lerp current velocity toward the ideal velocity pointing at the player */
    private void lerpTowardPlayer() {
        double length = distance(player);
        if (length == 0) return;

        double idealVelX = speed * (player.getPositionX() - posX) / length;
        double idealVelY = speed * (player.getPositionY() - posY) / length;

        final double idealWeight = 0.5;
        velX = Utils.lerp(idealVelX, velX, idealWeight);
        velY = Utils.lerp(idealVelY, velY, idealWeight);
    }

    @NonNull
    @Override
    public FollowerEnemy clone() {
        try {
            return (FollowerEnemy) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("FollowerEnemy clone failed", e);
        }
    }
}
