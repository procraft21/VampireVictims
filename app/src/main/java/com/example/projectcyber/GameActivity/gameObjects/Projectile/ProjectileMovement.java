package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import com.example.projectcyber.GameActivity.GameView;

public abstract class ProjectileMovement {

    protected int amountShot = 0;

    public ProjectileMovement(){}

    public ProjectileMovement(int amountShot) {
        this.amountShot = amountShot;
    }

    public abstract void update(long deltaTime, GameView gameView, Projectile projectile);
}
