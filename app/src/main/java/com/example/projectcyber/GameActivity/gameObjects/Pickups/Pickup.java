package com.example.projectcyber.GameActivity.gameObjects.Pickups;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Utils;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Player;

public abstract class Pickup extends Entity implements Cloneable{

    private boolean closeToPlayer;

    private final double SPEED = 300;

    public Pickup(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        closeToPlayer = false;
    }

    public void update(long deltaTime){
        savePrevPos();
        Player player = gameView.getPlayer();
        if(distance(player) < player.getStat(PlayerStatsType.Magnet).getFinalValue()){
            closeToPlayer = true;
        }

        if(closeToPlayer){
            lerpWithIdealVel();
        }
        super.update(deltaTime);
    }

    /**lerp with ideal velocity(towards the player)*/
    private void lerpWithIdealVel(){
        Player player = gameView.getPlayer();

        double length = distance(player);
        double idealVelX = SPEED *(player.getPositionX()-posX)/length;
        double idealVelY = SPEED *(player.getPositionY()-posY)/length;

        double idealWeight = 0.5;
        velX = Utils.lerp(idealVelX, velX, idealWeight);
        velY = Utils.lerp(idealVelY, velY, idealWeight);
    }

    @Override
    public Pickup clone() {
        try {
            Pickup clone = (Pickup) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
