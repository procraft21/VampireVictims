package com.example.projectcyber;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.projectcyber.gameObjects.Enemy;
import com.example.projectcyber.gameObjects.Player;
import com.example.projectcyber.uiObjects.HealthBar;
import com.example.projectcyber.uiObjects.Joystick;
import com.example.projectcyber.uiObjects.Timer;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    /*//SurfaceView variables ---------------------------------------------------------
    private SurfaceHolder holder = getHolder();
    private Canvas mainCanvas;
    private int INTERVAL = 17;*/
    //Player variables -----------------------------------------------------------------
    private static final int PLAYER_WIDTH = 150;
    private static final int PLAYER_HEIGHT = 150;
    Bitmap playerBitmap;
    Player player;

    HealthBar healthBar;

    //Enemies variables -----------------------------------------------------------------
    ArrayList<Enemy> enemyQueue;
    ArrayList<Enemy> enemies;

    EnemySummoner enemySummoner;

    //Game stuff------------------------------------------------------------------------
    private GameLoop gameLoop;
    private Context context;

    private Timer timer;

    private Joystick joystick;

    private int screenWidth;
    private int screenHeight;


    public GameView(Context context) {
        super(context);
        this.context = context;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
    }



    private void init(SurfaceHolder surfaceHolder){

        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick();

        player = new Player(this);
        playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_img);
        playerBitmap = Bitmap.createScaledBitmap(playerBitmap,PLAYER_WIDTH, PLAYER_HEIGHT, false);

        enemyQueue = new ArrayList<>();
        enemies = new ArrayList<>();

        enemySummoner = new EnemySummoner(this);

        healthBar = new HealthBar(this);

        timer = new Timer(this);

        player.setBitmap(playerBitmap);
        player.takeDamage(10);
    }

    /** Updates the data of the game, called every update*/
    public void update(long deltaTime){
        //Log.d("deltaTime", "deltaTime" + deltaTime);
        //add enemies from queue to main list
        enemies.addAll(enemyQueue);
        enemyQueue.clear();

        joystick.update();
        player.update(deltaTime);
        for(Enemy enemy : enemies){
            enemy.update(deltaTime);
        }

        enemySummoner.update(deltaTime);

        timer.update(deltaTime);

    }

    /** Draws the game onto the canvas, called every update*/
    public void draw(Canvas mainCanvas){
        super.draw(mainCanvas);
        mainCanvas.drawColor(0xFF006600);

        screenWidth = getWidth();
        screenHeight = getHeight();

        player.draw(mainCanvas);

        for(Enemy enemy : enemies){
            enemy.draw(mainCanvas);
        }

        healthBar.draw(mainCanvas);

        timer.draw(mainCanvas);

        joystick.draw(mainCanvas);

        drawUPS(mainCanvas);
        drawFPS(mainCanvas);
        drawPlayerPosition(mainCanvas);
        drawPlayerSpeed(mainCanvas);
    }

    public void startGame(SurfaceHolder surfaceHolder){
        init(surfaceHolder);
        gameLoop.startLoop();
    }

    public void drawUPS(Canvas canvas){
        double averageUPS = gameLoop.getAverageUPS();
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS : " + averageUPS, 100, 40, paint);
    }
    public void drawFPS(Canvas canvas){
        double averageFPS = gameLoop.getAverageFPS();
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS : " + averageFPS, 100, 100, paint);
    }
    public void drawPlayerPosition(Canvas canvas){
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("X : " + player.getPositionX() + "Y : " + player.getPositionY(), 100, 160, paint);
    }
    public void drawPlayerSpeed(Canvas canvas){
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("velX :" + player.getVelX() + " velY : " + player.getVelY(),100, 220, paint);
        canvas.drawText("velSize : " + Utils.distance(player.getVelX(), player.getVelY(), 0, 0), 100, 280, paint);
    }
    // functions from SurfaceHolder.Callback
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        startGame(surfaceHolder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX(), y = (int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                joystick.setIsJoystickOn(true); //turns on the joystick.
                joystick.setBase(x,y);
                joystick.resetActuator();
                break;

            case MotionEvent.ACTION_UP:
                joystick.setIsJoystickOn(false);
                joystick.resetActuator();
                break;

            case MotionEvent.ACTION_MOVE:
                joystick.setActuator(x,y);
                break;
        }
        return true;
    }

    public Player getPlayer(){
        return player;
    }

    public Joystick getJoystick(){
        return joystick;
    }

    public long getTime(){
        return timer.getTime();
    }

    /**Adds all enemies in given list to the enemies queue. */
    public void summonEnemies(List<Enemy> enemies){
        enemyQueue.addAll(enemies);
    }

    public int getNumberOfEnemies(){
        return enemies.size() + enemyQueue.size();
    }
}
