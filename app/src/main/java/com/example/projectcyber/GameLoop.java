package com.example.projectcyber;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

// Game loop is the clock of the game. It tells the game object when to update and draw
// Runs on a different thread to improve accuracy and efficiency
public class GameLoop extends Thread {

    // in order to stop the game loop
    public boolean isRunning = false;
    // the game object
    private final GameView game;
    // game's surfaceHolder object
    private final SurfaceHolder surfaceHolder;
    // Desired UPS and FPS
    private static final double MAX_UPS = 60.0;
    // time between updates in ms
    private static final double UPS_PERIOD = 1E+3 / MAX_UPS;  // 1E+3 = 1000.0
    // average UPS and FPS
    private double averageFPS;
    private double averageUPS;

    //constructor
    public GameLoop(GameView game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public void startLoop() {
        isRunning = true;
        start(); // start the thread
    }

    //the game loop function
    //tells game object when to update and draw with two objectives
    //Firstly,strives to match the UPS to MAX_UPS
    //Secondly strives to match the FPS to MAX_UPS unless it interferes with first objective
    @Override
    public void run()
    {
        //initializing variables
        int updateCount = 0;
        int frameCount = 0;
        long startTime;
        long elapsedTime;
        long sleepTime;

        long lastUpdate = 0;
        long deltaTime=0;
        //initializing game object's canvas
        Canvas canvas = null;
        //gets initial time
        startTime = System.currentTimeMillis();
        //start of loop
        while(isRunning) {
            //updates and renders the game
            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    if (lastUpdate == 0){
                        deltaTime = System.currentTimeMillis() - startTime;
                    }else{
                        deltaTime = System.currentTimeMillis() - lastUpdate;
                    }
                    game.update(deltaTime);
                    lastUpdate = System.currentTimeMillis();
                    updateCount++;
                    game.draw(canvas);
                }
            } catch(IllegalArgumentException e) {
                e.printStackTrace();
            } finally{
                if(canvas != null) {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //pauses loop to not exceed desired UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            if(sleepTime > 0)
                    SystemClock.sleep(sleepTime);

            //in case the UPS is too low: lowers FPS to reach desired UPS
            while(sleepTime < 0 && updateCount < MAX_UPS - 1)
            {
                deltaTime = System.currentTimeMillis() - lastUpdate;
                game.update(deltaTime);
                lastUpdate = System.currentTimeMillis();
                updateCount++;
                sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            }

            //calculates the averages during the past second
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000)
            {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }


    }

    //getter
    public double getAverageUPS()
    {
        return averageUPS;
    }

    //getter
    public double getAverageFPS()
    {
        return averageFPS;
    }
}
