package com.example.projectcyber.GameActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Weapons.EtherealSpike;
import com.example.projectcyber.GameActivity.Weapons.MagicCannon;
import com.example.projectcyber.GameActivity.Weapons.MagicWand;
import com.example.projectcyber.GameActivity.Weapons.ManaBlaster;
import com.example.projectcyber.GameActivity.Weapons.MysticOrbit;
import com.example.projectcyber.GameActivity.Weapons.ViolentStar;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.uiObjects.XpProgressBar;
import com.example.projectcyber.R;
import com.example.projectcyber.GameActivity.uiObjects.HealthBar;
import com.example.projectcyber.GameActivity.uiObjects.Joystick;
import com.example.projectcyber.GameActivity.uiObjects.Timer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    //Player variables -----------------------------------------------------------------

    Player player;

    HealthBar healthBar;
    XpProgressBar xpProgressBar;

    HashMap<PlayerStatsType, Double> startingStats;

    HashSet<Projectile> projectiles;
    HashSet<Pickup> pickups;
    //Enemies variables -----------------------------------------------------------------
    HashSet<Enemy> enemies;

    EnemySummoner enemySummoner;

    //grid of the entities. will only check collisions between entities in the same square.
    HashMap<Pair<Integer, Integer>, HashSet<Entity>> entityGrid;

    //Game stuff------------------------------------------------------------------------
    private GameLoop gameLoop;
    private Context context;

    private Timer timer;

    private Joystick joystick;

    private int screenWidth;
    private int screenHeight;

    HashSet<Entity> toBeRemoved;

    private boolean isPaused;

    GameActivity activity;

    public GameView(GameActivity activity, HashMap<PlayerStatsType, Double> startingStats) {
        super(activity.getApplicationContext());
        this.context = activity.getApplicationContext();
        this.activity = activity;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);

        this.startingStats = startingStats;
    }



    private void init(SurfaceHolder surfaceHolder){

        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick();

        entityGrid = new HashMap<>();
        player = new Player(this, startingStats);

        player.addWeapon(new EtherealSpike(this));
        player.addWeapon(new ViolentStar(this));

        toBeRemoved = new HashSet<>();

        enemies = new HashSet<>();
        projectiles = new HashSet<>();
        pickups = new HashSet<>();

        enemySummoner = new EnemySummoner(this);
        healthBar = new HealthBar(this);
        xpProgressBar = new XpProgressBar(this);

        timer = new Timer(this);

    }



    /** Updates the data of the game, called every update*/
    public void update(long deltaTime){
        //Log.d("deltaTime", "deltaTime" + deltaTime);
        //add enemies from queue to main list


        joystick.update();

        if(isPaused) return;

        for(Projectile projectile : projectiles){
            projectile.update(deltaTime);

        }
        player.update(deltaTime);

        enemySummoner.update(deltaTime);

        for(Enemy enemy : enemies){
            enemy.update(deltaTime);
        }

        for(Pickup pickup : pickups){
            pickup.update(deltaTime);
        }

        for(Entity remove : toBeRemoved){
            removeEntityCompletely(remove);
        }

        toBeRemoved = new HashSet<>();

        xpProgressBar.update();
        healthBar.update();
        timer.update(deltaTime);

        //Log.d("grid", entityGrid.toString());

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

        for(Projectile projectile : projectiles){
            projectile.draw(mainCanvas);
        }

        for(Pickup pickup : pickups){
            pickup.draw(mainCanvas);
        }

        healthBar.draw(mainCanvas);
        xpProgressBar.draw(mainCanvas);

        timer.draw(mainCanvas);

        joystick.draw(mainCanvas);


        drawUPS(mainCanvas);
        drawFPS(mainCanvas);
        drawPlayerPosition(mainCanvas);
        drawPlayerSpeed(mainCanvas);
        drawPlayerHp(mainCanvas);
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

    public void drawPlayerHp(Canvas canvas){
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("CurrHealth : " + player.getCurrentHP(), 100, 340, paint);
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

    /**Summons all enemies in the list to the game*/
    public void summonEnemies(List<Enemy> enemies){

        this.enemies.addAll(enemies);
        for(Enemy enemy : enemies)
            addToGrid(enemy);
    }

    public HashSet<Enemy> getEnemies(){
        return enemies;
    }

    public int getNumberOfEnemies(){
        return enemies.size();
    }

    public boolean updateGridPlacement(@NonNull Entity entity, double prevX, double prevY){
        //the prev slot index
        Pair<Integer, Integer> prevSlotIndex = new Pair<>(getGridPositionFromPositionX(prevX),getGridPositionFromPositionY(prevY));

        //the set of the enemies in the prev slot
        HashSet<Entity> prevEntitySlot = entityGrid.get(prevSlotIndex);


        if(prevEntitySlot == null){
            Log.d("wrong", "wrong");
        }

        //tries to remove the entity
        if(prevEntitySlot.remove(entity)){

            //if successful, add the entity again in the correct slot
            addToGrid(entity);

            //if after removal the prev slot is empty, remove it from the grid
            if(prevEntitySlot.isEmpty()){
                entityGrid.remove(prevSlotIndex);
            }

            return true;
        }
        return false;
    }

    public void addToGrid(@NonNull Entity entity){
        Pair<Integer, Integer> slotIndex =new Pair<>(getGridPositionFromPositionX(entity.getPositionX()), getGridPositionFromPositionY(entity.getPositionY()));
        HashSet<Entity> mobSlot = entityGrid.get(slotIndex);
        if(mobSlot == null){
            HashSet<Entity> newEntitiesSet = new HashSet<>();
            newEntitiesSet.add(entity);
            entityGrid.put(slotIndex, newEntitiesSet);
            return;
        }
        mobSlot.add(entity);
    }

    public int getGridPositionFromPositionX(double x){
        return (int)x/500;
    }
    public int getGridPositionFromPositionY(double y){
        return (int)y/ 500;
    }


    public HashSet<Entity> getEntitiesNear(Entity entity){
        Pair<Integer, Integer> slotIndex =new Pair<>(getGridPositionFromPositionX(entity.getPositionX()), getGridPositionFromPositionY(entity.getPositionY()));
        return getNeighboringSquares(slotIndex);
    }

    /**Gets all the mobs in the neighboring square and the square itself
     * @param slotIndex the index of the center slot
     * @return a hashSet of all the mobs*/
    private HashSet<Entity> getNeighboringSquares(Pair<Integer,Integer> slotIndex){
        HashSet<Entity> entities = new HashSet<>();

        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){
                HashSet<Entity> group = entityGrid.get(new Pair<>(slotIndex.first + i,slotIndex.second+ j));
                if(group!=null) entities.addAll(group);
            }
        }
        return entities;
    }

    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
        addToGrid(projectile);
    }

    public void addPickup(Pickup pickup){
        pickups.add(pickup);
        addToGrid(pickup);
    }

    private void removeEntityCompletely(Entity entity){
        Pair<Integer, Integer> slot = new Pair<>(getGridPositionFromPositionX(entity.getPositionX()), getGridPositionFromPositionY(entity.getPositionY()));
        entityGrid.get(slot).remove(entity);
        if(entityGrid.get(slot).isEmpty())
            entityGrid.remove(slot);

        if(entity instanceof Projectile)
            projectiles.remove(entity);
        if(entity instanceof Enemy)
            enemies.remove(entity);
        if(entity instanceof Pickup)
            pickups.remove(entity);

    }

    //Add to the set of entities to be removed, doesn't remove now.
    public void removeEntity(Entity entity){
        toBeRemoved.add(entity);
    }

    public void pauseGame(){
        isPaused = true;
    }

    public void resumeGame(){
        isPaused = false;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void levelUpSequence(){
        isPaused = true;

    }

    public void showLevelUpDialog(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showLevelUpDialog();
            }
        });
    }

}
