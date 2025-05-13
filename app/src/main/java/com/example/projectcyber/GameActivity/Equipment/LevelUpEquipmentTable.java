package com.example.projectcyber.GameActivity.Equipment;

import android.text.style.UpdateLayout;

import com.example.projectcyber.GameActivity.Equipment.Items.*;
import com.example.projectcyber.GameActivity.Equipment.Weapons.*;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Player;


import java.util.HashSet;
import java.util.Random;

public class LevelUpEquipmentTable {

    public static final int MAX_CHOICES = 3;
    Random rnd = new Random();

    HashSet<Weapon> allWeapons;
    HashSet<Item> allItems;

    GameView gameView;

    public LevelUpEquipmentTable(GameView gameView){
        this.gameView = gameView;
        allWeapons = new HashSet<>();
        allItems = new HashSet<>();
        addAllItems();
        addAllWeapons();
    }

    private void addAllWeapons(){
        allWeapons.add(new EtherealSpike(gameView));
        allWeapons.add(new MagicCannon(gameView));
        allWeapons.add(new MagicWand(gameView));
        allWeapons.add(new ManaBlaster(gameView));
        allWeapons.add(new MysticOrbit(gameView));
        allWeapons.add(new ViolentStar(gameView));
    }

    private void addAllItems(){
        allItems.add(new CoffeeTorus());
        allItems.add(new CompoundW());
        allItems.add(new DiamondArmor());
        allItems.add(new Duplicator());
        allItems.add(new Electromagnet());
        allItems.add(new Gauntlets());
        allItems.add(new GoldenCarrot());
        allItems.add(new GreenBull());
        allItems.add(new Grimoire());
        allItems.add(new WirelessTransmitter());
    }

    public HashSet<Equipment> getOptions(){
        HashSet<Equipment> options = new HashSet<>();

        Player player =gameView.getPlayer();
        for(int i = 0; i< MAX_CHOICES; i++){

            boolean owned = rnd.nextDouble() <= 0.2; //20% for owned items.


            Equipment picked;
            if(owned && canAnythingLevelUp())
                picked = pickOwned();
            else if(player.haveSpace())
                picked = pickUnOwned();
            else
                return options;


            if(options.contains(picked))
                i--;
            else
                options.add(picked);
        }

        return options;
    }

    private boolean canAnythingLevelUp(){
        Player player = gameView.getPlayer();
        for(Weapon weapon : player.getWeapons())
            if(weapon.canRaiseLevel())return true;
        for(Item item : player.getItems())
            if(item.canRaiseLevel()) return true;
        return false;
    }

    private Equipment pickOwned(){

        Player player = gameView.getPlayer();

        int pick = rnd.nextInt(player.getAmountOfUpgradableWeapons() + player.getAmountOfUpgradableItems());
        int k = 0;
        for(Weapon weapon : player.getWeapons()){
            if(k==pick){
                if(!weapon.canRaiseLevel()) continue;
                return weapon;
            }
            k++;
        }
        for(Item item : player.getItems()){
            if(k == pick){
                if(!item.canRaiseLevel()) continue;
                return item;
            }
            k++;
        }

        return pickUnOwned();
    }

    private Equipment pickUnOwned(){
        int pick = rnd.nextInt(allWeapons.size() + allItems.size());
        int k = 0;
        for(Weapon weapon : allWeapons){
            if(k==pick){
                return weapon;
            }
            k++;
        }
        for(Item item : allItems){
            if(k == pick){
                return item;
            }
            k++;
        }
        return null;
    }

    private Weapon pickFromAllLevelUpableWeapons(){
        int pick = rnd.nextInt(allWeapons.size());
        int k = 0;
        for(Weapon weapon : allWeapons){
            if(k==pick){
                if(weapon.canRaiseLevel())
                    return weapon;
                else{
                    return pickFromAllLevelUpableWeapons();
                }
            }
            k++;
        }
        return null;
    }

    private Item pickFromAllLevelUpableItems(){
        int pick = rnd.nextInt(allItems.size());
        int k = 0;
        for(Item item : allItems){
            if(k==pick){
                if(item.canRaiseLevel())
                    return item;
                else{
                    return pickFromAllLevelUpableItems();
                }
            }
            k++;
        }
        return null;
    }

    public Weapon getWeapon(Weapon weapon){
        for(Weapon weaponItr : allWeapons){
            if(weaponItr.name == weapon.name)
                return weaponItr;
        }
        return null;
    }
}
