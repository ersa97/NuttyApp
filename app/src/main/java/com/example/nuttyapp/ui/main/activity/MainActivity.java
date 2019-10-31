package com.example.nuttyapp.ui.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.main.storage.MainStorage;
import com.example.nuttyapp.ui.setting.AccountSetting;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btnStorage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference activityRef = db.collection("activity");

    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;

    private ActivityAdapter adapter;

    private boolean doubleBackPressed;

    private Handler handler = new Handler();

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doubleBackPressed = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStorage = findViewById(R.id.btn_storage);
        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainStorage.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpRecyclerView();

    }

    private void setUpRecyclerView(){
        Query query = activityRef.orderBy("name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<activity> recyclerOptions = new FirestoreRecyclerOptions.Builder<activity>()
                .setQuery(query,activity.class)
                .build();

        adapter = new ActivityAdapter(recyclerOptions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.go_to_account:
                Intent intent = new Intent(MainActivity.this, AccountSetting.class);
                startActivity(intent);

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null){
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            super.onBackPressed();
            return;
        }

        this.doubleBackPressed = true;
        Toast.makeText(this, "please click again to exit app", Toast.LENGTH_SHORT).show();

        handler.postDelayed(runnable, 2000);
    }
}
