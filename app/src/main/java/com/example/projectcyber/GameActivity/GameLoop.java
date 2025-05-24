package com.example.projectcyber.GameActivity;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private boolean isRunning = false;
    private final GameView game;
    private final SurfaceHolder surfaceHolder;

    private static final double MAX_UPS = 60.0;
    private static final double MAX_FPS = 60.0;

    private static final long UPDATE_PERIOD_NS = (long) (1_000_000_000 / MAX_UPS);
    private static final long DRAW_PERIOD_NS = (long) (1_000_000_000 / MAX_FPS);

    private double averageUPS;
    private double averageFPS;

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
        long previousUpdateTime = System.nanoTime();
        long previousDrawTime = System.nanoTime();

        int updateCount = 0;
        int frameCount = 0;

        long startTimeMs = System.currentTimeMillis();

        while (isRunning) {
            long currentTime = System.nanoTime();

            // Game update (fixed UPS)
            if (currentTime - previousUpdateTime >= UPDATE_PERIOD_NS) {
                long deltaTimeMs = (currentTime - previousUpdateTime) / 1_000_000;
                previousUpdateTime = currentTime;

                game.update(deltaTimeMs);
                updateCount++;
            }

            // Always try to render (uncapped FPS or optionally capped at higher rate)
            Canvas canvas;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        game.draw(canvas);
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    frameCount++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Sleep a tiny bit to yield CPU
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update stats every second
            long elapsedTimeMs = System.currentTimeMillis() - startTimeMs;
            if (elapsedTimeMs >= 1000) {
                averageUPS = updateCount / (elapsedTimeMs / 1000.0);
                averageFPS = frameCount / (elapsedTimeMs / 1000.0);
                updateCount = 0;
                frameCount = 0;
                startTimeMs = System.currentTimeMillis();
            }
        }
    }


    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void stopGame() {
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
