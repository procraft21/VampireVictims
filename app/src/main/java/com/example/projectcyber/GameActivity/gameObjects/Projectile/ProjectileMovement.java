package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import com.example.projectcyber.GameActivity.GameView;

public abstract class ProjectileMovement {
    public abstract void update(long deltaTime, GameView gameView, Projectile projectile);
}
