package com.example.projectcyber;

import android.graphics.Point;
import android.graphics.PointF;

public class Utils {
    public static double distance(double x1, double y1,  double x2, double y2){
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    public static PointF normalize(int x, int y){
        double dist = distance(x,y,0,0);
        return new PointF((float) (x/dist),(float) (y/dist));
    }
}
