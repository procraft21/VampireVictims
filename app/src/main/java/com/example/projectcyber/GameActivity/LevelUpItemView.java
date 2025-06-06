package com.example.projectcyber.GameActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Equipment.LevelDesc;
import com.example.projectcyber.R;

/**
 * Custom view for displaying a level-up item in the game.
 * It shows the equipment's name, level, description, and icon.
 */
public class LevelUpItemView extends FrameLayout {

    private TextView itemNameTextView;
    private TextView itemDescTextView;
    private TextView levelTextView;
    private ImageView equipmentImage;

    private Equipment equipment;


    public LevelUpItemView(Context context) {
        super(context);
        init();
    }

    public LevelUpItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public LevelUpItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public LevelUpItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * Initializes the layout and finds the view references.
     */
    public void init(){
        inflate(getContext(), R.layout.levelup_item, this);
        itemNameTextView = findViewById(R.id.levelupItemName);
        itemDescTextView = findViewById(R.id.levelupItemDesc);
        levelTextView = findViewById(R.id.levelupItemLevel);
        equipmentImage = findViewById(R.id.levelUpImage);
    }


    private void setName(String name){
        itemNameTextView.setText(name);
    }


    private void setLevel(int level){
        levelTextView.setText("level : " + level);
    }


    private void setDesc(String desc){
        itemDescTextView.setText(desc);
    }

    private void setBitmap(Bitmap bitmap){
        equipmentImage.setImageBitmap(bitmap);
    }

    /**
     * Populates all views with item data.
     *
     * @param name Name of the equipment
     * @param level Level of the equipment
     * @param desc Description of the equipment
     * @param bitmap Icon/image of the equipment
     */
    private void setLevelUpItemView(String name, int level, String desc, Bitmap bitmap){
        setName(name);
        setLevel(level);
        setDesc(desc);
        setBitmap(bitmap);
    }

    /**
     * Sets the view's content based on the given equipment.
     *
     * @param equipment The equipment to be displayed
     */
    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
        LevelDesc desc = equipment.getNextLevelDescription();
        setLevelUpItemView(desc.getName(), desc.getLevel(), desc.getDesc(), equipment.getEquipmentBitmap());
    }

    public Equipment getEquipment(){
        return equipment;
    }
}
