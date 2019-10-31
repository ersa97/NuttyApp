package com.example.nuttyapp.ui.main.activity.detailactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuttyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {


    private static final String TAG = DetailActivity.class.getSimpleName();
    Button btnCheckIn, btnCheckOut;
    RecyclerView recyclerDetail;
    DetailStorageAdapter adapter;
    List<ActivityStorageDetail> list = new ArrayList<>();

    Button btnStorage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference storageRef = db.collection("storage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setUp();
    }


    void setUp() {
        btnCheckIn = findViewById(R.id.btn_check_in);
        btnCheckOut = findViewById(R.id.btn_check_out);
        recyclerDetail = findViewById(R.id.recycler_detail);
        recyclerDetail.setHasFixedSize(true);
        recyclerDetail.setLayoutManager(new LinearLayoutManager(this));

        setUpRecyclerView();

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteBatch batch = db.batch();
                for (ActivityStorageDetail data : list) {
                    DocumentReference ref = db.collection("storage").document(data.getId());
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", data.getId());
                    map.put("location", data.getLocation());
                    map.put("measurement", data.getMeasurement());
                    map.put("stock", data.getStock() + data.getAmount());
                    batch.update(ref, map);
                }
                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DetailActivity.this, "Update Stock In Berhasil", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (ActivityStorageDetail data : list) {
                    if(data.getStock() < data.getAmount()){
                        Toast.makeText(DetailActivity.this, "Stock ["+data.getName()+"] tidak cukup untuk dikeluarkan", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                WriteBatch batch = db.batch();
                for (ActivityStorageDetail data : list) {
                    DocumentReference ref = db.collection("storage").document(data.getId());
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", data.getId());
                    map.put("location", data.getLocation());
                    map.put("measurement", data.getMeasurement());
                    map.put("stock", data.getStock() - data.getAmount());
                    batch.update(ref, map);
                }
                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DetailActivity.this, "Update Stock Out Berhasil", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
        recyclerDetail.setAdapter(adapter);
    }

    void setUpRecyclerView() {
//        Query query = storageRef.orderBy("name",Query.Direction.ASCENDING);

        adapter = new DetailStorageAdapter(new DetailStorageAdapter.DetailStorageListener() {
            @Override
            public void onUpdate(ActivityStorageDetail data, int position) {
                list.set(position, data);
            }
        });
        db.collection("storage")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.toObject(ActivityStorageDetail.class));
                            }

                            adapter.setList(list);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}
