package com.example.projectcyber;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Timer {


    private long currTime = 0;

    public Timer(GameView gameView){

    }
    public void update(long deltaTime){
        currTime += deltaTime;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        int distanceFromTop = 100;
        canvas.drawText(formatTime(), canvas.getWidth()/2, distanceFromTop, paint);
    }

    private String formatTime(){
        long totalSeconds = currTime/1000;

        long seconds = totalSeconds%60;
        long minutes = totalSeconds/60;

        String secondsString = seconds<10 ? "0" + seconds : "" + seconds;
        String minutesString = minutes<10 ? "0" + minutes : "" + minutes;
        return minutesString + ":" + secondsString;
    }
}
