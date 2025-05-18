package com.example.projectcyber.GameActivity.Equipment;

import android.graphics.Bitmap;

public abstract class Equipment {
    protected int level;
    protected int maxLevel;
    protected String name;
    protected String initialDesc;

    protected Bitmap equipmentBitmap;

    public boolean canRaiseLevel(){
        return level<maxLevel;
    }

    public LevelDesc getNextLevelDescription(){
        return new LevelDesc(name, level+1, level == 0 ? initialDesc : getNextLevelStatsDescription());
    }

    protected abstract String getNextLevelStatsDescription();

    public abstract boolean raiseLevel();

    public String getName(){
        return this.name;
    }

    public int getLevel() {
        return level;
    }

    public Bitmap getEquipmentBitmap() {
        return equipmentBitmap;
    }
}
