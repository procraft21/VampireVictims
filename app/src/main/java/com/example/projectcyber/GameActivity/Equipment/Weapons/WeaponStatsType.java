package com.example.projectcyber.GameActivity.Equipment.Weapons;

import com.example.projectcyber.Menu.StatType;

public enum WeaponStatsType implements StatType {
    Duration, Damage, Cooldown, Speed, Amount, Pierce, ProjectileInterval, Area;

    public String getName(){
        switch (this){
            case Area:return "area";
            case Speed: return "projectile speed";
            case Amount: return "bullet amount";
            case Damage: return "weapon damage";
            case Pierce: return "enemies pierced";
            case Cooldown: return "weapon cooldown";
            case Duration: return "weapon duration";
            case ProjectileInterval: return "interval of shots";
        }
        return "";
    }
}
