package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Mob;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.R;

public class Enemy extends Mob implements Cloneable{
    private static Bitmap bitmap;

    //movement speed
    protected int speed = 100;

    //raw damage to player without armor.
    protected int might = 5;

    protected int maxHp = 1;

    private static DropTable dropTable = null;

    public Enemy(GameView gameView, double posX, double posY){
        super(posX,posY, gameView);
        if(bitmap == null){
            Bitmap enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.enemy_img);
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,75,75, false);
            this.bitmap = enemyBitmap;

        }
        if(dropTable == null){
            dropTable = new DropTable(gameView);
        }

        this.currHP = maxHp;
    }

    public Enemy(Enemy enemy, double posX, double posY){
        super(posX, posY, enemy.gameView);
        this.speed = enemy.speed;
        if(bitmap == null){
            Bitmap enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.enemy_img);
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,75,75, false);
            this.bitmap = enemyBitmap;


        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void takeDamage(double damage) {
        super.takeDamage(damage);
        if(currHP <= 0){
            destroy();
        }
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap,(int)(relX - bitmap.getWidth()/2), (int)(relY - bitmap.getHeight()/2), null );

        /*int color = ContextCompat.getColor(gameView.getContext(), R.color.magenta);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("" + this.tag, (int)(relX - bitmap.getWidth()/2), (int)(relY +50), paint);*/
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        Enemy clone = (Enemy) super.clone();
        return clone;
    }

    @Override
    public double getCollisionRadius() {
        return super.getCollisionRadius();
    }

    public int getMight(){
        return might;
    }

    @Override
    public void destroy() {
        super.destroy();

        Pickup pickup = dropTable.getDrop();
        if(pickup != null){
            pickup.setPosX(posX);
            pickup.setPosY(posY);
            gameView.addPickup(pickup);
        }
    }
}
