package com.example.nuttyapp.ui.main.storage;

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
import com.example.nuttyapp.ui.main.activity.ActivityAdapter;
import com.example.nuttyapp.ui.main.activity.MainActivity;
import com.example.nuttyapp.ui.setting.AccountSetting;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainStorage extends AppCompatActivity {

    Button btnMain;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference storageRef = db.collection("storage");

    RecyclerView recyclerView;

    private StorageAdapter adapter;

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
        setContentView(R.layout.activity_main_storage);

        btnMain = findViewById(R.id.btn_activity);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.main_storage);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query = storageRef.orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Storage> recyclerOptions = new FirestoreRecyclerOptions.Builder<Storage>()
                .setQuery(query,Storage.class)
                .build();

        adapter = new StorageAdapter(recyclerOptions);
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
                Intent intent = new Intent(MainStorage.this, AccountSetting.class);
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
