package com.example.projectcyber.GameActivity;

import android.graphics.PointF;

public class Utils {

    /**
     * Calculates the distance between the vector(x1,y1) and the vector(x2,y2).
     * @return the distance
     */
    public static double distance(double x1, double y1,  double x2, double y2){
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    /**
     * Normalizes the given vector.
     * @return the normalized vector.
     */
    public static PointF normalize(double x, double y){
        double dist = distance(x,y,0,0);
        return new PointF((float) (x/dist),(float) (y/dist));
    }

    /**linear interpolation function of a and b with a having a weight of wA.*/
    public static double lerp(double a, double b, double wA){
        return b*(1-wA) + a*wA;
    }
}
