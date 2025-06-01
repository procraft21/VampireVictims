package com.example.projectcyber.Menu;

public enum PlayerStatsType implements StatType{
    MaxHp,MoveSpd,Duration,Might,Amount,Armor, Recovery, ProjectileSpd, Cooldown, Magnet;

    @Override
    public String getName() {
        switch (this){
            case Duration:return"Duration";
            case Cooldown:return "Cooldown";
            case Amount : return "Amount";
            case Armor:return"Armor";
            case MaxHp:return"Max Hp";
            case Might: return "Might";
            case Magnet: return "Magnet";
            case MoveSpd: return "Move Speed";
            case Recovery: return "Recovery";
            case ProjectileSpd: return "Projectile Speed";
        }
        return "";
    }
}