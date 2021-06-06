package com.b1707000.groupproject;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MovieHolder extends ViewHolder implements View.OnClickListener {
    CoordinatorLayout image;
    TextView episodeNumber;
    TextView filmName;
    private ItemSelectedListener itemSelectedListener;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        episodeNumber = itemView.findViewById(R.id.episodeNumber);
        filmName = itemView.findViewById(R.id.name);
        itemView.setOnClickListener(this);
    }

    public void setItemSelectedListener(ItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    public void onClick(View v) {
        itemSelectedListener.onClick(v,getAdapterPosition());
    }
}
