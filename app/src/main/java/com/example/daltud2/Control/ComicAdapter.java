package com.example.daltud2.Control;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.Comic;
import com.example.daltud2.R;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    public class ComicViewHolder extends RecyclerView.ViewHolder {
        public TextView comicTitle;
        public ImageView comicImage;

        public ComicViewHolder(View itemView) {
            super(itemView);
            comicTitle = itemView.findViewById(R.id.comicTitle);
            comicImage = itemView.findViewById(R.id.comicImage);
        }
    }
    private List<Comic> comicList;

    public ComicAdapter(List<Comic> comicList) {
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ComicAdapter.ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic, parent, false);
        return new ComicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicAdapter.ComicViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        holder.comicTitle.setText(comic.title);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }
    public void updateData(List<Comic> newComicList) {
        this.comicList = newComicList;
        notifyDataSetChanged(); // Báo cho RecyclerView biết dữ liệu đã thay đổi
    }


}
