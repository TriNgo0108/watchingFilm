package com.b1707000.groupproject;

import android.content.Context;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> implements ItemSelectedListener {
    private final List<Movie> movies;
    private final Context context;
    public MovieAdapter(List<Movie> movies, Context context){
        this.movies = movies;
        this.context = context;
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_layout,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.setItemSelectedListener(this);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // Bitmap is loaded, use image here
                BitmapDrawable drawable = new BitmapDrawable(context.getResources(),bitmap);
                holder.image.setBackground(drawable);
                Log.d("From",from.name());



            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                holder.image.setBackground(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                holder.image.setBackground(placeHolderDrawable);
            }


        };
        Picasso.get().load(movie.getImgURL()).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_error_24).into(target);
        holder.episodeNumber.setText(String.format("Có %s tập",movie.getAmount()));
        holder.filmName.setText(movie.getFilmName());
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onClick(View view, int position) {
        Movie movie = movies.get(position);
        Intent intent = new Intent(context,FilmActivity.class);
        intent.putExtra("id",movie.getId());
        intent.putExtra("filmName",movie.getFilmName());
        intent.putExtra("imgURL",movie.getImgURL());
        intent.putExtra("publishDate",movie.getPublishDate());
        intent.putExtra("category",movie.getCategory());
        intent.putExtra("description",movie.getDescription());
        intent.putExtra("amount",movie.getAmount());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextWrapper cw = new ContextWrapper(context);
        cw.startActivity(intent);
    }
}
