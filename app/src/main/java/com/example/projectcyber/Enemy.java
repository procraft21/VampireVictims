package com.example.projectcyber;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Enemy extends Entity{
    private Bitmap bitmap;
    @Override
    protected void drawRelative(Canvas canvas, Point center) {
        canvas.drawBitmap(bitmap, canvas.getWidth()/2 + (pos.x-center.x) - bitmap.getWidth()/2f, canvas.getHeight()/2 + (pos.y-center.y) - bitmap.getHeight()/2f, new Paint());
    }

    public Enemy(Bitmap bitmap, Point pos){
       this.pos = new Point(pos);
       this.bitmap = bitmap;
    }
}
