package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsContainer;
import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.Weapons.MagicWand;
import com.example.projectcyber.GameActivity.Weapons.MysticOrbit;
import com.example.projectcyber.GameActivity.Weapons.Weapon;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.uiObjects.Joystick;
import com.example.projectcyber.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Mob{


    private static final int PLAYER_WIDTH = 150;
    private static final int PLAYER_HEIGHT = 150;

    private Bitmap bitmap;

    private PlayerStatsContainer stats;

    private ArrayList<Weapon> weapons;

    private long lastTimeSinceHeal = 0;

    private int level;

    private int xpRequired;
    private int xpAcquired;

    public Player(GameView gameView, HashMap<PlayerStatsType, Double> startingStats){
        super(0, 0, gameView);
        gameView.addToGrid(this);
        mass = Double.POSITIVE_INFINITY;

        stats = new PlayerStatsContainer(startingStats);
        currHP = stats.getStat(PlayerStatsType.MaxHp).getFinalValue();

        this.level = 1;
        this.xpAcquired = 0;
        this.xpRequired = 5;

        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.player_img);
            bitmap = Bitmap.createScaledBitmap(bitmap,PLAYER_WIDTH, PLAYER_HEIGHT, false);
        }

        weapons = new ArrayList<>();

    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void raiseXp(int xp){
        xpAcquired += xp;
        while(xpAcquired >= xpRequired){
            levelUp();
            xpAcquired-=xpRequired;
            xpRequired += level<=20 ? 10 : level<=40 ? 13 : 16;
        }
    }

    public void levelUp(){

    }

    public int getXpRequired(){
        return xpRequired;
    }

    public int getXpAcquired(){
        return xpAcquired;
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap, (int)(relX - bitmap.getWidth()/2),(int)(relY - bitmap.getHeight()/2), null);
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();
        Joystick joystick = gameView.getJoystick();
        velX = joystick.getDirX() * stats.getStat(PlayerStatsType.MoveSpd).getFinalValue();
        velY = joystick.getDirY() * stats.getStat(PlayerStatsType.MoveSpd).getFinalValue();

        for(Weapon weapon : weapons){
            weapon.update(deltaTime);
        }

        lastTimeSinceHeal += deltaTime;
        if(lastTimeSinceHeal > 1000){
            heal(stats.getStat(PlayerStatsType.Recovery).getFinalValue());
            lastTimeSinceHeal -= 1000;
        }

        super.update(deltaTime);

    }

    @Override
    public double getCollisionRadius() {
        return bitmap.getWidth()/2;
    }





    @Override
    protected void resolveEntityCollision(Entity entity){
        super.resolveEntityCollision(entity);
        if(entity instanceof Enemy && !immunityList.inList(entity)){
            Enemy enemy = (Enemy)entity;
            double damage = enemy.getMight() - stats.getStat(PlayerStatsType.Armor).getFinalValue();
            if(damage > 0) takeDamage(damage);
        }
    }

    public void heal(double heal){
        currHP += heal;
        if(currHP > stats.getStat(PlayerStatsType.MaxHp).getFinalValue()){
            currHP = stats.getStat(PlayerStatsType.MaxHp).getFinalValue();
        }
    }

    public double getMaxHP(){
        return stats.getStat(PlayerStatsType.MaxHp).getFinalValue();
    }

    public double getCurrentHP(){
        return currHP;
    }

    public Stat<PlayerStatsType> getStat(PlayerStatsType type){
        return stats.getStat(type);
    }

    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
}

