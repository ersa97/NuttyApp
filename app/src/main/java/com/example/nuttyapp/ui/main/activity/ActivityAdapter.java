package com.example.nuttyapp.ui.main.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.main.activity.detailactivity.DetailActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ActivityAdapter extends FirestoreRecyclerAdapter<activity, ActivityAdapter.ActivityHolder> {

    private OnItemClickListener listener;

    public ActivityAdapter(@NonNull FirestoreRecyclerOptions<activity> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ActivityHolder activityHolder, int i, @NonNull activity activity) {
        activityHolder.bind(activity);
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activity, parent, false);
        return new ActivityHolder(view);
    }

    class ActivityHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewDescriptions;
        CardView cardView;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.show_activity);
            textViewDescriptions = itemView.findViewById(R.id.show_description);
            cardView =itemView.findViewById(R.id.card_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            }

            public void bind(final activity activity){
                textViewName.setText(activity.getName());
                textViewDescriptions.setText(activity.getDescriptions());
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = activity.getName();
                        String descriptions = activity.getDescriptions();

                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("descriptions", descriptions);
                        view.getContext().startActivity(intent);
                    }
                });
            }

        }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickLlistener(OnItemClickListener listener){
        this.listener = listener;
    }
    }

