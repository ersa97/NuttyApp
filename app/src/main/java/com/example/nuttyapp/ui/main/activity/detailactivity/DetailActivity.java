package com.example.nuttyapp.ui.main.activity.detailactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.main.activity.ActivityAdapter;
import com.example.nuttyapp.ui.main.activity.activity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DetailActivity extends AppCompatActivity {


    Button btnCheckIn, btnCheckOut;
    RecyclerView recyclerDetail;
    DetailStorageAdapter adapter;

    Button btnStorage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference storageRef = db.collection("storage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setUp();
    }


    void setUp(){
        btnCheckIn = findViewById(R.id.btn_check_in);
        btnCheckOut = findViewById(R.id.btn_check_out);
        recyclerDetail = findViewById(R.id.recycler_detail);
        recyclerDetail.setHasFixedSize(true);
        recyclerDetail.setLayoutManager(new LinearLayoutManager(this));

        setUpRecyclerView();

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void setUpRecyclerView(){
        Query query = storageRef.orderBy("name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ActivityStorageDetail> recyclerOptions = new FirestoreRecyclerOptions.Builder<ActivityStorageDetail>()
                .setQuery(query,ActivityStorageDetail.class)
                .build();
        adapter = new DetailStorageAdapter(recyclerOptions);
        recyclerDetail.setAdapter(adapter);
    }
}
