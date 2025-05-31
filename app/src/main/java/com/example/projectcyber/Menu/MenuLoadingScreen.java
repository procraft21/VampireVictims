package com.example.projectcyber.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectcyber.R;
import com.example.projectcyber.UserLogic.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuLoadingScreen extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_loading_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        DB = FirebaseFirestore.getInstance();

        DB.collection(getString(R.string.usersCollection)).document(auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        Intent intent = new Intent(MenuLoadingScreen.this, MenuActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
