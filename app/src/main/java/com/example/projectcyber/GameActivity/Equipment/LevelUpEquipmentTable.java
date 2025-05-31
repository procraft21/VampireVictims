package com.example.projectcyber.GameActivity.Equipment;

import android.media.audiofx.DynamicsProcessing;

import com.example.projectcyber.GameActivity.Equipment.Items.*;
import com.example.projectcyber.GameActivity.Equipment.Weapons.*;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Handles generation of equipment choices during level-up.
 * Offers the player a set of upgrade options based on what they own,
 * what is available, and their inventory space.
 */
public class LevelUpEquipmentTable {

    /** Maximum number of equipment choices to offer on level-up. */
    public static final int MAX_CHOICES = 3;

    /** Random number generator for equipment selection. */
    Random rnd = new Random();

    /** Set of all possible weapons available to the player. */
    HashSet<Weapon> allWeapons;

    /** Set of all possible items available to the player. */
    HashSet<Item> allItems;

    /** Reference to the GameView containing game context. */
    GameView gameView;

    /**
     * Constructs a new LevelUpEquipmentTable.
     *
     * @param gameView the current game view instance
     */
    public LevelUpEquipmentTable(GameView gameView){
        this.gameView = gameView;
        allWeapons = new HashSet<>();
        allItems = new HashSet<>();
        addAllItems();
        addAllWeapons();
    }

    /**
     * Populates the internal set with all weapon types.
     */
    private void addAllWeapons(){
        allWeapons.add(new EtherealSpike(gameView));
        allWeapons.add(new MagmaShot(gameView));
        allWeapons.add(new MagicWand(gameView));
        allWeapons.add(new ManaBlaster(gameView));
        allWeapons.add(new MysticOrbit(gameView));
        allWeapons.add(new ViolentStar(gameView));
    }

    /**
     * Populates the internal set with all item types.
     */
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

    /**
     * Retrieves a random selection of equipment options for the player.
     * Takes into account current equipment, space for new equipment, and level-up potential.
     *
     * @return a set of up to MAX_CHOICES equipment options
     */
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

        Player player = gameView.getPlayer();

        for(int i = 0; i < MAX_CHOICES; i++){
            boolean owned = rnd.nextDouble() <= 0.2; // 20% chance to pick an already owned equipment

            if(ownedEligibleEquipment.size() == 0 && !player.haveItemSpace() && !player.haveWeaponSpace()){
                return options; // No options possible
            }

            if(owned && ownedEligibleEquipment.size() > 0){
                Equipment picked = pickFromArray(ownedEligibleEquipment);
                options.add(picked);
                if(picked instanceof Weapon) {
                    eligibleWeapons.remove(picked);
                } else if(picked instanceof Item){
                    eligibleItems.remove(picked);
                }
                ownedEligibleEquipment.remove(picked);
            } else {
                if(player.haveWeaponSpace()){
                    if(player.haveItemSpace()){
                        ArrayList<Equipment> combined = new ArrayList<>();
                        combined.addAll(eligibleWeapons);
                        combined.addAll(eligibleItems);
                        Equipment picked = pickFromArray(combined);
                        options.add(picked);
                        if(picked instanceof Weapon) {
                            eligibleWeapons.remove(picked);
                        } else if(picked instanceof Item){
                            eligibleItems.remove(picked);
                        }
                    } else {
                        Equipment picked = pickFromArray(eligibleWeapons);
                        options.add(picked);
                        eligibleWeapons.remove(picked);
                        ownedEligibleEquipment.remove(picked);
                    }
                } else {
                    if(player.haveItemSpace()){
                        Equipment picked = pickFromArray(eligibleItems);
                        options.add(picked);
                        eligibleItems.remove(picked);
                        ownedEligibleEquipment.remove(picked);
                    } else {
                        Equipment picked = pickFromArray(ownedEligibleEquipment);
                        options.add(picked);
                        if(picked instanceof Weapon) {
                            eligibleWeapons.remove(picked);
                        } else if(picked instanceof Item){
                            eligibleItems.remove(picked);
                        }
                        ownedEligibleEquipment.remove(picked);
                    }
                }
            }
        }

        return options;
    }

    /**
     * Selects a random element from the given list.
     *
     * @param list list to select from
     * @param <T>  subtype of Equipment
     * @return randomly selected element
     */
    private <T extends Equipment> T pickFromArray(ArrayList<T> list){
        return list.get(rnd.nextInt(list.size()));
    }

    /**
     * Retrieves the canonical instance of a weapon by matching name.
     *
     * @param weapon the weapon to look for
     * @return matching weapon instance from the pool or null if not found
     */
    public Weapon getWeapon(Weapon weapon){
        for(Weapon weaponItr : allWeapons){
            if(weaponItr.name == weapon.name)
                return weaponItr;
        }
        return null;
    }
}
