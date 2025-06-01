package com.example.projectcyber.GameActivity.gameObjects.Pickups;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.GameActivity.Utils;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Player;

public abstract class Pickup extends Entity implements Cloneable {

    private boolean closeToPlayer;
    private static final double SPEED = 300;

    public Pickup(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        closeToPlayer = false;
    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();
        Player player = gameView.getPlayer();

        // Check if player is within magnetic range
        if (!closeToPlayer && distance(player) < player.getStatValue(PlayerStatsType.Magnet)) {
            closeToPlayer = true;
        }

        // Attract towards player
        if (closeToPlayer) {
            moveTowardPlayer(player);
        }

        super.update(deltaTime);
    }

    /**
     * Smoothly adjusts velocity to move towards the player.
     */
    private void moveTowardPlayer(Player player) {
        double dx = player.getPositionX() - posX;
        double dy = player.getPositionY() - posY;
        double distance = Math.max(Utils.distance(0, 0, dx, dy), 1e-5); // avoid div-by-zero

        double idealVelX = SPEED * dx / distance;
        double idealVelY = SPEED * dy / distance;

        double smoothingFactor = 0.5;
        velX = Utils.lerp(idealVelX, velX, smoothingFactor);
        velY = Utils.lerp(idealVelY, velY, smoothingFactor);
    }

    @Override
    public Pickup clone() {
        try {
            return (Pickup) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Pickup cloning failed", e);
        }
    }
}
