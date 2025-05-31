package com.example.projectcyber.GameActivity;

import android.util.Log;
import android.util.Pair;

import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.EnemyBat;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.EnemyBatKnight;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.FollowerEnemy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Handles enemy spawning behavior during gameplay.
 * Enemies are summoned based on the game's current "SummoningSlot", which
 * determines enemy types, frequency, and minimum count.
 */
public class EnemySummoner {

    /**
     * Internal class representing a wave of enemy summoning logic.
     */
    class SummoningSlot {
        long length;                // Duration of this slot in milliseconds
        long summoningInterval;    // Time between summons
        int minimumEnemies;        // Minimum number of enemies always maintained
        Set<Enemy> enemies;        // Possible enemy types in this slot

        /**
         * Constructs a new SummoningSlot.
         *
         * @param length            Duration of the slot in ms
         * @param summoningInterval Interval between summons in ms
         * @param minimumEnemies    Minimum number of enemies alive
         * @param enemies           Set of enemy prototypes to summon
         */
        public SummoningSlot(long length, long summoningInterval, int minimumEnemies, Set<Enemy> enemies) {
            this.length = length;
            this.summoningInterval = summoningInterval;
            this.minimumEnemies = minimumEnemies;
            this.enemies = new HashSet<>(enemies);
        }
    }

    private static final double SUMMON_OUTER_RING_MODIFIER = 1.2;
    private static final double SUMMON_INNER_RING_MODIFIER = 1.01;

    private static final Random rnd = new Random();

    private final GameView gameView;

    private long timeSinceLastSummon = 0;
    private long timeSinceSlotStarted = 0;
    private int currSlotIndex = 0;
    private final ArrayList<SummoningSlot> summoningList;

    /**
     * Constructs a new EnemySummoner for a given GameView.
     *
     * @param gameView The active GameView instance controlling game state
     */
    public EnemySummoner(GameView gameView) {
        this.gameView = gameView;
        summoningList = new ArrayList<>();

        // Define summoning slots with escalating difficulty
        HashSet<Enemy> enemies = new HashSet<>();
        enemies.add(new EnemyBat(gameView, 0, 0));
        summoningList.add(new SummoningSlot(120_000, 500, 30, enemies));

        enemies.add(new EnemyBatKnight(gameView, 0, 0));
        summoningList.add(new SummoningSlot(60_000, 500, 40, enemies));

        enemies.clear();
        enemies.add(new EnemyBatKnight(gameView, 0, 0));
        summoningList.add(new SummoningSlot(120_000, 250, 50, enemies));
    }

    /**
     * Called every game tick to update the summoning logic.
     *
     * @param deltaTime Time passed since last update in milliseconds
     */
    public void update(long deltaTime) {
        timeSinceSlotStarted += deltaTime;
        timeSinceLastSummon += deltaTime;

        if (currSlotIndex >= summoningList.size()) {
            gameView.showResultDialog(true); // Game won
            return;
        }

        SummoningSlot currentSlot = summoningList.get(currSlotIndex);

        if (timeSinceSlotStarted >= currentSlot.length) {
            currSlotIndex++;
            timeSinceSlotStarted = 0;
            return;
        }

        ArrayList<Enemy> enemiesToSpawn = new ArrayList<>();

        if (timeSinceLastSummon >= currentSlot.summoningInterval) {
            enemiesToSpawn.add(getEnemyFromSlot());
            timeSinceLastSummon = 0;
        }

        while (gameView.getNumberOfEnemies() + enemiesToSpawn.size() < currentSlot.minimumEnemies) {
            enemiesToSpawn.add(getEnemyFromSlot());
        }

        gameView.summonEnemies(enemiesToSpawn);
    }

    /**
     * Randomly selects and clones an enemy from the current summoning slot.
     *
     * @return A new cloned Enemy with randomized spawn position
     */
    private Enemy getEnemyFromSlot() {
        Set<Enemy> enemySet = summoningList.get(currSlotIndex).enemies;
        int item = rnd.nextInt(enemySet.size());
        int i = 0;

        for (Enemy enemy : enemySet) {
            if (i == item) {
                Pair<Double, Double> newPos = getRandomEnemyPosition();
                try {
                    enemy.setPosX(newPos.first);
                    enemy.setPosY(newPos.second);
                    return (Enemy) enemy.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            i++;
        }

        return null;
    }

    /**
     * Computes a random spawn position in a ring around the player.
     *
     * @return A pair representing the (x, y) coordinates of the spawn point
     */
    private Pair<Double, Double> getRandomEnemyPosition() {
        double playerPosX = gameView.getPlayer().getPositionX();
        double playerPosY = gameView.getPlayer().getPositionY();

        double screenWidth = gameView.getWidth();
        double screenHeight = gameView.getHeight();

        double distToCorner = Utils.distance(0, 0, screenWidth / 2, screenHeight / 2);

        double radius = rnd.nextInt((int) (distToCorner * (SUMMON_OUTER_RING_MODIFIER - SUMMON_INNER_RING_MODIFIER)))
                + distToCorner * SUMMON_INNER_RING_MODIFIER;

        double angle = rnd.nextDouble() * Math.PI * 2;

        double posX = playerPosX + radius * Math.cos(angle);
        double posY = playerPosY + radius * Math.sin(angle);

        return new Pair<>(posX, posY);
    }
}
