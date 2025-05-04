package com.example.projectcyber.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcyber.GameActivity.GameActivity;
import com.example.projectcyber.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import com.example.projectcyber.UserLogic.User;

public class MenuActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore DB;

    TextView coinTextView;
    private RecyclerView statShop;

    private ArrayList<StatItem> stats;

    Button playButton;

    FloatingActionButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MenuActivity activity = this;

        coinTextView = findViewById(R.id.coinsTextView);
        statShop = findViewById(R.id.statsUpgradesView);
        stats = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        DB = FirebaseFirestore.getInstance();

        playButton = findViewById(R.id.playButton);

        String uid = firebaseAuth.getCurrentUser().getUid();

        logoutButton = findViewById(R.id.logoutButton);

        DB.collection(getString(R.string.usersCollection)).document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);

                assert user!=null;

                long coins = user.getCoins();
                stats = user.getStats();

                coinTextView.setText("Coins : " + coins);

                StatUpgradeRecyclerAdapter adapter = new StatUpgradeRecyclerAdapter(user, activity);
                statShop.setAdapter(adapter);
                statShop.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("user", user.getMaxHpLvl() + "");
                        saveUserToDatabase(user);
                        startGame();
                    }
                });

                logoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseAuth.signOut();
                        activity.finish();
                    }
                });
            }
        });

    }

    private void startGame(){
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        for(StatItem item : stats){
            intent.putExtra(item.getType().name(), item.getFinalValue());
            Log.d("stats", item.getType().name() + item.getFinalValue());
        }

        Log.d("stats", stats.toString());
        startActivity(intent);
    }

    private void saveUserToDatabase(User user){
        DB.collection(getString(R.string.usersCollection)).document(firebaseAuth.getCurrentUser().getUid())
                .set(user);
    }

    public void setCoins(int coins){
        coinTextView.setText("Coins : " + coins);
    }
}