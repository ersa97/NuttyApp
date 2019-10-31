package com.example.nuttyapp.ui.main.activity.detailactivity;

import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

public class DetailStorageAdapter extends RecyclerView.Adapter<DetailStorageAdapter.DetailStorageHolder> {


    private DetailStorageListener listener;
    private List<ActivityStorageDetail> list = new ArrayList<>();

    public DetailStorageAdapter(DetailStorageListener listener) {
        this.listener = listener;
    }

    void setList(List<ActivityStorageDetail> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    List<ActivityStorageDetail> getList(){
        return list;
    }

    @NonNull
    @Override
    public DetailStorageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailStorageHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_storage_activity, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DetailStorageHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DetailStorageHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewLocation;
        TextView textViewStock;
        TextView textViewMeasurement;
        EditText editAmount;

        DetailStorageHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.show_storage);
            textViewLocation = itemView.findViewById(R.id.show_locations);
            textViewStock = itemView.findViewById(R.id.show_stock);
            textViewMeasurement = itemView.findViewById(R.id.show_measurement);
            editAmount = itemView.findViewById(R.id.edit_amount);
        }

        void bind(final ActivityStorageDetail storage){
            textViewName.setText(storage.getName());
            textViewLocation.setText(storage.getLocation());
            textViewStock.setText(String.valueOf(storage.getStock()));
            textViewMeasurement.setText(storage.getMeasurement());
            editAmount.setText(Double.toString(storage.getAmount()));

            editAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.length() > 0){
                        list.get(getAdapterPosition()).setAmount(Double.parseDouble(editAmount.getText().toString()));
                    } else {
                        list.get(getAdapterPosition()).setAmount(0);
                    }
                    listener.onUpdate(list.get(getAdapterPosition()), getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }

    interface DetailStorageListener{
        void onUpdate(ActivityStorageDetail data, int position);
    }

}
