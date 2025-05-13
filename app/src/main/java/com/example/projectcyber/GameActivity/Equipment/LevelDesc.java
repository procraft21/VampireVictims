package com.example.projectcyber.GameActivity.Equipment;

public class LevelDesc {
    private String name;
    private int level;
    private String desc;

    public LevelDesc(String name, int level, String desc){
        this.name = name;
        this.level = level;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
