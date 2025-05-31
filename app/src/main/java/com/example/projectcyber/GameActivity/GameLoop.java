package com.example.projectcyber.GameActivity;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * The GameLoop manages the main game thread. It updates the game logic and draws frames
 * at a target update rate (UPS) and frame rate (FPS).
 */
public class GameLoop extends Thread {

    private boolean isRunning = false;
    private final GameView game;
    private final SurfaceHolder surfaceHolder;

    private static final double MAX_UPS = 60.0; // Target Updates Per Second
    private static final double MAX_FPS = 60.0; // Target Frames Per Second

    private static final long UPDATE_PERIOD_NS = (long) (1_000_000_000 / MAX_UPS);
    private static final long DRAW_PERIOD_NS = (long) (1_000_000_000 / MAX_FPS);

    private double averageUPS;
    private double averageFPS;

    /**
     * Constructs the game loop with the GameView and its SurfaceHolder.
     *
     * @param game The GameView instance containing game logic
     * @param surfaceHolder The SurfaceHolder used to draw on the canvas
     */
    public GameLoop(GameView game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    /**
     * Starts the game loop.
     */
    public void startLoop() {
        isRunning = true;
        start();
    }

    /**
     * The main loop of the game. Handles updating and rendering.
     */
    @Override
    public void run() {
        long previousUpdateTime = System.nanoTime();
        long previousDrawTime = System.nanoTime();

        int updateCount = 0;
        int frameCount = 0;

        long startTimeMs = System.currentTimeMillis();

        while (isRunning) {

            // Game update: logic is updated at a fixed rate
            if (System.nanoTime() - previousUpdateTime >= UPDATE_PERIOD_NS) {
                long deltaTimeMs = (System.nanoTime() - previousUpdateTime) / 1_000_000;
                previousUpdateTime = System.nanoTime();

                game.update(deltaTimeMs);
                updateCount++;
            }

            // Drawing happens only after update to prevent drawing outdated frames
            if (previousDrawTime <= previousUpdateTime) {
                Canvas canvas;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        synchronized (surfaceHolder) {
                            game.draw(canvas);
                            previousDrawTime = System.nanoTime();
                        }
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Slight sleep to yield CPU, prevents tight loop from hogging resources
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Recalculate UPS and FPS every second
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

    /**
     * @return The average updates per second since the last measurement.
     */
    public double getAverageUPS() {
        return averageUPS;
    }

    /**
     * @return The average frames per second since the last measurement.
     */
    public double getAverageFPS() {
        return averageFPS;
    }

    /**
     * Gracefully stops the game loop and waits for the thread to finish.
     */
    public void stopGame() {
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
