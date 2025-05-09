package com.example.projectcyber.GameActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.R;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        Bundle bundle = getIntent().getExtras();

        HashMap<PlayerStatsType, Double> startingStats = new HashMap<>();
        for(String name : bundle.keySet())
            startingStats.put(PlayerStatsType.valueOf(name),bundle.getDouble(name));

        gameView = new GameView(this, startingStats);
        window.setContentView(gameView);
    }

    public void showLevelUpDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_level_up);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }


}