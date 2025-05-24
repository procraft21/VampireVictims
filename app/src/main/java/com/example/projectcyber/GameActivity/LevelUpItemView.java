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

public class LevelUpItemView extends FrameLayout {

    TextView itemNameTextView;
    TextView itemDescTextView;
    TextView levelTextView;
    ImageView equipmentImage;

    Equipment equipment;

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

    public void init(){
        inflate(getContext(), R.layout.levelup_item, this);
        itemNameTextView = findViewById(R.id.levelupItemName);
        itemDescTextView = findViewById(R.id.levelupItemDesc);
        levelTextView = findViewById(R.id.levelupItemLevel);
        equipmentImage = findViewById(R.id.levelUpImage);

    }

    public TextView getItemNameTextView() {
        return itemNameTextView;
    }

    public TextView getItemDescTextView() {
        return itemDescTextView;
    }

    public TextView getLevelTextView() {
        return levelTextView;
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

    private void setBitmap(Bitmap bitmap){ equipmentImage.setImageBitmap(bitmap);}

    private void setLevelUpItemView(String name, int level, String desc, Bitmap bitmap){
        setName(name);
        setLevel(level);
        setDesc(desc);
        setBitmap(bitmap);
    }

    public void set(Equipment equipment){
        this.equipment = equipment;
        LevelDesc desc = equipment.getNextLevelDescription();
        setLevelUpItemView(desc.getName(), desc.getLevel(), desc.getDesc(), equipment.getEquipmentBitmap());
    }

    public Equipment getEquipment(){
        return equipment;
    }
}
