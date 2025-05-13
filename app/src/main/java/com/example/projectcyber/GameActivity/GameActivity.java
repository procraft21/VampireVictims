package com.example.projectcyber.GameActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Equipment.Items.Item;
import com.example.projectcyber.GameActivity.Equipment.Weapons.Weapon;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    public void showLevelUpDialog(HashSet<Equipment> options){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_level_up);

        LevelUpItemView view1 = dialog.findViewById(R.id.levelUpItemView1);
        LevelUpItemView view2 = dialog.findViewById(R.id.levelUpItemView2);
        LevelUpItemView view3 = dialog.findViewById(R.id.levelUpItemView3);

        ArrayList<Equipment> oppArr = new ArrayList<>(options);

        view1.set(oppArr.get(0));
        view2.set(oppArr.get(1));
        view3.set(oppArr.get(2));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Equipment equipment = ((LevelUpItemView)view).getEquipment();
                if(equipment instanceof Weapon){
                    if(gameView.getPlayer().getWeapons().contains(equipment))
                        equipment.raiseLevel();
                    else
                        gameView.getPlayer().addWeapon((Weapon)equipment);
                }else {
                    if(gameView.getPlayer().getItems().contains(equipment))
                        equipment.raiseLevel();
                    else
                        gameView.getPlayer().addItem((Item)equipment);
                }
                dialog.cancel();
                gameView.resumeGame();
            }
        };

        view1.setOnClickListener(onClickListener);
        view2.setOnClickListener(onClickListener);
        view3.setOnClickListener(onClickListener);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    public void showResultDialog(boolean won){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.result_dialog);

        TextView resultText = dialog.findViewById(R.id.resultTextView);
        TextView coinText = dialog.findViewById(R.id.coinTextView);
        Button returnButton = dialog.findViewById(R.id.backButton);

        resultText.setText(won ? "YOU WIN!" : "YOU ARE DEAD!");
        coinText.setText("COINS : " + gameView.getCoins());
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.stopLoop();
                finish();
            }
        });

        dialog.show();
    }

    @Override
    protected void onStop() {
        gameView.stopLoop();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }
}