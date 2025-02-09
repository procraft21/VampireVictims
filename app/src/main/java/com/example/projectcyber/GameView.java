package com.example.projectcyber;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{

    /* JOYSTICK VARIABLES ---------------------------------------------------*/
    private boolean isJoyStickOn = false;
    private PointF joystickBaseCenter, joystickHandleCenter;
    private PointF joystickDirVector = new PointF(0,0);
    private static final int JOYSTICK_BASE_RADIUS = 200;
    private static final int JOYSTICK_HANDLE_RADIUS = 75;
    private static Paint JOYSTICK_BASE_PAINT, JOYSTICK_HANDLE_PAINT;
    private static final int JOYSTICK_BASE_COLOR = 0x60FFFFFF,JOYSTICK_HANDLE_COLOR = 0xF0FFFFFF;
    private static final int JOYSTICK_DEAD_RADIUS = 50;

    //SurfaceView variables ---------------------------------------------------------
    private SurfaceHolder holder = getHolder();
    private Canvas mainCanvas;
    private int INTERVAL = 17;
    //Player variables -----------------------------------------------------------------
    private static final int PLAYER_WIDTH = 150;
    private static final int PLAYER_HEIGHT = 150;
    Player player;
    ArrayList<Enemy> enemies;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){

        JOYSTICK_BASE_PAINT = new Paint();
        JOYSTICK_BASE_PAINT.setColor(JOYSTICK_BASE_COLOR);
        JOYSTICK_HANDLE_PAINT = new Paint();
        JOYSTICK_HANDLE_PAINT.setColor(JOYSTICK_HANDLE_COLOR);

        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_img);
        playerBitmap = Bitmap.createScaledBitmap(playerBitmap,PLAYER_WIDTH, PLAYER_HEIGHT, false);
        player = Player.getInstance();
        player.setBitmap(playerBitmap);
        player.setDirVector(joystickDirVector);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(playerBitmap, new Point(50, 0)));
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(true){
            player.move();
            for(Enemy enemy : enemies)
                enemy.move();
            drawSurface();
            SystemClock.sleep(INTERVAL);
        }

    }

    public void drawSurface(){
        if(holder.getSurface().isValid()){
            mainCanvas = holder.lockCanvas();

            mainCanvas.drawColor(0xFF006600);

            player.drawRelative(mainCanvas, player.pos);
            for(Enemy enemy : enemies)
                enemy.drawRelative(mainCanvas, player.pos);

            if(isJoyStickOn){
                mainCanvas.drawCircle(joystickBaseCenter.x, joystickBaseCenter.y,
                        JOYSTICK_BASE_RADIUS, JOYSTICK_BASE_PAINT);
                mainCanvas.drawCircle(joystickHandleCenter.x, joystickHandleCenter.y,
                        JOYSTICK_HANDLE_RADIUS, JOYSTICK_HANDLE_PAINT);
            }

            holder.unlockCanvasAndPost(mainCanvas);
        }
    }

//    @Override
//    protected void onDraw(@NonNull Canvas canvas) {
//        super.onDraw(canvas);
//        setBackgroundColor(0xFF006600);
//        /**/
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX(), y = (int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isJoyStickOn = true;
                joystickBaseCenter = new PointF(x,y);
                joystickHandleCenter = new PointF(x,y);
                break;
            case MotionEvent.ACTION_UP:
                joystickHandleCenter = new PointF(joystickBaseCenter);
                isJoyStickOn = false;
                joystickDirVector.x = 0;
                joystickDirVector.y = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int radius = (int)Math.sqrt((joystickBaseCenter.x-x)*(joystickBaseCenter.x-x)+
                        (joystickBaseCenter.y-y)*(joystickBaseCenter.y-y));


                if(radius > JOYSTICK_BASE_RADIUS){

                    x  = (int) ((x- joystickBaseCenter.x)*JOYSTICK_BASE_RADIUS/radius+joystickBaseCenter.x);
                    y = (int) ((y- joystickBaseCenter.y)*JOYSTICK_BASE_RADIUS/radius+joystickBaseCenter.y);
                    radius = (int)Math.sqrt((joystickBaseCenter.x-x)*(joystickBaseCenter.x-x)+
                            (joystickBaseCenter.y-y)*(joystickBaseCenter.y-y));
                }

                joystickHandleCenter.x = x;
                joystickHandleCenter.y = y;

                if(radius <= JOYSTICK_DEAD_RADIUS){
                    joystickDirVector.x = 0;
                    joystickDirVector.y = 0;
                    break;
                }

                joystickDirVector.x = (joystickHandleCenter.x - joystickBaseCenter.x)/JOYSTICK_BASE_RADIUS;
                joystickDirVector.y = (joystickHandleCenter.y - joystickBaseCenter.y)/JOYSTICK_BASE_RADIUS;


                break;
        }



        return true;
    }
}
