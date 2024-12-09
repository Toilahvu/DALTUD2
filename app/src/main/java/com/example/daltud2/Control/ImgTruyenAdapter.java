package com.example.daltud2.Control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.ImagesChapter;
import com.example.daltud2.R;

import java.util.ArrayList;

public class ImgTruyenAdapter extends RecyclerView.Adapter<ImgTruyenAdapter.MyViewHolder> {
    private ArrayList<ImagesChapter> imageList;

    public ImgTruyenAdapter(ArrayList<ImagesChapter> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anh_truyen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImagesChapter imageItem = imageList.get(position);
        holder.imageView.setImageResource(imageItem.getIdAnh()); // Gán ảnh từ ID resource
    }

    @Override
    public int getItemCount() {
        return imageList.size(); // Trả về số lượng ảnh
    }

    // Lớp ViewHolder bên trong Adapter
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgTruyen); // Tham chiếu tới ImageView trong item
        }
    }
}
