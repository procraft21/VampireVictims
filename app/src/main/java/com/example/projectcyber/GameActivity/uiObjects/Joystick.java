package com.example.projectcyber.GameActivity.uiObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.projectcyber.GameActivity.Utils;

public class Joystick {

    private static final int JOYSTICK_BASE_RADIUS = 100;
    private static final int JOYSTICK_HANDLE_RADIUS = 50;
    private static final int JOYSTICK_BASE_COLOR = 0x60FFFFFF,JOYSTICK_HANDLE_COLOR = 0xF0FFFFFF; //2 opacities of white
    private static final int JOYSTICK_DEAD_RADIUS = JOYSTICK_BASE_RADIUS/4;

    private Paint joystickBasePaint, joystickHandlePaint;


    //updated
    private boolean isJoystickDrawable = false;
    private int baseCenterX, baseCenterY;
    private int handleCenterX, handleCenterY;

    //raw from input
    private boolean isJoystickOn = false;
    private int baseX, baseY;
    private double actuatorX, actuatorY;

    public Joystick(){
        joystickBasePaint = new Paint();
        joystickBasePaint.setColor(JOYSTICK_BASE_COLOR);
        joystickHandlePaint = new Paint();
        joystickHandlePaint.setColor(JOYSTICK_HANDLE_COLOR);
    }

    public void update(){
        isJoystickDrawable = isJoystickOn;
        moveCenterTo(baseX, baseY);
        moveHandleToActuator();
    }

    private void moveHandleToActuator(){
        handleCenterX = baseCenterX + (int)(actuatorX*JOYSTICK_BASE_RADIUS);
        handleCenterY = baseCenterY + (int)(actuatorY*JOYSTICK_BASE_RADIUS);
    }

    public void draw(Canvas canvas){
        if(!isJoystickDrawable) return;
        //draw base
        canvas.drawCircle(baseCenterX,
                baseCenterY,
                JOYSTICK_BASE_RADIUS,
                joystickBasePaint);
        //draw handle
        canvas.drawCircle(handleCenterX,
                handleCenterY,
                JOYSTICK_HANDLE_RADIUS,
                joystickHandlePaint);
    }

    public void setIsJoystickOn(boolean status){
        isJoystickOn = status;
    }

    public void resetActuator(){
        setActuator(baseCenterX, baseCenterY);
    }

    private void moveCenterTo(int x, int y){
        baseCenterX = x;
        baseCenterY = y;
    }

    public void setActuator(int x, int y){
        double dist = Utils.distance(x,y, baseCenterX,baseCenterY);
        if(dist < JOYSTICK_BASE_RADIUS){
            actuatorX = ((double)(x-baseCenterX))/JOYSTICK_BASE_RADIUS;
            actuatorY = ((double)(y-baseCenterY))/JOYSTICK_BASE_RADIUS;
        }else{
            PointF normalized = Utils.normalize(x-baseCenterX,y - baseCenterY);
            actuatorX = normalized.x;
            actuatorY = normalized.y;
        }

    }

    public void setBase(int x, int y){
        baseX = x;
        baseY = y;
    }

    public double getDirX(){
        return (handleCenterX - baseCenterX)/ (double)JOYSTICK_BASE_RADIUS;
    }

    public double getDirY(){
        return (handleCenterY - baseCenterY) / (double)JOYSTICK_BASE_RADIUS;
    }
}
