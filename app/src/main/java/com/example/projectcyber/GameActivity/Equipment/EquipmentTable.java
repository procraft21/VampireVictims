package com.example.projectcyber.GameActivity.Equipment;

import com.example.projectcyber.GameActivity.Equipment.Items.*;
import com.example.projectcyber.GameActivity.Equipment.Weapons.*;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;


public class EquipmentTable {

    /** Random number generator for equipment selection. */
    static Random rnd = new Random();

    /** Maximum number of equipment choices to offer on level-up. */
    public static final int MAX_CHOICES = 3;

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
    public EquipmentTable(GameView gameView){
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
    public HashSet<Equipment> getOptions() {
        HashSet<Equipment> options = new HashSet<>(); // Final set of equipment options to return

        ArrayList<Equipment> ownedEligibleEquipment = new ArrayList<>(); // Owned equipment that can be upgraded

        ArrayList<Weapon> eligibleWeapons = new ArrayList<>(); // All weapons that can be upgraded
        for (Weapon weapon : allWeapons) {
            if (weapon.canRaiseLevel()) {
                eligibleWeapons.add(weapon);
                if (weapon.level > 0) // Already owned weapon
                    ownedEligibleEquipment.add(weapon);
            }
        }

        ArrayList<Item> eligibleItems = new ArrayList<>(); // All items that can be upgraded
        for (Item item : allItems) {
            if (item.canRaiseLevel()) {
                eligibleItems.add(item);
                if (item.level > 0) // Already owned item
                    ownedEligibleEquipment.add(item);
            }
        }

        Player player = gameView.getPlayer();

        // Try to fill up to MAX_CHOICES options
        for (int i = 0; i < MAX_CHOICES; i++) {
            boolean owned = rnd.nextDouble() <= 0.2; // 20% chance to pick an already owned equipment

            // Early exit: if no more room and no owned items to upgrade
            if (ownedEligibleEquipment.size() == 0 &&
                    !player.haveItemSpace() && !player.haveWeaponSpace()) {
                return options;
            }

            if (owned && ownedEligibleEquipment.size() > 0) {
                // Pick an already owned upgradable item
                Equipment picked = pickFromArray(ownedEligibleEquipment);
                options.add(picked);
                if (picked instanceof Weapon) {
                    eligibleWeapons.remove(picked);
                } else if (picked instanceof Item) {
                    eligibleItems.remove(picked);
                }
                ownedEligibleEquipment.remove(picked);
            } else {
                // Pick a new item depending on player inventory space
                if (player.haveWeaponSpace()) {
                    if (player.haveItemSpace()) {
                        // Space for both: pick from all eligible
                        ArrayList<Equipment> combined = new ArrayList<>();
                        combined.addAll(eligibleWeapons);
                        combined.addAll(eligibleItems);
                        Equipment picked = pickFromArray(combined);
                        options.add(picked);
                        if (picked instanceof Weapon) {
                            eligibleWeapons.remove(picked);
                        } else if (picked instanceof Item) {
                            eligibleItems.remove(picked);
                        }
                    } else {
                        // Only weapon space available
                        Equipment picked = pickFromArray(eligibleWeapons);
                        options.add(picked);
                        eligibleWeapons.remove(picked);
                        ownedEligibleEquipment.remove(picked);
                    }
                } else {
                    if (player.haveItemSpace()) {
                        // Only item space available
                        Equipment picked = pickFromArray(eligibleItems);
                        options.add(picked);
                        eligibleItems.remove(picked);
                        ownedEligibleEquipment.remove(picked);
                    } else {
                        // No space for new items, fallback to owned
                        Equipment picked = pickFromArray(ownedEligibleEquipment);
                        options.add(picked);
                        if (picked instanceof Weapon) {
                            eligibleWeapons.remove(picked);
                        } else if (picked instanceof Item) {
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
            if(Objects.equals(weaponItr.name, weapon.name))
                return weaponItr;
        }
        return null;
    }
}
