package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;

import java.util.HashMap;

public abstract class Weapon {
    WeaponStatsContainer stats;
    GameView gameView;

    public Weapon(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        stats = new WeaponStatsContainer(startingStats, gameView.getPlayer());
        this.gameView = gameView;
    }

    public Weapon(){

    }

    public abstract Projectile createProjectile();

}
