package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsContainer;
import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.uiObjects.Joystick;

import java.util.HashMap;

public class Player extends Mob{

    private double currHP;
    private Bitmap bitmap;

    private PlayerStatsContainer stats;

    public Player(GameView gameView, HashMap<PlayerStatsType, Double> startingStats){
        super(0, 0, gameView);
        gameView.addToGrid(this);
        mass = Double.POSITIVE_INFINITY;

        stats = new PlayerStatsContainer(startingStats);
        currHP = stats.getStat(PlayerStatsType.MaxHp).getFinalValue();


    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }


    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        Paint playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, (int)(relX - bitmap.getWidth()/2),(int)(relY - bitmap.getHeight()/2), playerPaint);
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
        super.update(deltaTime);

    }

    @Override
    public double getCollisionRadius() {
        return bitmap.getWidth()/2;
    }

    public void takeDamage(int damage){
        if(damage < 0)
            return;
        currHP -= damage;
        if(currHP < 0)
            currHP = 0;
    }



    @Override
    protected void resolveEntityCollision(Entity entity){
        super.resolveEntityCollision(entity);
        if(entity instanceof Enemy && !immunityList.inList(entity)){
            Enemy enemy = (Enemy)entity;
            takeDamage(enemy.getMight());
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
}

