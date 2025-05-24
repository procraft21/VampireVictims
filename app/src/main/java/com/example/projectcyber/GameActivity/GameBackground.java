package com.example.projectcyber.GameActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.R;

public class GameBackground {

    Bitmap template;

    GameView gameView;
    private double playerPosX, playerPosY;

    private static final int SQUARE_SIZE = 150;

    public GameBackground(GameView gameView){
        template = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.background_grass);
        template = Bitmap.createScaledBitmap(template, SQUARE_SIZE, SQUARE_SIZE, false);
        this.gameView = gameView;
        playerPosX = gameView.getPlayer().getPositionX();
        playerPosY = gameView.getPlayer().getPositionY();
    }

    public void update(){
        playerPosX = gameView.getPlayer().getPositionX();
        playerPosY = gameView.getPlayer().getPositionY();
    }

    public void draw(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int gamePosXFirstSquare = SQUARE_SIZE * getSquareIndex(playerPosX - width);
        int gamePosYFirstSquare = SQUARE_SIZE * getSquareIndex(playerPosY - height);

        int relX = (int) (gamePosXFirstSquare - playerPosX + width/2);
        int relY = (int) (gamePosYFirstSquare - playerPosY + height/2);

        for(int x = relX; x<width; x+=SQUARE_SIZE){
            for(int y = relY; y<height; y+=SQUARE_SIZE){
                canvas.drawBitmap(template, x, y, null);
            }
        }

    }

    private int getSquareIndex(double pos){
        return (int)pos/SQUARE_SIZE;
    }
}
