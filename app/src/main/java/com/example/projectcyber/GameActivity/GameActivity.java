package com.example.projectcyber.GameActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Equipment.Items.Item;
import com.example.projectcyber.GameActivity.Equipment.Weapons.Weapon;
import com.example.projectcyber.GameActivity.PauseMenu.EquipmentAdapter;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.Menu.MenuActivity;
import com.example.projectcyber.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;

public class GameActivity extends AppCompatActivity {

    GameView gameView;
    FloatingActionButton pauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Log.d("create", "create");

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
        gameView.setLayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ConstraintLayout main = findViewById(R.id.main);
        main.addView(gameView);

        pauseButton = findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.pauseEntities();
                try {
                    showPauseDialog();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    public void showLevelUpDialog(HashSet<Equipment> options){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_level_up);


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

        LinearLayout layout = dialog.findViewById(R.id.levelUpLinearLayout);
        for(Equipment option : options){
            LevelUpItemView itemView = new LevelUpItemView(this);
            itemView.set(option);
            layout.addView(itemView);
            itemView.setOnClickListener(onClickListener);
        }

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    public void showResultDialog(boolean won){


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.result_dialog);
        dialog.setCancelable(false);

        TextView resultText = dialog.findViewById(R.id.resultTextView);
        TextView coinText = dialog.findViewById(R.id.coinTextView);
        Button returnButton = dialog.findViewById(R.id.backButton);

        resultText.setText(won ? "YOU WIN!" : "YOU ARE DEAD!");
        coinText.setText("COINS : " + gameView.getCoins());
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeGame();
            }
        });

        dialog.show();
    }
    private void closeGame(){
        gameView.stopGame();
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        intent.putExtra("Coins", gameView.getCoins());
        setResult(RESULT_OK, intent);
        finish();
    }
    public void showPauseDialog() throws Exception {

        Callable<GridLayoutManager> getManager = () -> {
            return new GridLayoutManager(this,gameView.getPlayer().getMaxEquipmentPerType()){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
        };

        HashSet<Weapon> weapons = gameView.getPlayer().getWeapons();
        Collection<Item> items = gameView.getPlayer().getItems();

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pause_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, getWindow().getWindowManager().getCurrentWindowMetrics().getBounds().bottom/2);

        RecyclerView weaponsRecycler = dialog.findViewById(R.id.weaponsRecycler);
        weaponsRecycler.setAdapter(new EquipmentAdapter(weapons));
        weaponsRecycler.setLayoutManager(getManager.call());

        RecyclerView itemsRecycler = dialog.findViewById(R.id.itemsRecycler);
        itemsRecycler.setAdapter(new EquipmentAdapter(items));
        itemsRecycler.setLayoutManager(getManager.call());

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                gameView.resumeGame();
            }
        });

        Button leaveButton = dialog.findViewById(R.id.leaveButton);
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeGame();
            }
        });

        Button resumeButton = dialog.findViewById(R.id.resumeButton);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();



    }


    @Override
    protected void onPause() {
        Log.d("pause", "pause");
        gameView.stopGame();
        super.onPause();
    }

    //cancel back pressing to avoid closing game.
    @Override
    public void onBackPressed() {

    }


}