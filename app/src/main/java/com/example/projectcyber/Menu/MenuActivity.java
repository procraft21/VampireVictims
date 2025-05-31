package com.example.projectcyber.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcyber.GameActivity.GameActivity;
import com.example.projectcyber.R;
import com.example.projectcyber.UserLogic.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * MenuActivity serves as the main hub post-authentication, where the user can review their coin balance,
 * upgrade stats, and initiate a new game session. It also handles user logout and data synchronization.
 */
public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore DB;

    private TextView coinTextView;
    private RecyclerView statShop;
    private Button playButton;
    private FloatingActionButton logoutButton;

    private ArrayList<StatItem> stats;
    private ActivityResultLauncher<Intent> launcher;

    private User user;

    /**
     * Initializes the menu activity, loads user data, and configures UI interactions.
     *
     * @param savedInstanceState Previous instance state, if available
     */
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

        // Initialize Firebase components
        firebaseAuth = FirebaseAuth.getInstance();
        DB = FirebaseFirestore.getInstance();

        // Initialize views and components
        coinTextView = findViewById(R.id.coinsTextView);
        statShop = findViewById(R.id.statsUpgradesView);
        playButton = findViewById(R.id.playButton);
        logoutButton = findViewById(R.id.logoutButton);

        user = (User) getIntent().getSerializableExtra("User");
        if (user == null) throw new IllegalStateException("User object must be provided via Intent");

        stats = user.getStats();
        setCoinsText(user.getCoins());

        // Configure RecyclerView for stat upgrades
        StatUpgradeRecyclerAdapter adapter = new StatUpgradeRecyclerAdapter(user, this);
        statShop.setAdapter(adapter);
        statShop.setLayoutManager(new GridLayoutManager(this, 3));

        // Handle game launch
        playButton.setOnClickListener(view -> {
            Log.d("user", "Max HP Level: " + user.getMaxHpLvl());
            saveUserToDatabase(user);
            startGame(user);
        });

        // Handle user logout
        logoutButton.setOnClickListener(view -> {
            firebaseAuth.signOut();
            finish();
        });

        // Handle game result returning with coin updates
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                user.addCoins(data.getIntExtra("Coins", 0));
                                setCoinsText(user.getCoins());
                                saveUserToDatabase(user);
                            }
                        }
                    }
                });
    }

    /**
     * Launches the GameActivity with user-defined stat modifiers as Intent extras.
     *
     * @param user The active user instance with current stat values
     */
    private void startGame(User user) {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        for (StatItem item : stats) {
            intent.putExtra(item.getType().name(), item.getFinalValue());
        }
        launcher.launch(intent);
    }

    /**
     * Saves the current user state to Firestore.
     *
     * @param user The user object containing updated state
     */
    private void saveUserToDatabase(User user) {
        DB.collection(getString(R.string.usersCollection))
                .document(firebaseAuth.getCurrentUser().getUid())
                .set(user);
    }

    /**
     * Updates the coin text view with the user's current coin balance.
     *
     * @param coins Number of coins to display
     */
    public void setCoinsText(int coins) {
        coinTextView.setText("Coins : " + coins);
    }
}
