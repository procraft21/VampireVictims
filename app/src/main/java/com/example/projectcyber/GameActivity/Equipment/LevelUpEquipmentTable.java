package com.example.projectcyber.GameActivity.Equipment;

import android.media.audiofx.DynamicsProcessing;

import com.example.projectcyber.GameActivity.Equipment.Items.*;
import com.example.projectcyber.GameActivity.Equipment.Weapons.*;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Player;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
        allWeapons.add(new MagmaShot(gameView));
        allWeapons.add(new MagicWand(gameView));
        allWeapons.add(new ManaBlaster(gameView));
        allWeapons.add(new MysticOrbit(gameView));
        allWeapons.add(new ViolentStar(gameView));
    }

    private void addAllItems(){
        allItems.add(new CoffeeDonut(gameView));
        allItems.add(new CompoundW(gameView));
        allItems.add(new DiamondArmor(gameView));
        allItems.add(new Duplicator(gameView));
        allItems.add(new Electromagnet(gameView));
        allItems.add(new Gauntlets(gameView));
        allItems.add(new GoldenCarrot(gameView));
        allItems.add(new GreenBull(gameView));
        allItems.add(new Grimoire(gameView));
        allItems.add(new WirelessTransmitter(gameView));
    }

    public HashSet<Equipment> getOptions(){
        HashSet<Equipment> options = new HashSet<>();

        ArrayList<Equipment> ownedEligibleEquipment = new ArrayList<>();

        ArrayList<Weapon> eligibleWeapons = new ArrayList<>();
        for(Weapon weapon : allWeapons){
            if(weapon.canRaiseLevel()){
                eligibleWeapons.add(weapon);
                if(weapon.level > 0)
                    ownedEligibleEquipment.add(weapon);
            }
        }

        ArrayList<Item> eligibleItems = new ArrayList<>();
        for(Item item : allItems){
            if(item.canRaiseLevel()){
                eligibleItems.add(item);
                if(item.level > 0)
                    ownedEligibleEquipment.add(item);
            }
        }

        Player player =gameView.getPlayer();
        for(int i = 0; i< MAX_CHOICES; i++){

            boolean owned = rnd.nextDouble() <= 0.2; //20% for owned items.

            if(ownedEligibleEquipment.size() == 0 && !player.haveItemSpace() && !player.haveWeaponSpace()){
                return options;
            }

            if(owned && ownedEligibleEquipment.size() > 0){
                Equipment picked = pickFromArray(ownedEligibleEquipment);
                options.add(picked);
                if(picked instanceof Weapon) {
                    eligibleWeapons.remove(picked);
                }else if(picked instanceof Item){
                    eligibleItems.remove(picked);
                }
                ownedEligibleEquipment.remove(picked);
            }else{
                if(player.haveWeaponSpace()){
                    if(player.haveItemSpace()){
                        //have both weapon and item space
                        ArrayList<Equipment> combined = new ArrayList<>();
                        combined.addAll(eligibleWeapons);
                        combined.addAll(eligibleItems);
                        Equipment picked = pickFromArray(combined);
                        options.add(picked);
                        if(picked instanceof Weapon) {
                            eligibleWeapons.remove(picked);
                        }else if(picked instanceof Item){
                            eligibleItems.remove(picked);
                        }
                    }else{
                        //have only  weapon space
                        Equipment picked = pickFromArray(eligibleWeapons);
                        options.add(picked);
                        eligibleWeapons.remove(picked);
                        ownedEligibleEquipment.remove(picked);
                    }
                }else{
                    if(player.haveItemSpace()){
                        //have only item space
                        Equipment picked = pickFromArray(eligibleItems);
                        options.add(picked);
                        eligibleItems.remove(picked);
                        ownedEligibleEquipment.remove(picked);
                    }else{
                        //have no space
                        Equipment picked = pickFromArray(ownedEligibleEquipment);
                        options.add(picked);
                        if(picked instanceof Weapon) {
                            eligibleWeapons.remove(picked);
                        }else if(picked instanceof Item){
                            eligibleItems.remove(picked);
                        }
                        ownedEligibleEquipment.remove(picked);
                    }
                }
            }
        }

        return options;
    }

    private Equipment pickFromArray(ArrayList<? extends Equipment> list){
        return list.get(rnd.nextInt(list.size()));
    }

    public Weapon getWeapon(Weapon weapon){
        for(Weapon weaponItr : allWeapons){
            if(weaponItr.name == weapon.name)
                return weaponItr;
        }
        return null;
    }
}
