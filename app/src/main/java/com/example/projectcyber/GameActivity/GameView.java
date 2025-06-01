package com.example.projectcyber.GameActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Equipment.EquipmentTable;
import com.example.projectcyber.GameActivity.Equipment.Weapons.MagicWand;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.DropTable;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.uiObjects.CoinCounter;
import com.example.projectcyber.GameActivity.uiObjects.XpProgressBar;
import com.example.projectcyber.R;
import com.example.projectcyber.GameActivity.uiObjects.HealthBar;
import com.example.projectcyber.GameActivity.uiObjects.Joystick;
import com.example.projectcyber.GameActivity.uiObjects.Timer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * GameView is the main SurfaceView that handles rendering and updating
 * the core game loop, game objects (player, enemies, projectiles, pickups),
 * UI elements, and collision logic via grid-based partitioning.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // ----------------- Player variables -----------------
    private Player player;
    private HashMap<PlayerStatsType, Double> startingStats;
    private EquipmentTable equipmentTable;
    private int coinsAmount;

    // ----------------- Entities variables -----------------
    private HashSet<Enemy> enemies;
    private EnemySummoner enemySummoner;
    private DropTable dropTable;

    private HashSet<Projectile> projectiles;

    private HashSet<Pickup> pickups;

    private HashSet<Entity> toBeRemoved;

    // Grid for spatial partitioning, improves collision detection performance
    private HashMap<Pair<Integer, Integer>, HashSet<Entity>> entityGrid;

    // ----------------- UI mechanics -------------------

    private HealthBar healthBar;
    private XpProgressBar xpProgressBar;
    private Timer timer;
    private Joystick joystick;
    private CoinCounter coinCounter;
    private GameBackground gameBackground;

    // ----------------- Game mechanics -----------------
    private GameLoop gameLoop;

    private Context context;
    private GameActivity activity;

    private int screenWidth;
    private int screenHeight;

    private boolean isPaused;


    /**
     * Constructor for GameView
     * @param activity Reference to GameActivity
     * @param startingStats Player's initial stats
     */
    public GameView(GameActivity activity, HashMap<PlayerStatsType, Double> startingStats) {
        super(activity.getApplicationContext());

        this.context = activity.getApplicationContext();
        this.activity = activity;

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);

        this.startingStats = startingStats;
    }

    /**
     * Initializes all game components and state.
     * Called when the game is first started.
     */
    private void init(SurfaceHolder surfaceHolder) {
        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick();

        entityGrid = new HashMap<>();
        player = new Player(this, startingStats);
        equipmentTable = new EquipmentTable(this);
        player.addWeapon(equipmentTable.getWeapon(new MagicWand(this)));

        toBeRemoved = new HashSet<>();
        enemies = new HashSet<>();
        dropTable = new DropTable(this);

        projectiles = new HashSet<>();
        pickups = new HashSet<>();

        coinsAmount = 0;
        coinCounter = new CoinCounter(this);

        enemySummoner = new EnemySummoner(this);
        healthBar = new HealthBar(this);
        xpProgressBar = new XpProgressBar(this);
        timer = new Timer(this);
        gameBackground = new GameBackground(this);
    }

    /**
     * Updates the game state every frame.
     * @param deltaTime Time passed since last update (ms)
     */
    public void update(long deltaTime) {
        joystick.update();

        if (isPaused) return;

        player.update(deltaTime);
        enemySummoner.update(deltaTime);

        for (Projectile projectile : projectiles) {
            projectile.update(deltaTime);
        }

        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }

        for (Pickup pickup : pickups) {
            pickup.update(deltaTime);
        }

        for (Entity remove : toBeRemoved) {
            removeEntityCompletely(remove);
        }
        toBeRemoved.clear();

        xpProgressBar.update();
        healthBar.update();
        coinCounter.update();
        timer.update(deltaTime);
        gameBackground.update();
    }

    /**
     * Draws the game view to the provided canvas.
     * @param mainCanvas Canvas to draw on
     */
    public void draw(Canvas mainCanvas) {
        super.draw(mainCanvas);
        mainCanvas.drawColor(0xFF006600); // background color

        gameBackground.draw(mainCanvas);

        screenWidth = getWidth();
        screenHeight = getHeight();

        player.draw(mainCanvas);

        for (Pickup pickup : pickups) {
            pickup.draw(mainCanvas);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(mainCanvas);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(mainCanvas);
        }

        healthBar.draw(mainCanvas);
        xpProgressBar.draw(mainCanvas);
        coinCounter.draw(mainCanvas);
        timer.draw(mainCanvas);
        joystick.draw(mainCanvas);
    }

    /**
     * Starts the game loop.
     */
    public void startGame(SurfaceHolder surfaceHolder) {
        init(surfaceHolder);
        gameLoop.startLoop();
    }

    // Debugging draw functions -------------------------------------------------

    public void drawUPS(Canvas canvas) {
        double averageUPS = gameLoop.getAverageUPS();
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS : " + averageUPS, 100, 40, paint);
    }

    public void drawFPS(Canvas canvas) {
        double averageFPS = gameLoop.getAverageFPS();
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS : " + averageFPS, 100, 100, paint);
    }

    public void drawPlayerPosition(Canvas canvas) {
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("X : " + player.getPositionX() + "Y : " + player.getPositionY(), 100, 160, paint);
    }

    public void drawPlayerSpeed(Canvas canvas) {
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("velX :" + player.getVelX() + " velY : " + player.getVelY(), 100, 220, paint);
        canvas.drawText("velSize : " + Utils.distance(player.getVelX(), player.getVelY(), 0, 0), 100, 280, paint);
    }

    public void drawPlayerHp(Canvas canvas) {
        int color = ContextCompat.getColor(context, R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("CurrHealth : " + player.getCurrentHP(), 100, 340, paint);
    }

    // SurfaceHolder.Callback implementation ----------------------------------

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop != null && gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder holder = getHolder();
            holder.addCallback(this);

            gameLoop = new GameLoop(this, surfaceHolder);
            gameLoop.startLoop();
        } else {
            startGame(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("GameView.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("GameView.java", "surfaceDestroyed()");
    }

    /**
     * Handles touch events for the joystick.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                joystick.setIsJoystickOn(true);
                joystick.setBase(x, y);
                joystick.resetActuator();
                break;

            case MotionEvent.ACTION_UP:
                joystick.setIsJoystickOn(false);
                joystick.resetActuator();
                break;

            case MotionEvent.ACTION_MOVE:
                joystick.setActuator(x, y);
                break;
        }
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public Joystick getJoystick() {
        return joystick;
    }

    public long getTime() {
        return timer.getTime();
    }

    /**
     * Summons all enemies in the given list.
     * @param enemies the list of enemies to summon.
     */
    public void summonEnemies(List<Enemy> enemies) {
        this.enemies.addAll(enemies);
        for (Enemy enemy : enemies) {
            addToGrid(enemy);
        }
    }

    public HashSet<Enemy> getEnemies() {
        return enemies;
    }

    public int getNumberOfEnemies() {
        return enemies.size();
    }

    /**
     * Updates the entity's position in the entity grid.
     * @param entity the entity whose position changed.
     */
    public boolean updateGridPlacement(@NonNull Entity entity, double prevX, double prevY) {
        Pair<Integer, Integer> prevSlotIndex = new Pair<>(getGridPositionFromPositionX(prevX), getGridPositionFromPositionY(prevY));
        HashSet<Entity> prevEntitySlot = entityGrid.get(prevSlotIndex);

        if (prevEntitySlot == null) {
            entity.destroy();
            return true;
        }

        if (prevEntitySlot.remove(entity)) {
            addToGrid(entity);
            if (prevEntitySlot.isEmpty()) {
                entityGrid.remove(prevSlotIndex);
            }
            return true;
        }
        return false;
    }

    /**
     * Adds the entity to the entity grid.
     * @param entity the entity you want to add.
     */
    public void addToGrid(@NonNull Entity entity) {
        Pair<Integer, Integer> slotIndex = new Pair<>(getGridPositionFromPositionX(entity.getPositionX()), getGridPositionFromPositionY(entity.getPositionY()));
        HashSet<Entity> mobSlot = entityGrid.get(slotIndex);
        if (mobSlot == null) {
            HashSet<Entity> newEntitiesSet = new HashSet<>();
            newEntitiesSet.add(entity);
            entityGrid.put(slotIndex, newEntitiesSet);
            return;
        }
        mobSlot.add(entity);
    }

    /**
     * Get the x grid coordinate from the position
     * @param x the x position.
     * @return the grid coordinate
     */
    public int getGridPositionFromPositionX(double x) {
        return (int) x / 500;
    }

    /**
     * Get the y grid coordinate from the position
     * @param y the y position.
     * @return the grid coordinate
     */
    public int getGridPositionFromPositionY(double y) {
        return (int) y / 500;
    }

    /**
     * Retrieves all entities near a given entity for collision checks.
     * 'near' is defined as in the same square or adjacent squares.
     * @param entity the entity you want to get the entities near
     */
    public HashSet<Entity> getEntitiesNear(Entity entity) {
        Pair<Integer, Integer> slotIndex = new Pair<>(getGridPositionFromPositionX(entity.getPositionX()), getGridPositionFromPositionY(entity.getPositionY()));
        return getNeighboringSquares(slotIndex);
    }

    /**
     * @return all entities in a 3x3 grid centered at the given grid slot.
     */
    private HashSet<Entity> getNeighboringSquares(Pair<Integer, Integer> slotIndex) {
        HashSet<Entity> entities = new HashSet<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                HashSet<Entity> group = entityGrid.get(new Pair<>(slotIndex.first + i, slotIndex.second + j));
                if (group != null) entities.addAll(group);
            }
        }
        return entities;
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
        addToGrid(projectile);
    }

    public void addPickup(Pickup pickup) {
        pickups.add(pickup);
        addToGrid(pickup);
    }

    /**
     * Completely removes a given entity from the game.
     */
    private void removeEntityCompletely(Entity entity) {
        Pair<Integer, Integer> slot = new Pair<>(getGridPositionFromPositionX(entity.getPositionX()), getGridPositionFromPositionY(entity.getPositionY()));
        entityGrid.get(slot).remove(entity);
        if (entityGrid.get(slot).isEmpty())
            entityGrid.remove(slot);

        if (entity instanceof Projectile)
            projectiles.remove(entity);
        if (entity instanceof Enemy)
            enemies.remove(entity);
        if (entity instanceof Pickup)
            pickups.remove(entity);
    }

    /**
     * Flags an entity for removal in the next update cycle.
     */
    public void removeEntity(Entity entity) {
        toBeRemoved.add(entity);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Displays the level-up UI on the main thread.
     */
    public void showLevelUpDialog() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HashSet<Equipment> equipmentOptions = equipmentTable.getOptions();
                activity.showLevelUpDialog(equipmentOptions);
            }
        });
    }

    public int getCoins() {
        return coinsAmount;
    }

    public void addCoins(int coins) {
        this.coinsAmount += coins;
    }

    public DropTable getDropTable() {
        return dropTable;
    }

    /**
     * Displays the result dialog (win/lose).
     */
    public void showResultDialog(boolean won) {
        pauseEntities();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showResultDialog(won);
            }
        });
    }

    public void pauseEntities() {
        isPaused = true;
    }

    public void resumeEntities() {
        isPaused = false;
    }

    public void stopGame() {
        gameLoop.stopGame();
    }
}
