package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import com.example.projectcyber.GameActivity.GameView;

public abstract class ProjectileMovement {
    public abstract void update(double deltaTime, GameView gameView, Projectile projectile);
}
