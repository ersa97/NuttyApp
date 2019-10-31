package com.example.nuttyapp.ui.main.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuttyapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StorageAdapter extends FirestoreRecyclerAdapter <Storage, StorageAdapter.StorageHolder> {


    public StorageAdapter(@NonNull FirestoreRecyclerOptions<Storage> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull StorageHolder storageHolder, int i, @NonNull Storage storage) {
        storageHolder.bind(storage);
    }

    @NonNull
    @Override
    public StorageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_storage, parent, false);
        return new StorageHolder(view);
    }

    class StorageHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewLocation;
        TextView textViewStock;
        TextView textViewMeasurement;
        CardView cardView;

        public StorageHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.show_storage);
            textViewLocation = itemView.findViewById(R.id.show_locations);
            textViewStock = itemView.findViewById(R.id.show_stock);
            textViewMeasurement = itemView.findViewById(R.id.show_measurement);

        }

        public void bind(final Storage storage){
            textViewName.setText(storage.getName());
            textViewLocation.setText(storage.getLocations());
            textViewStock.setText(String.valueOf(storage.getStock()));
            textViewMeasurement.setText(storage.getMeasurement());
        }
    }
}
