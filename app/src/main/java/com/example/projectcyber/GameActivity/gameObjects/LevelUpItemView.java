package com.example.projectcyber.GameActivity.gameObjects;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectcyber.R;

public class LevelUpItemView extends FrameLayout {

    TextView itemNameTextView;
    TextView itemDescTextView;
    TextView levelTextView;

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

}
