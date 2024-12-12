package com.example.daltud2.Control;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.R;

import java.io.File;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        public TextView comicTitle;
        public ImageView comicImage;

        public ComicViewHolder(View itemView) {
            super(itemView);
            comicTitle = itemView.findViewById(R.id.comicTitle);
            comicImage = itemView.findViewById(R.id.comicImage);
        }
    }
    private List<Truyen> comicList;
    //them
    private OnItemClickListener onItemClickListener;

    public ComicAdapter(List<Truyen> comicList) {
        this.comicList = comicList;
    }

    //them
    public interface OnItemClickListener {
        void onItemClick(Truyen truyen); // Hàm callback cho sự kiện click
    }
    //them
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
        Truyen comic = comicList.get(position);
        holder.comicTitle.setText(comic.getTenTruyen());

        String imagePath = comic.getUrlAnhBia();
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.comicImage.setImageBitmap(bitmap);
        } else {
            holder.comicImage.setImageResource(R.drawable.logo);
        }
        //them
        // Gắn sự kiện click cho từng item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(comic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Truyen> newComicList) {
        this.comicList = newComicList;
        notifyDataSetChanged(); // Báo cho RecyclerView biết dữ liệu đã thay đổi
    }


}
