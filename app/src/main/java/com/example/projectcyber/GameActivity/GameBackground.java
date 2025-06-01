package com.example.projectcyber.GameActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.R;

/**
 * GameBackground handles the rendering of a tiled background that follows the player's movement.
 */
public class GameBackground {

    private static final int SQUARE_SIZE = 150;

    private Bitmap template;
    private final GameView gameView;

    private double playerPosX, playerPosY;

    /**
     * Constructs the GameBackground by initializing the tiled background and linking to the player.
     *
     * @param gameView The main game view providing context and access to the player
     */
    public GameBackground(GameView gameView) {
        // Load and scale the grass background tile
        template = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.background_grass);
        this.template = Bitmap.createScaledBitmap(template, SQUARE_SIZE, SQUARE_SIZE, false);

        this.gameView = gameView;

        // Initialize player position
        this.playerPosX = gameView.getPlayer().getPositionX();
        this.playerPosY = gameView.getPlayer().getPositionY();
    }

    /**
     * Updates the background's reference to the player's current position.
     */
    public void update() {
        playerPosX = gameView.getPlayer().getPositionX();
        playerPosY = gameView.getPlayer().getPositionY();
    }

    /**
     * Draws a tiled grass background that follows the player's position.
     *
     * @param canvas The canvas to draw the background on
     */
    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Calculate the top-left square index in game coordinates
        int gamePosXFirstSquare = SQUARE_SIZE * getSquareIndex(playerPosX - width);
        int gamePosYFirstSquare = SQUARE_SIZE * getSquareIndex(playerPosY - height);

        // Calculate the screen-relative position of that top-left square
        int relX = (int) (gamePosXFirstSquare - playerPosX + width / 2);
        int relY = (int) (gamePosYFirstSquare - playerPosY + height / 2);

        // Tile the background image across the canvas
        for (int x = relX; x < width; x += SQUARE_SIZE) {
            for (int y = relY; y < height; y += SQUARE_SIZE) {
                canvas.drawBitmap(template, x, y, null);
            }
        }
    }

    /**
     * Converts a position into a tile index.
     *
     * @param pos The position in game world coordinates
     * @return The corresponding index of the tile gridכ
     */
    private int getSquareIndex(double pos) {
        return (int) pos / SQUARE_SIZE;
    }
}
