package com.example.projectcyber.GameActivity;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private boolean isRunning = false;
    private final GameView game;
    private final SurfaceHolder surfaceHolder;

    private static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1000.0 / MAX_UPS;  // in milliseconds

    private double averageFPS;
    private double averageUPS;

    public GameLoop(GameView game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        int updateCount = 0;
        int frameCount = 0;

        long startTime = System.currentTimeMillis();
        long previousTime = System.nanoTime();
        long currentTime;
        long deltaTime;

        Canvas canvas = null;

        while (isRunning) {
            currentTime = System.nanoTime();
            deltaTime = (currentTime - previousTime) / 1_000_000;  // Convert to milliseconds
            previousTime = currentTime;

            // Update and draw
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update(deltaTime); // deltaTime in milliseconds as long
                    updateCount++;
                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (surfaceHolder.getSurface().isValid()) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Sleep to maintain target UPS
            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                SystemClock.sleep(sleepTime);
            }

            // Catch up on updates if behind
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                currentTime = System.nanoTime();
                deltaTime = (currentTime - previousTime) / 1_000_000;
                previousTime = currentTime;

                game.update(deltaTime);
                updateCount++;

                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            // Update UPS and FPS once per second
            if (System.currentTimeMillis() - startTime >= 1000) {
                long currentTimeMs = System.currentTimeMillis();
                double elapsed = currentTimeMs - startTime;
                averageUPS = updateCount / (elapsed / 1000.0);
                averageFPS = frameCount / (elapsed / 1000.0);
                updateCount = 0;
                frameCount = 0;
                startTime = currentTimeMs;
            }
        }
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void stopGame(){
        isRunning = false;
        try{
            join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
