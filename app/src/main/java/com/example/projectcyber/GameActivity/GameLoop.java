package com.example.projectcyber.GameActivity;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private boolean isRunning = false;
    private boolean isPaused = false;
    private final Object pauseLock = new Object();

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

            // --- Pause Handling ---
            synchronized (pauseLock) {
                if (isPaused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    previousTime = System.nanoTime(); // reset time after pause
                    startTime = System.currentTimeMillis();
                    updateCount = 0;
                    frameCount = 0;
                    continue;
                }
            }

            // --- Timing ---
            currentTime = System.nanoTime();
            deltaTime = (currentTime - previousTime) / 1_000_000; // ms
            previousTime = currentTime;

            // --- Update and Draw ---
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update(deltaTime);
                    updateCount++;
                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null){

                    try {
                        if (surfaceHolder.getSurface().isValid()) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            frameCount++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // --- UPS Throttling ---
            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                SystemClock.sleep(sleepTime);
            }

            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                currentTime = System.nanoTime();
                deltaTime = (currentTime - previousTime) / 1_000_000;
                previousTime = currentTime;

                game.update(deltaTime);
                updateCount++;

                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            if (System.currentTimeMillis() - startTime >= 1000) {
                long now = System.currentTimeMillis();
                double secondsElapsed = now - startTime;
                averageUPS = updateCount / (secondsElapsed / 1000.0);
                averageFPS = frameCount / (secondsElapsed / 1000.0);
                updateCount = 0;
                frameCount = 0;
                startTime = now;
            }
        }
    }

    // --- Public API ---

    public void pauseGame() {
        synchronized (pauseLock) {
            isPaused = true;
        }
    }

    public void resumeGame() {
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notify();
        }
    }

    public void stopGame() {
        isRunning = false;
        resumeGame(); // ensure thread can exit from wait()
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }
}
