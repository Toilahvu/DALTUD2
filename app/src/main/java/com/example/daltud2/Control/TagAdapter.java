package com.example.daltud2.Control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.TruyenAddress;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    private ArrayList<TruyenAddress> dataList;

    public TagAdapter(ArrayList<TruyenAddress> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String data = String.valueOf(dataList.get(position));
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size(); // Trả về số lượng item
    }

    // Lớp ViewHolder bên trong Adapter
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTag);
        }
    }
}
