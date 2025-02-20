package com.example.projectcyber;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class StationaryEntity extends Entity{

    public StationaryEntity(Point pos){
        super(pos);
    }
    @Override
    protected void drawRelative(Canvas canvas, Point center) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(canvas.getWidth()/2 + pos.x - center.x,canvas.getHeight()/2 +  pos.y - center.y, 50, paint);
    }

    @Override
    public void move() {

    }
}
