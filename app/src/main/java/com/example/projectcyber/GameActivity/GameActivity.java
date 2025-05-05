package com.example.projectcyber.GameActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.R;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {


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

        Log.d("stats", startingStats.toString());
        GameView gameView = new GameView(this, startingStats);
        window.setContentView(gameView);

    }

}