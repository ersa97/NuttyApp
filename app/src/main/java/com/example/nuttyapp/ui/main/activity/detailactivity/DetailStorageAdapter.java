package com.example.nuttyapp.ui.main.activity.detailactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.main.activity.ActivityAdapter;
import com.example.nuttyapp.ui.main.storage.Storage;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DetailStorageAdapter extends FirestoreRecyclerAdapter <ActivityStorageDetail, DetailStorageAdapter.DetailStorageHolder> {

    public DetailStorageAdapter(@NonNull FirestoreRecyclerOptions<ActivityStorageDetail> options){
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull DetailStorageHolder storageHolder, int i, @NonNull ActivityStorageDetail storage) {
        storageHolder.bind(storage);
    }

    @NonNull
    @Override
    public DetailStorageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_storage_activity, parent, false);
        return new DetailStorageHolder(view);
    }

    class DetailStorageHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewLocation;
        TextView textViewStock;
        TextView textViewMeasurement;
        EditText editAmount;

        public DetailStorageHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.show_storage);
            textViewLocation = itemView.findViewById(R.id.show_locations);
            textViewStock = itemView.findViewById(R.id.show_stock);
            textViewMeasurement = itemView.findViewById(R.id.show_measurement);
            editAmount = itemView.findViewById(R.id.edit_amount);
        }

        public void bind(final ActivityStorageDetail storage){
            textViewName.setText(storage.getName());
            textViewLocation.setText(storage.getLocations());
            textViewStock.setText(String.valueOf(storage.getStock()));
            textViewMeasurement.setText(storage.getMeasurement());
        }
    }

}
